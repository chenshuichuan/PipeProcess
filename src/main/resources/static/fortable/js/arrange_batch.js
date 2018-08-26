/*
* 作者：ricardo
* 描述：批次派工页面js
* 编写结构说明：
*     接口URL-->
*     页面加载-->
*     事件监听-->
**/
//获取计划分页
var urlPage = "/plan/page";
//将批次下料派工到工位
var urlArrangeBatchToWorkPlace="/arrange/arrangeBatchToWorkPlace";

$(function () {
    var $wrapper = $('#div-table-container');
    var $table = $('#table-plan');
    var _table = $table.dataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        ajax: function (data, callback, settings) {//ajax配置为function,手动调用异步查询
            //手动控制遮罩
            $wrapper.spinModal();
            //封装请求参数
            var param = planManage.getQueryCondition(data);
            $.ajax({
                type: "GET",
                url: urlPage,
                cache: false,	//禁用缓存
                data: param,	//传入已封装的参数
                dataType: "json",
                success: function (result) {
                    //异常判断与处理
                    if (result.errorCode) {
                        $.dialog.alert("查询失败。错误码：" + result.errorCode);
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
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $.dialog.alert("查询失败");
                    $wrapper.spinModal(false);
                }
            });
        },
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                data: "serialNumber",
                width: "40px"
            },
            {
                className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                data: "shipName",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,//会显示省略号的列，需要用title属性实现划过时显示全部文本的效果
            },
            {
                data: "batchName",
                width: "80px"
            },
            {
                className: "ellipsis",
                data: "batchDescription",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: "220px"
            },
            {
                data: "processPlace",
                width: "120px"
            },
            {
                data: "number",
                width: "80px"
            },
            {
                data: "planStart",
                width: "80px",
                render: function (data, type, row, meta) {
                    return new Date(data).toLocaleString();
                }
            },
            {
                data: "planEnd",
                width: "80px",
                render: function (data, type, row, meta) {
                    return new Date(data).toLocaleString();
                }
            },
            {
                data: "isCutted",
                width: "80px",
                render: function (data, type, row, meta) {
                    return stringIsCutted(data);
                }
            },
            {
                className: "td-operation",
                data: null,
                defaultContent: "",
                orderable: false,
                width: "120px"
            }
        ],
        "createdRow": function (row, data, index) {
            //行渲染回调,在这里可以对该行dom元素进行任何操作
            //给当前行加样式
            if (data.role) {
                $(row).addClass("info");
            }
            //给当前行某列加样式
            $('td', row).eq(9).addClass(classIsCutted(data.isCutted));
            //不使用render，改用jquery文档操作呈现单元格
            var $btnEdit = $('<button type="button" class="btn btn-small btn-info btn-edit"'+
                (data.isCutted===-1?"":"disabled='disabled'")+'>派工</button>');
            $('td', row).eq(10).append($btnEdit);

        },
        "drawCallback": function (settings) {
            //渲染完毕后的回调
            //清空全选状态
            $(":checkbox[name='cb-check-all']", $wrapper).prop('checked', false);
            //默认选中第一行
            $("tbody tr", $table).eq(0).click();
        }
    })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

    //行点击事件
    $("tbody", $table).on("click", "tr", function (event) {
        $(this).addClass("active").siblings().removeClass("active");
        //获取该行对应的数据
        var item = _table.row($(this).closest('tr')).data();
        planManage.currentItem = item;
        planManage.editItemInit(item);
    });

    $table.on("change", ":checkbox", function () {
        if ($(this).is("[name='cb-check-all']")) {
            //全选
            $(":checkbox", $table).prop("checked", $(this).prop("checked"));
        } else {
            //一般复选
            var checkbox = $("tbody :checkbox", $table);
            $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
        }
    }).on("click", ".td-checkbox", function (event) {
        //点击单元格即点击复选框
        !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
    }).on("click", ".btn-edit", function () {
        //点击派工按钮
        var item = _table.row($(this).closest('tr')).data();
        $(this).closest('tr').addClass("active").siblings().removeClass("active");
        planManage.currentItem = item;
        planManage.editItemInit(item);
        //planManage.arrangePlan(item);
    });

    $("#toggle-advanced-search").click(function () {
        $("i", this).toggleClass("fa-angle-double-down fa-angle-double-up");
        $("#div-advanced-search").slideToggle("fast");
    });

    $("#btn-info-content-collapse").click(function () {
        $("i", this).toggleClass("fa-minus fa-plus");
        $("span", this).toggle();
        $("#plan-view .info-content").slideToggle("fast");
    });

    $("#btn-simple-search").click(function () {
        planManage.fuzzySearch = true;
        //reload效果与draw(true)或者draw()类似,draw(false)则可在获取新数据的同时停留在当前页码,可自行试验
//		_table.ajax.reload();
//		_table.draw(false);
        _table.draw();
    });

    $("#btn-advanced-search").click(function () {
        planManage.fuzzySearch = false;
        _table.draw();
    });
    //派工按钮
    $("#btn-save-edit").click(function () {
        planManage.editItemSubmit();
        _table.draw();//重新加载表格数据
    });

    //信息详情页点击派工按钮
    $("#btn-view-edit").click(function () {
        planManage.editItemInit(planManage.currentItem);
    });

    $(".btn-cancel").click(function () {
        planManage.showItemDetail(planManage.currentItem);
    });
    $(".btn-edit").click(function () {
        planManage.arrangePlan(planManage.currentItem);
    });
    $("#btn-refresh").click(function () {
        window.location.reload();
    });
    //初始化工段选择框
    initSectionSeletor();
    //初始化船名选择框
    initShipSeletor();
    //document.getElementById("btn-save-edit").style.display="none";
});


