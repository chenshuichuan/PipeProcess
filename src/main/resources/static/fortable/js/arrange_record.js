/*
* 作者：ricardo
* 描述：派工记录查看页面js
* 编写结构说明：
*     接口URL-->
*     页面加载-->
*     事件监听-->
**/
var urlPage = "/arrange/Page";
var urlCancel = "/arrange/cancelById";
var urlAdd = "/arrange/add";
var urlUpdate = "/arrange/update";

$(function (){
	var $wrapper = $('#div-table-container');
	var $table = $('#table-record');
	
	var _table = $table.dataTable($.extend(true,{},CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
		ajax : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
			//手动控制遮罩
			$wrapper.spinModal();
			//封装请求参数
			var param = userManage.getQueryCondition(data);
			$.ajax({
		            type: "GET",
		            url: urlPage,
		            cache : false,	//禁用缓存
		            data: param,	//传入已封装的参数
		            dataType: "json",
		            success: function(result) {
                        //异常判断与处理
                        if (result.errorCode) {
                            $.dialog.alert("查询失败。错误码："+result.errorCode);
                            return;
                        }
                        //封装返回数据，这里仅演示了修改属性名
                        var returnData = {};
                        //returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.draw = result.draw;
                        returnData.recordsTotal = result.total;
                        returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.pageData;
                        //关闭遮罩
                        $wrapper.spinModal(false);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
		            },
		            error: function(XMLHttpRequest, textStatus, errorThrown) {
		                $.dialog.alert("查询失败");
		                $wrapper.spinModal(false);
		            }
		        });
		},
        columns: [
            //CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                data : "arrangeId",
                width : "80px"
            },
            {
                data : "arrangeType",
                width : "80px"
            },
            {
            	className : "ellipsis",	
            	data: "name",
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				width : "80px"			
            },
            {
                className : "ellipsis",
                data: "section",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width : "120px"
            },
            {
                data: "stage",
                width : "80px"
            },
            {
                className : "ellipsis",
                data: "workplace",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width : "150px"
            },
            {
                data: "worker",
                width : "80px"
            },
			{
				data : "isFinished",
				width : "80px",
				render : function(data,type, row, meta) {
					return (data?'<i class="fa fa-circle"></i>完工':'<i class="fa fa-circle-o"></i>未完工');
				}
			},
			 {
				className : "td-operation",
				data: null,
				defaultContent:"",
				orderable : false,
				width : "80px"
			}
            ,
            {
                data: ""
            }
        ],
        "createdRow": function ( row, data, index ) {
        	//行渲染回调,在这里可以对该行dom元素进行任何操作
        	//给当前行加样式
        	if (data.isFinished) {
        		$(row).addClass("info");
			}
        	//给当前行某列加样式
        	$('td', row).eq(7).addClass(data.isFinished?"text-success":"text-error");
        	//不使用render，改用jquery文档操作呈现单元格
            var $btnCancel = $('<button type="button" class="btn btn-small btn-primary btn-cancel">撤销派工</button>');
            $('td', row).eq(8).append($btnCancel);
            
        },
        "drawCallback": function( settings ) {
        	//渲染完毕后的回调
        	//默认选中第一行
        	$("tbody tr",$table).eq(0).click();
        }
	})).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

	
	$("#btn-simple-search").click(function(){
		userManage.fuzzySearch = true;
		
		//reload效果与draw(true)或者draw()类似,draw(false)则可在获取新数据的同时停留在当前页码,可自行试验
//		_table.ajax.reload();
//		_table.draw(false);
		_table.draw();
	});
	
	$("#btn-advanced-search").click(function(){
		userManage.fuzzySearch = false;
		_table.draw();
	});

	//行点击事件
	$("tbody",$table).on("click","tr",function(event) {
		$(this).addClass("active").siblings().removeClass("active");
		//获取该行对应的数据
		var item = _table.row($(this).closest('tr')).data();
		userManage.currentItem = item;
		userManage.showItemDetail(item);
    });
	
	$table.on("click",".btn-cancel",function() {
		//点击撤销派工按钮
		var item = _table.row($(this).closest('tr')).data();
		$(this).closest('tr').addClass("active").siblings().removeClass("active");
		userManage.cancelItem([item]);
	});
	
	$("#toggle-advanced-search").click(function(){
		$("i",this).toggleClass("fa-angle-double-down fa-angle-double-up");
		$("#div-advanced-search").slideToggle("fast");
	});
	
	$("#btn-info-content-collapse").click(function(){
		$("i",this).toggleClass("fa-minus fa-plus");
		$("span",this).toggle();
		$("#arrange-view .info-content").slideToggle("fast");
	});
});



var userManage = {
	currentItem : null,
	fuzzySearch : true,
	getQueryCondition : function(data) {
		var param = {};
		//组装排序参数
		// if (data.order&&data.order.length&&data.order[0]) {
		// 	switch (data.order[0].column) {
		// 	case 1:
		// 		param.orderColumn = "name";
		// 		break;
		// 	case 2:
		// 		param.orderColumn = "job";
		// 		break;
		// 	case 3:
		// 		param.orderColumn = "state";
		// 		break;
		// 	case 4:
		// 		param.orderColumn = "updateTime";
		// 		break;
		// 	default:
		// 		param.orderColumn = "name";
		// 		break;
		// 	}
		// 	param.orderDir = data.order[0].dir;
		// }
		// //组装查询参数
		// param.fuzzySearch = userManage.fuzzySearch;
		// if (userManage.fuzzySearch) {
		// 	param.fuzzy = $("#fuzzy-search").val();
		// }else{
		// 	param.name = $("#name-search").val();
		// 	param.code = $("#code-search").val();
		// 	param.job = $("#job-search").val();
		// 	param.departments = $("#departments-search").val();
		// 	param.state = $("#state-search").val();
		// 	param.role = $("#role-search").val();
		// }
		//组装分页参数
		param.startIndex = data.start;
		param.pageSize = data.length;
		param.draw = data.draw;
		
		return param;
	},
	showItemDetail : function(item) {
		$("#arrange-view").show().siblings(".info-block").hide();
		if (!item) {
			$("#arrange-view .prop-value").text("");
			return;
		}
        $("#arrangeId-view").text(item.arrangeId);
        $("#arrangeType-view").text(item.arrangeType);
        $("#name-view").text(item.name);
        $("#plan-view").text(item.plan);
        $("#section-view").text(item.section);
        $("#stage-view").text(item.stage);
        $("#workplace-view").text(item.workplace);
        $("#worker-view").text(item.worker);
        $("#arranger-view").text(item.arranger);
        $("#updateTime-view").text(item.updateTime);
        $("#isFinished-view").text(item.isFinished?'<i class="fa fa-circle"></i>完工':'<i class="fa fa-circle-o"></i>未完工');
        $("#finishedTime-view").text(item.isFinished?dateToString(item.finishedTime):"---");
	},
	cancelItem : function(selectedItems) {
        $.dialog.alert("撤销测试！");
	}
};