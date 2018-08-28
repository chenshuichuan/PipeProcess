/*
* 作者：ricardo
* 描述：在线套料页面js
* 编写结构说明：
*     接口URL-->
*     页面加载-->
*     事件监听-->
*
**/

//获取某工序下可派工的所有单元
var urlGetUsersArrangeTable = "/taoliao/getUsersArrangeTable";
//根据列表加载该计划批次内所有的管材
var urlGetTaoliaoPage= "/taoliao/page";

var $wrapper = $('#material-table');

$(document).ready(function(){
    //侧边栏 //侧边栏用的是jq1.11 和ztree的1.44不兼容
    $(".dropdown-button").dropdown();
    $("#sideNav").click(function(){
        if($(this).hasClass('closed')){
            $('.navbar-side').animate({left: '0px'});
            $(this).removeClass('closed');
            $('#page-wrapper').animate({'margin-left' : '260px'});

        }
        else{
            $(this).addClass('closed');
            $('.navbar-side').animate({left: '-260px'});
            $('#page-wrapper').animate({'margin-left' : '0px'});
        }
    });
    /*MENU
        ------------------------------------*/
    $('#main-menu').metisMenu();
    $(window).bind("load resize", function () {
        if ($(this).width() < 768) {
            $('div.sidebar-collapse').addClass('collapse')
        } else {
            $('div.sidebar-collapse').removeClass('collapse')
        }
    });
    //初始化plan选择框
    onlineCutManage.initSeletePlan();

    //material-table
    var $materialTable = $('#material-table');
    var  material_table = $materialTable.dataTable(
        $.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION,
            {
                ajax: function (data, callback, settings) {//ajax配置为function,手动调用异步查询
                    //手动控制遮罩
                    $wrapper.spinModal();
                    //封装请求参数
                    var param = onlineCutManage.getQueryCondition(data);
                    $.ajax({
                        type: "GET",
                        url: urlGetTaoliaoPage,
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
                    {
                        data: "id",
                        width: "50px"
                    },
                    {
                        className: "ellipsis",	//文字过长时用省略号显示，CSS实现
                        data: "pipeMaterial",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,//会显示省略号的列，需要用title属性实现划过时显示全部文本的效果
                        width: "150px"
                    },
                    {
                        data: "totalLength",
                        width: "80px"
                    },
                    {
                        className: "ellipsis",
                        data: "pipeNumber",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                        width: "80px"
                    },
                    {
                        className: "ellipsis",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                        data: "isTaoliao",
                        width: "80px",
                        render: function (data, type, row, meta) {
                            return data?"<span style='color: #0ae113;'>是</span>":"<span style='color: #e1b611;'>否</span>";
                        }
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
                    if (data.isTaoliao) {
                        $(row).addClass("text-info");
                    }
                    // //给当前行某列加样式
                    // $('td', row).eq(9).addClass(classIsCutted(data.isCutted));
                    //不使用render，改用jquery文档操作呈现单元格
                    var $btnPrint = $('<button type="button" class="btn btn-small btn-primary btn-print">打印</button>');
                    var $btnLack = $('<button type="button" class="btn btn-small btn-danger btn-lack">缺件</button>');
                    var $btnMarkAsTaoliao = $('<button type="button" class="btn btn-small btn-primary btn-mark-as-taoliao">已套料</button>');
                    $('td', row).eq(5).append($btnMarkAsTaoliao);

                },
                "drawCallback": function (settings) {
                   $("tbody tr", $materialTable).eq(0).click();
                }
            })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

    $materialTable.on("click", ".btn-mark-as-taoliao", function () {
        //点击按钮
        var item = material_table.row($(this).closest('tr')).data();
        $(this).closest('tr').addClass("active").siblings().removeClass("active");
        planManage.arrangeUnit(item);
    });

    //监听plan选择框更改事件
    $("#selected-plan").change(function () {

        //重载表格数据
        material_table.draw();
    });
});


var onlineCutManage = {
    currentItem: null,
    getQueryCondition: function (data) {
        var param = {};
        param.planId = $("#selected-plan").val();
        param.isTaoliao = $("#selected-istaoliao").val();
        
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
            $.dialog.tips('请先选择派工工位！');
            return;
        }
        var message = "确定将批次:"+selectedItem.batchName+" 的单元:"+selectedItem.unitName+"派工给:"+workplace+"?";
        $.dialog.confirm(message, function () {
            //$.dialog.tips("i am in!");
            $.ajax({
                type : "get",
                url : urlArrangeUnitToWorkPlace,
                data :"unitId=" + selectedItem.unitId+"&workplaceId="+workplaceId,
                async : false,
                success : function(data){
                    $.dialog.tips(data.message);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $.dialog.alert("查询失败");
                    $wrapper.spinModal(false);
                }
            });
        });
    },

    initSeletePlan:function () {
        //根据批次派工计划列表渲染选择框
        var arrangePlan = getUsersArrangeTable();

        var selector =  $("#selected-plan");
        selector.empty();
        var options = "<option value=''>所有</option>";
        for (var i=0; i<arrangePlan.length;i++){
            //此处渲染选择框 的显示仅做展示，实际是否需要 workplace 再说
            options+= "<option"+
                " value='"+arrangePlan[i].planId+ "'>"+arrangePlan[i].name+":"+arrangePlan[i].workplace
                +"</option>";
        }
        selector.append(options);
        selector.selectpicker('refresh');
    }
};

//获取某工序下可派工的所有单元
function getUsersArrangeTable() {
    var arrangePlan =null;
    //设置同步
    $.ajax({
        type : "get",
        url : urlGetUsersArrangeTable,
        data :"arrangeType="+1+"&isFinished=0",
        async : false,
        success : function(data){
            if(data.result===1) arrangePlan = data.data;
            else{
                $.dialog.tips(data.message);
            }
        }
    });
    return arrangePlan;
}