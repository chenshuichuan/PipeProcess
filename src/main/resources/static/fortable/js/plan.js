/*
* 作者：ricardo
* 描述：计划管理页面js
* 编写结构说明：
*     接口URL-->
*     页面加载-->
*     事件监听-->
**/

var urlPage = "/plan/page";
var urlDelete = "/plan/deleteById";
var urlUpdate = "/plan/update";

var $wrapper = $('#div-table-container');

$(function () {
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
            var $btnEdit = $('<button type="button" class="btn btn-small btn-primary btn-edit">修改</button>');
            var $btnDel = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
            $('td', row).eq(10).append($btnEdit).append($btnDel);

        },
        "drawCallback": function (settings) {
            //渲染完毕后的回调
            //清空全选状态
            $(":checkbox[name='cb-check-all']", $wrapper).prop('checked', false);
            //默认选中第一行
            $("tbody tr", $table).eq(0).click();
        }
    })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

    // $("#btn-add").click(function () {
    //     planManage.addItemInit();
    // });

    $("#btn-del").click(function () {
        var arrItemId = [];
        $("tbody :checkbox:checked", $table).each(function (i) {
            var item = _table.row($(this).closest('tr')).data();
            arrItemId.push(item);
        });
        planManage.deleteItem(arrItemId);
    });

    $("#btn-simple-search").click(function () {
        planManage.fuzzySearch = true;

        //reload效果与draw(true)或者draw()类似,draw(false)则可在获取新数据的同时停留在当前页码,可自行试验
//		_table.ajax.reload();
//		_table.draw(false);
        _table.draw();//触发表格数据重新加载
    });

    $("#btn-advanced-search").click(function () {
        planManage.fuzzySearch = false;
        _table.draw();
    });

    // $("#btn-save-add").click(function () {
    //     planManage.addItemSubmit();
    // });

    $("#btn-save-edit").click(function () {
        planManage.editItemSubmit();
    });

    //行点击事件
    $("tbody", $table).on("click", "tr", function (event) {
        $(this).addClass("active").siblings().removeClass("active");
        //获取该行对应的数据
        var item = _table.row($(this).closest('tr')).data();
        planManage.currentItem = item;
        planManage.showItemDetail(item);
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
        //点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
        $(this).closest('tr').addClass("active").siblings().removeClass("active");
        planManage.currentItem = item;
        planManage.editItemInit(item);
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        $(this).closest('tr').addClass("active").siblings().removeClass("active");
        planManage.deleteItem([item]);
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

    $("#btn-view-edit").click(function () {
        planManage.editItemInit(planManage.currentItem);
    });

    $(".btn-cancel").click(function () {
        planManage.showItemDetail(planManage.currentItem);
    });
    $("#btn-refresh").click(function () {
        window.location.reload();
    });

});


