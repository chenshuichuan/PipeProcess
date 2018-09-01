/*
* 作者：ricardo
* 描述：在线套料页面js
* 编写结构说明：
*     接口URL-->
*     页面加载-->
*     事件监听-->
*
**/

//获取用户可查看的未完成派工记录，管理员获取所有，工段长获取工段内所有未完成，工人获取自己未完成
var urlGetUsersArrangeTable = "/taoliao/getUsersArrangeTable";
//根据列表加载该计划批次内所有的管材
var urlGetTaoliaoPage= "/taoliao/page";
//设置套料管材为已套料
var urlUpdateTaoliao = "/taoliao/update";

//var 根据套料id 获取该套料管材下对应的所有管件
var urlGetPipesByTaoliaoId="/taoliao/getPipesByTaoliaoId";

var $wrapper = $('#panel-div');

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

    /***
     * //pipeTable
     */
    var $pipeTable = $('#pipe-table');
    var  pipe_table = $pipeTable.dataTable(
        $.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION,
            {
                ajax: function (data, callback, settings) {//ajax配置为function,手动调用异步查询
                    //手动控制遮罩
                    $wrapper.spinModal();
                    //封装请求参数
                    var param = pipeTableManage.getQueryCondition(data);
                    $.ajax({
                        type: "GET",
                        url: urlGetPipesByTaoliaoId,
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
                        data: "pipeId",
                        width: "50px"
                    },
                    {
                        data: "batchName",
                        width: "80px"
                    },
                    {
                        data: "unitName",
                        width: "80px"
                    },
                    {
                        className: "ellipsis",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                        data: "pipeCode"

                    },
                    {
                        data: "cutLength",
                        width: "80px"
                    },
                    {
                        data: "isCutted",
                        width: "80px",
                        render: function (data, type, row, meta) {
                            return (data==1)?"<span style='color: #0b9612;'>完成</span>":"<span style='color: #e10b0f;'>未完成</span>";
                        }
                    },
                    {
                        className: "td-operation",
                        data: null,
                        defaultContent: "",
                        orderable: false,
                        width: "180px"
                    }
                ],
                "createdRow": function (row, data, index) {
                    //行渲染回调,在这里可以对该行dom元素进行任何操作
                    // //给当前行加样式
                    if (data.isCutted) {
                        $(row).addClass("text-success");
                    }
                    // //给当前行某列加样式
                    // $('td', row).eq(9).addClass(classIsCutted(data.isCutted));
                    //不使用render，改用jquery文档操作呈现单元格
                    var $btnPrint = $('<button type="button" class="btn btn-small btn-info btn-print">打印</button>');
                    var $btnLack = $('<button type="button" class="btn btn-small btn-danger btn-lack">缺件</button>');
                    var $btnMarkAsCutted = $('<button type="button" class="btn btn-small btn-primary btn-mark-as-cutted">标记为已下料</button>');
                    var $btnMarkUnCutted = $('<button type="button" class="btn btn-small btn-warning btn-mark-un-cutted">标记为未下料</button>');
                    if(data.isCutted==1)$('td', row).eq(6).append($btnMarkUnCutted);
                    else $('td', row).eq(6).append($btnMarkAsCutted);
                    $('td', row).eq(6).append($btnPrint).append($btnLack);

                },
                "drawCallback": function (settings) {
                    $("tbody tr", $pipeTable).eq(0).click();
                }
            })
    ).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

    $pipeTable.on("click", ".btn-mark-as-cutted", function () {
        //点击按钮
        var item = pipe_table.row($(this).closest('tr')).data();
        $(this).closest('tr').addClass("active").siblings().removeClass("active");
        //onlineCutManage.setTaoliao(item,1);
    }).on("click", ".btn-mark-un-cutted", function () {
        //点击按钮
        var item = pipe_table.row($(this).closest('tr')).data();
        $(this).closest('tr').addClass("active").siblings().removeClass("active");
        //onlineCutManage.setTaoliao(item,0);
    });


    /**
     * material-table
     */
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
                        $(row).addClass("text-success");
                    }
                    // //给当前行某列加样式
                    // $('td', row).eq(9).addClass(classIsCutted(data.isCutted));
                    //不使用render，改用jquery文档操作呈现单元格
                    var $btnPrint = $('<button type="button" class="btn btn-small btn-primary btn-print">打印</button>');
                    var $btnLack = $('<button type="button" class="btn btn-small btn-danger btn-lack">缺件</button>');
                    var $btnMarkAsTaoliao = $('<button type="button" class="btn btn-small btn-primary btn-mark-as-taoliao">标记为已套料</button>');
                    var $btnMarkUnTaoliao = $('<button type="button" class="btn btn-small btn-warning btn-mark-un-taoliao">标记为未套料</button>');
                    if(data.isTaoliao==0)$('td', row).eq(5).append($btnMarkAsTaoliao);
                    else $('td', row).eq(5).append($btnMarkUnTaoliao);

                },
                "drawCallback": function (settings) {
                   $("tbody tr", $materialTable).eq(0).click();
                }
            })
    ).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
    // 行点击事件
    $("tbody",  $materialTable).on("click", "tr", function (event) {
       // $(this).addClass("active").siblings().removeClass("active");
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            material_table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
        //获取该行对应的数据
        var item = material_table.row($(this).closest('tr')).data();
        onlineCutManage.currentItem = item;
        onlineCutManage.showMaterial(item);
        //重新加载pipeTable表格
        pipe_table.draw();
    });
    $materialTable.on("click", ".btn-mark-as-taoliao", function () {
        //点击按钮
        var item = material_table.row($(this).closest('tr')).data();
        //$(this).closest('tr').addClass("active").siblings().removeClass("active");
        onlineCutManage.setTaoliao(item,1);
    }).on("click", ".btn-mark-un-taoliao", function () {
        //点击按钮
        var item = material_table.row($(this).closest('tr')).data();
        $(this).closest('tr').addClass("active").siblings().removeClass("active");
        onlineCutManage.setTaoliao(item,0);
    });

    //监听plan选择框更改事件
    $("#selected-plan").change(function () {
        //重载表格数据
        material_table.draw();
    });
    //监听plan选择框更改事件
    $("#selected-IsTaoliao").change(function () {
        //重载表格数据
        material_table.draw();
    });


    /***
     * 输入原材料的表格
     */
    var $input_table = $('#input-table');
    var inputTable = $input_table.dataTable({
        lengthChange: false,
        paging: false,
        ordering: false,
        searching: false,
        stateSave: true,//未起作用
        columns: [{}, {width: "150px"}, {width: "120px"}
        // , {
        //         className: "td-operation",
        //         data: null,
        //         defaultContent: "",
        //         width: "80px"
        //     }
        ],
        "createdRow": function (row, data, index) {
            //行渲染回调,在这里可以对该行dom元素进行任何操作
            var $btnDelete = $('<button type="button" class="btn btn-small btn-warning btn-delete">删除</button>');
            //$('td', row).eq(3).append($btnDelete);
        },
        "drawCallback": function (settings) {
            $("tbody tr", $input_table).eq(0).click();
        }
    }
    ).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象;
    $input_table.on("click", ".btn-delete", function () {
        //点击按钮
        //var item = inputTable.row($(this).closest('tr')).data();
        inputTable.row('.selected').remove().draw( false );
    });
    //输入框初始化时先从本地 ，就是刷新时,取出加载到表格中，取出的数据为','符号分隔的字符串！
    var inputList = getCookie("inputTable");
    if(inputList!=null&&inputList!=undefined&&inputList.length>0){
        var data = inputList.split(',');
        for(var i=0;i<data.length-2;i+=3){
            inputTable.row.add(
                [data[i],data[i+1],data[i+2]]
            ).draw();
        }
    }
    $('#input-table tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            inputTable.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );

    //输入表格删除行
    $('.btn-delete').click( function () {
        inputTable.row('.selected').remove().draw( false );
    } );
    //输入表格删除行
    $('#btn-delete-row').click( function () {
        inputTable.row('.selected').remove().draw( false );
        //保存输入表数据，刷新重画方便输入者
        var data = inputTable.data().toArray();
        setCookie("inputTable",data);
    } );
    //输入表格删除所有行，并清空cookie
    $('#btn-delete-all').click( function () {
        setCookie("inputTable",null);
        inputTable.clear().draw();
    } );
    //输入表格添加一行
    $('#btn-add-row').on( 'click', function () {
        var pipeMaterial = onlineCutManage.currentItem.pipeMaterial;
        var length = $("#input-material-length").val();
        var number = $("#input-material-number").val();
        inputTable.row.add( [
            pipeMaterial,length,number
        ] ).draw();
        var data = inputTable.data().toArray();
        //保存输入表数据，刷新重画方便输入者
        setCookie("inputTable",data);
    } );

    //套料按钮
    $('#btn-taoliao').click( function () {
        var data = inputTable.data().toArray();
        data.forEach(function(row, i) {
            row.forEach(function(column, j) {
                console.log('row ' + i + ' column ' + j + ' value ' + column);
            });
            console.log("row="+row);
        });
    } );
});


