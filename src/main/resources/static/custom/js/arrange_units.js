/*
* 作者：ricardo
* 描述：部门管理页面js
* 编写结构说明：
*     接口URL-->
*     页面加载-->
*     事件监听-->
*
**/

//获取某工序下可派工的所有单元
var urlGetUnitsByStage = "/unit/getUnitsByStageId";
//将单元派工给工位
var urlArrangeUnitToWorkPlace= "/arrange/arrangeUnitToWorkPlace";

var $wrapper = $('#table-panel-body');


$144(document).ready(function(){
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick
        }
    };
    //这里要记录当前选择的工位，及其父部门(也就是工序)的id,如若选择的工序与当前cookie保存的不一致，则重新加载表格数据
    function onClick(event, treeId, treeNode, clickFlag) {
        $("#selected-view").text(treeNode.name);
        $("#view-title-span").text(treeNode.name);
        //$144.dialog.tips(treeNode.level);
        if(treeNode.level===1){//工序级别
            updateTable(treeNode.id);
            setCookie("workplace","");
            setCookie("pId",treeNode.id);
        }
        else if(treeNode.level===2){
            if(getCookie("pId")!==(""+treeNode.pId)){//在不同工序中直接选择工位也会触发更新表格数据
                updateTable(treeNode.pId);
                setCookie("pId",treeNode.pId);
            }
            setCookie("workplace",treeNode.name);
            setCookie("workplaceId",treeNode.id);
        }
        else setCookie("workplace","");
    }
    setCookie("workplace","");
    setCookie("pId",0);
    $144.fn.zTree.init($144("#treeDemo"), setting, treeList);
    var $jq =jQuery.noConflict(true);
    var $table = $110('#dataTables-example');
    var  _table = $table.dataTable(
        $.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION,
            {
                ajax: function (data, callback, settings) {//ajax配置为function,手动调用异步查询
                    //手动控制遮罩
                    $wrapper.spinModal();
                    //封装请求参数
                    var param = planManage.getQueryCondition(data);
                    $.ajax({
                        type: "GET",
                        url: urlGetUnitsByStage,
                        cache: false,	//禁用缓存
                        data: param,	//传入已封装的参数
                        dataType: "json",
                        success: function (result) {
                            //异常判断与处理
                            if (result.errorCode) {
                                $144.dialog.alert("查询失败。错误码：" + result.errorCode);
                                return;
                            }
                            //封装返回数据，这里仅演示了修改属性名
                            var returnData = {};
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
                            $144.dialog.alert("查询失败");
                            $wrapper.spinModal(false);
                        }
                    });
                },
                columns: [
                    {
                        data: "unitId",
                        width: "50px"
                    },
                    {
                        className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                        data: "unitName",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,//会显示省略号的列，需要用title属性实现划过时显示全部文本的效果
                        width: "80px"
                    },
                    {
                        data: "batchName",
                        width: "80px"
                    },
                    {
                        className: "ellipsis",
                        data: "shipCode",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                        width: "80px"
                    },
                    {
                        className: "ellipsis",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                        data: "section",
                        width: "120px"
                    },
                    {
                        className: "ellipsis",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                        data: "processOrder"
                    },
                    {
                        data: "processState",
                        width: "80px"
                    },
                    {
                        data: "pipeNumber",
                        width: "80px"
                    },
                    {
                        data: "pipeProcessingNumber",
                        width: "90px"
                    },
                    {
                        data: "pipeFinishedNumber",
                        width: "100px"
                    },
                    {
                        className: "td-operation",
                        data: null,
                        defaultContent: "",
                        orderable: false,
                        width: "80px"
                    }
                ],
                "createdRow": function (row, data, index) {
                    //行渲染回调,在这里可以对该行dom元素进行任何操作
                    // //给当前行加样式
                    // if (data.role) {
                    //     $(row).addClass("info");
                    // }
                    // //给当前行某列加样式
                    // $('td', row).eq(9).addClass(classIsCutted(data.isCutted));
                    //不使用render，改用jquery文档操作呈现单元格
                    var $btnEdit = $110('<button type="button" class="btn btn-small btn-primary btn-edit">派工</button>');
                    //var $btnDel = $110('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
                    $110('td', row).eq(10).append($btnEdit);

                },
                "drawCallback": function (settings) {
                    //
                    // //默认选中第一行
                    // $("tbody tr", $table).eq(0).click();
                }
            })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
        // {
        //     "scrollY": 700,
        //     "scrollX": true
        // });

     function updateTable(stageId){
        setCookie("stageId",stageId);
         _table.draw();
     }

    $table.on("click", ".btn-edit", function () {
        //点击派工按钮
        var item = _table.row($(this).closest('tr')).data();
        $(this).closest('tr').addClass("active").siblings().removeClass("active");
        planManage.arrangeUnit(item);
    });


    //侧边栏 //侧边栏用的是jq1.11 和ztree的1.44不兼容
    $110(".dropdown-button").dropdown();
    $110("#sideNav").click(function(){
        if($110(this).hasClass('closed')){
            $110('.navbar-side').animate({left: '0px'});
            $110(this).removeClass('closed');
            $110('#page-wrapper').animate({'margin-left' : '260px'});

        }
        else{
            $110(this).addClass('closed');
            $110('.navbar-side').animate({left: '-260px'});
            $110('#page-wrapper').animate({'margin-left' : '0px'});
        }
    });
    /*MENU
        ------------------------------------*/
    $110('#main-menu').metisMenu();
    $110(window).bind("load resize", function () {
        if ($110(this).width() < 768) {
            $110('div.sidebar-collapse').addClass('collapse')
        } else {
            $110('div.sidebar-collapse').removeClass('collapse')
        }
    });
});


var planManage = {
    currentItem: null,
    fuzzySearch: true,
    getQueryCondition: function (data) {
        var param = {};
        param.stageId = getCookie("stageId");
        if(param.stageId===null||param.stageId.length<=0){
            $144.dialog.tips('获取不到stageId！');
            param.stageId=7;
        }
        //$144.dialog.tips("stageId="+param.stageId);
        //组装分页参数
        param.startIndex = data.start;
        param.pageSize = data.length;
        param.draw = data.draw;

        return param;
    },

    arrangeUnit: function (selectedItem) {
        var workplace = getCookie("workplace");
        var workplaceId = getCookie("workplaceId");
        if(workplace===null||workplace===""){
            $144.dialog.tips('请先选择派工工位！');
            return;
        }
        var message = "确定将批次:"+selectedItem.batchName+" 的单元:"+selectedItem.unitName+"派工给:"+workplace+"?";
        $144.dialog.confirm(message, function () {
            //$144.dialog.tips("i am in!");
            $.ajax({
                type : "get",
                url : urlArrangeUnitToWorkPlace,
                data :"unitId=" + selectedItem.unitId+"&workplaceId="+workplaceId,
                async : false,
                success : function(data){
                    $144.dialog.tips(data.message);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $144.dialog.alert("查询失败");
                    $wrapper.spinModal(false);
                }
            });
        });
    }
};