var planManage = {
    currentItem: null,
    fuzzySearch: true,
    getQueryCondition: function (data) {
        var param = {};
        //组装排序参数
        if (data.order && data.order.length && data.order[0]) {
            switch (data.order[0].column) {
                case 1:
                    param.orderColumn = "serialNumber";
                    break;
                case 2:
                    param.orderColumn = "shipName";
                    break;
                case 3:
                    param.orderColumn = "batchName";
                    break;
                case 4:
                    param.orderColumn = "batchDescription";
                    break;
                case 5:
                    param.orderColumn = "processPlace";
                    break;
                case 6:
                    param.orderColumn = "number";
                    break;
                case 7:
                    param.orderColumn = "planStart";
                    break;
                case 8:
                    param.orderColumn = "planEnd";
                    break;
                case 9:
                    param.orderColumn = "isCutted";
                    break;
                default:
                    param.orderColumn = "serialNumber";
                    break;
            }
            param.orderDir = data.order[0].dir;
        }
        // //组装查询参数
        // param.fuzzySearch = userManage.fuzzySearch;
        // if (userManage.fuzzySearch) {
        //     param.fuzzy = $("#fuzzy-search").val();
        // } else {
        //     param.name = $("#name-search").val();
        //     param.code = $("#code-search").val();
        //     param.job = $("#job-search").val();
        //     param.departments = $("#departments-search").val();
        //     param.state = $("#state-search").val();
        //     param.role = $("#role-search").val();
        // }
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
    addItemInit: function () {
        $("#form-add")[0].reset();
        $("#plan-add").show().siblings(".info-block").hide();
    },
    editItemInit: function (item) {
        if (!item) {
            return;
        }
        $("#form-edit")[0].reset();
        $("#title-edit").text(item.planName);

        $("#serial-edit").val(item.serialNumber);
        $("#planName-edit").val(item.planName);
        $("#batchName-edit").val(item.batchName);
        $("#processPlace-edit").val(item.processPlace);
        $("#lightBodyPipe-edit").val(item.lightBodyPipe);
        $("#planStart-edit").val(dateToString(item.planStart));
        $("#planEnd-edit").val(dateToString(item.planEnd));
        $("#sendPicTime-edit").val(dateToString(item.sendPicTime));
        $("#oneBCutNum-edit").val(item.oneBcutNum);
        $("#oneBendCut-edit").val(item.oneBendCut);
        $("#oneVerCut-edit").val(item.oneVerCut);
        $("#oneBigCut-edit").val(item.oneBigCut);
        $("#twoSpeBendCut-edit").val(item.twoSpeBendCut);
        $("#twoSpeVerCut-edit").val(item.twoSpeVerCut);
        $("#stocks-edit").val(item.stocks);
        $("#sections-edit").val(item.sections);
        //填充单元名
        $("#units-edit").val(getUnitsName(item.id));
        $("#remark-edit").val(item.remark);

        $("#plan-edit").show().siblings(".info-block").hide();
    },
    addItemSubmit: function () {
        $.dialog.tips("添加成功！(测试)");
    },
    editItemSubmit: function () {
        var serialNumber = $("#serial-edit").val();
        var planName = $("#planName-edit").val();
        var batchName= $("#batchName-edit").val();
        var processPlace = $("#processPlace-edit").val();
        var lightBodyPipe = $("#lightBodyPipe-edit").val();
        //日期数据要注意和后台一致
        var planStart = $("#planStart-edit").val();
        var planEnd = $("#planEnd-edit").val();
        var sendPicTime = $("#sendPicTime-edit").val();
        //var oneBCutNum = $("#oneBCutNum-edit").val();
        var oneBendCut = $("#oneBendCut-edit").val();
        var oneVerCut= $("#oneVerCut-edit").val();
        var oneBigCut = $("#oneBigCut-edit").val();
        var twoSpeBendCut= $("#twoSpeBendCut-edit").val();
        var twoSpeVerCut =$("#twoSpeVerCut-edit").val();
        var stocks = $("#stocks-edit").val();
        var sections = $("#sections-edit").val();
        //var units = $("#units-edit").val();
        $("#remark-edit").val();
        $.post(urlUpdate,
            {
                "serialNumber": serialNumber, "planName": planName, "batchName": batchName,
                "processPlace": processPlace, "lightBodyPipe": lightBodyPipe,
                "actualStart": planStart, "actualEnd": planEnd, "sendPicTime": sendPicTime,
                 "oneBendCut": oneBendCut, "oneVerCut": oneVerCut,
                "oneBigCut": oneBigCut, "twoSpeBendCut": twoSpeBendCut, "twoSpeVerCut": twoSpeVerCut,
                "stocks": stocks,"sections": sections
            },
            function (data, status) {
                $.dialog.tips(data.message);
                //要不要重新加载页面呢？
            });
    },
    deleteItem: function (selectedItems) {
        var message;
        if (selectedItems && selectedItems.length) {
            if (selectedItems.length === 1) {
                message = "确定要删除序号 '" + selectedItems[0].serialNumber + "' 的数据吗?";

            } else {
                message = "确定要删除选中的" + selectedItems.length + "项记录吗?";
            }
            $.dialog.confirmDanger(message, function () {
                //手动控制遮罩
                $wrapper.spinModal();
                for (var i = 0; i < selectedItems.length; i++) {
                    //alert("id="+selectedItems[i].id) //可以在此直接拿到di，所以接口可以改为 deleteByid
                    //var url = urlDelete + "?id=" + selectedItems[i].id ;
                    //设置同步，否则，页面上删除第一条后其他的仍残留
                    $.ajax({
                        type : "get",
                        url : urlDelete,
                        data :"id=" + selectedItems[i].id,
                        async : false,
                        success : function(data){
                            $.dialog.tips(data.message);
                        }
                    });
                    // $.get(url, function (data, status) {
                    //     //alert("数据: " + data + "\n状态: " + status);
                    //     $.dialog.tips(data.message);
                    // });
                }
                //手动控制遮罩
                $wrapper.spinModal(false);
                window.location.reload();
            });
        } else {
            $.dialog.tips('请先选中要删除的行');
        }
    }
};

// //判断下料状态
// function stringIsCutted(isCutted) {
//     if (isCutted === 1) return "下料完成";
//     else if (isCutted === 0) return "已派工";
//     else if (isCutted === -1) return "未开始";
//     else return "未知";
// }
//
// //根据下料状态返回渲染的css class
// function classIsCutted(isCutted) {
//     if (isCutted === 1) return "text-success";
//     else if (isCutted === 0) return "text-info";
//     else if (isCutted === -1) return "text-warning";
//     else return "text-error";
// }
// //根据planId获取该plan包含的所有单元对象
// function getUnits(planId) {
//     var units =null;
//     //设置同步
//     $.ajax({
//         type : "get",
//         url : urlGetUnits,
//         data :"planId=" + planId,
//         async : false,
//         success : function(data){
//             units = data.data;
//         }
//     });
//     return units;
// }
// //根据planId获取该plan包含的所有单元名称
// function getUnitsName(planId) {
//     var units = getUnits(planId);
//     var unitNames="";
//     for(var i = 0;i<units.length;i++){
//         unitNames+=units[i].unitName+",";
//     }
//     return unitNames;
// }