var planManage = {
    currentItem: null,
    fuzzySearch: true,
    getQueryCondition: function (data) {
        var param = {};
        // //组装排序参数
        // if (data.order && data.order.length && data.order[0]) {
        //     switch (data.order[0].column) {
        //         case 1:
        //             param.orderColumn = "serialNumber";
        //             break;
        //         case 2:
        //             param.orderColumn = "shipName";
        //             break;
        //         case 3:
        //             param.orderColumn = "batchName";
        //             break;
        //         case 4:
        //             param.orderColumn = "batchDescription";
        //             break;
        //         case 5:
        //             param.orderColumn = "processPlace";
        //             break;
        //         case 6:
        //             param.orderColumn = "number";
        //             break;
        //         case 7:
        //             param.orderColumn = "planStart";
        //             break;
        //         case 8:
        //             param.orderColumn = "planEnd";
        //             break;
        //         case 9:
        //             param.orderColumn = "isCutted";
        //             break;
        //         default:
        //             param.orderColumn = "serialNumber";
        //             break;
        //     }
        //     param.orderDir = data.order[0].dir;
        // }
        //组装查询参数
        param.fuzzySearch = planManage.fuzzySearch;
        if (planManage.fuzzySearch) {//页面载入，fuzzySearch: true,
            param.fuzzy = $("#fuzzy-search").val();
            param.isCutted = "-1";//默认展示未下料的
        } else {
            param.processPlace = $("#section-search").val();
            param.shipCode = $("#ship-search").val();
            param.isCutted = $("#isCutted-search").val();
        }
        //组装分页参数
        param.startIndex = data.start;
        param.pageSize = data.length;
        param.draw = data.draw;

        return param;
    },
    showItemDetail: function (item) {
        $("#plan-view").show().siblings(".info-block").hide();
        if (!item) {
            $("#plan-view .prop-value").text("");
            return;
        }

        $("#serial-view").text(item.serialNumber);
        $("#planName-view").text(item.planName);
        $("#batchName-view").text(item.batchName);
        $("#processPlace-view").text(item.processPlace);
        $("#lightBodyPipe-view").text(item.lightBodyPipe);
        $("#actualStart-view").text(dateToString(item.actualStart));
        $("#actualEnd-view").text(dateToString(item.actualEnd));
        $("#sendPicTime-view").text(dateToString(item.sendPicTime));
        $("#upldateTime-view").text(dateToString(item.upldateTime));
        $("#oneBCutNum-view").text(item.oneBcutNum);
        $("#oneBendCut-view").text(item.oneBendCut);
        $("#oneVerCut-view").text(item.oneVerCut);
        $("#oneBigCut-view").text(item.oneBigCut);
        $("#twoTotalNumber-view").text(item.twoSpeBendCut+item.twoSpeVerCut);
        $("#twoSpeBendCut-view").text(item.twoSpeBendCut);
        $("#twoSpeVerCut-view").text(item.twoSpeVerCut);
        $("#stocks-view").text(item.stocks);
        $("#sections-view").text(item.sections);
        $("#units-view").text(getUnitsName(item.id));
        $("#remark-view").text(item.remark);
    },
    editItemInit: function (item) {
        if (!item) {
            return;
        }
        initSelector(item.processPlace);
        //$("#workPlace-edit").append();
        $("#form-edit")[0].reset();
        $("#title-edit").text(item.planName);
        $("#serial-view2").text(item.serialNumber);
        $("#planName-view2").text(item.planName);
        $("#batchName-view2").text(item.batchName);
        $("#processPlace-view2").text(item.processPlace);
        $("#units-view2").text(getUnitsName(item.id));
        $("#planId-edit").val(item.id);

        $("#plan-edit").show().siblings(".info-block").hide();
    },
    editItemSubmit: function () {
        var id = $("#planId-edit").val();
        var workPlaceId = $("#workPlace-edit").val();
        var remark = $("#remark-edit").val();
        //参数检查
        if(workPlaceId===null||workPlaceId.length<=0){
            Dialog.lockDialog("警告","请选择工位！");
            $.dialog.tips("请选择工位！");
            return;
        }
         $.dialog.confirm("确定要将该计划批次派工给该工位吗？", function () {
             $.ajax({
                 type : "get",
                 url : urlArrangeBatchToWorkPlace,
                 data : "planId="+id+"&workPlaceId="+workPlaceId+"&remark="+remark,
                 async : true,
                 success : function(data){
                     $.dialog.tips(data.message);
                     //要不要重新加载页面呢？不用，在调用监听处重新加载表格数据，而不是刷新
                 }
             });
         });
        //document.getElementById("btn-save-edit").style.display="none";
    },
    //表格里面的单个plan派工
    //待完成
    arrangePlan: function (item) {
        var workPlaceId = $("#workPlace-edit").val();

        //alert("id="+id+",workPlaceId="+workPlaceId);
        //参数检查
        if(workPlaceId===null||workPlaceId.length<=0){
            Dialog.lockDialog("警告","请选择工位！")
            $.dialog.tips("请选择工位！");
        }
       // document.getElementById("btn-save-edit").style.display="block";
    }
};
//根据的得到的工位列表渲染选择框
function initSelector(processPlace) {
    var stage = getDepartmentBySectionAndStage(processPlace,"下料");
    var departmentList = getWorkplaceInfoByStageId(stage.id);
    //根据的得到的工位列表渲染选择框
    $("#workPlace-edit").empty();
    var options = "";
    for (var i=0; i<departmentList.length;i++){
        //这里的是否空闲渲染存在问题
        options+= "<option"+
            " value='"+departmentList[i].id+ "' data-content=\"<span class='label "+
            (departmentList[i].isVaccancy?"label-success'>" : "label-warning'>")+
            departmentList[i].name +"</span>\">"+departmentList[i].name
            +"</option>";
    }
    $("#workPlace-edit").append(options);
    $('#workPlace-edit').selectpicker('refresh');
}