var onlineCutManage = {
    currentItem: null,
    getQueryCondition: function (data) {
        var param = {};
        param.planId = $("#selected-plan").val();
        param.isTaoliao = $("#selected-IsTaoliao").val();
        //组装分页参数
        param.startIndex = data.start;
        param.pageSize = data.length;
        param.draw = data.draw;
        return param;
    },
    setTaoliao: function (item,isTaoliao) {
        var message = "确定要设置该批次管材为已套料状态吗？";
        $.dialog.confirm(message, function () {
            $.ajax({
                type : "get",
                url : urlUpdateTaoliao,
                data :"id=" + item.id+"&isTaoliao="+isTaoliao,
                async : false,
                success : function(data){
                    if(data.result!=1)$.dialog.tips(data.message);
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
    },
    showMaterial:function (item) {
        $("#material-title").text(item.pipeMaterial);
        $("#view-pipe-material").val(item.pipeMaterial);
        $("#view-total-length").val(item.totalLength);
        $("#view-lack-length").val(0);
    }
};

//获取派工表中类型为批次，未完成的派工
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

var pipeTableManage = {
    currentItem: null,
    getQueryCondition: function (data) {
        var param = {};
        if(onlineCutManage.currentItem!=null)
            param.taoliaoId = onlineCutManage.currentItem.id;
        else param.taoliaoId=0;
        //组装分页参数
        param.startIndex = data.start;
        param.pageSize = data.length;
        param.draw = data.draw;
        return param;
    },
    setCutted: function (item,isCutted) {
        var message = "确定要设置该批次管材为已套料状态吗？";
        $.dialog.tip(message);
        // $.dialog.confirm(message, function () {
        //     $.ajax({
        //         type : "get",
        //         url : urlUpdateTaoliao,
        //         data :"id=" + item.id+"&isTaoliao="+isTaoliao,
        //         async : false,
        //         success : function(data){
        //             if(data.result!=1)$.dialog.tips(data.message);
        //         },
        //         error: function (XMLHttpRequest, textStatus, errorThrown) {
        //             $.dialog.alert("查询失败");
        //             $wrapper.spinModal(false);
        //         }
        //     });
        // });
    },
    showPipesOfMaterial:function (item) {
        $.dialog.tips("show test!")
    }
};