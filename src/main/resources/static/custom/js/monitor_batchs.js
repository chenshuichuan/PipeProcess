/*
* 作者：ricardo
* 描述：监控首页页面js
* 编写结构说明：
*     接口URL-->
*     页面加载-->
*     事件监听-->
*
**/
//查找船的加工情况，根据完成状态刷选
var urlGetBatchProcessInfo="/batch/processInfo"
var $wrapper = $('#table-panel-body');
$(document).ready(function(){
    /***
     * //batchTable
     */
    var $batchTable = $('#batch-table');
    var  batchTable = $batchTable.dataTable(
        $.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION,
            {
                lengthChange: false,
                paging: false,
                ordering: false,
                searching: false,
                info: false,
                //scrollX: false,
                ajax: function (data, callback, settings) {//ajax配置为function,手动调用异步查询
                    //手动控制遮罩
                    $wrapper.spinModal();
                    //封装请求参数
                    var param = batchTableManage.getQueryCondition(data);
                    $.ajax({
                        type: "GET",
                        url: urlGetBatchProcessInfo,
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
                            returnData.data = result.data;
                            //关闭遮罩
                            $wrapper.spinModal(false);
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
                        data: "shipName",
                        width: "220px"
                    },
                    {
                        data: "batchName",
                        width: "80px"
                    },
                    {
                        className: "ellipsis",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                        data: "batchDescription"
                    },
                    {
                        data: "unitNumber",
                        width: "100px"

                    },
                    {
                        data: "unitFinished",
                        width: "80px"
                    },
                    {
                        data: "unitFinishedRate",
                        width: "80px",
                        render: function (data, type, row, meta) {
                            return data*100 +"%";
                        }
                    },
                    {
                        data: "pipeNumber",
                        width: "100px"

                    },
                    {
                        data: "pipeFinished",
                        width: "80px"
                    },
                    {
                        data: "pipeFinishedRate",
                        width: "80px",
                        render: function (data, type, row, meta) {
                            return data*100 +"%";
                        }
                    },
                    {
                        data: "finishedTime",
                        width: "120px",
                        render: function (data, type, row, meta) {
                            return (data!=null&&data!=undefined)
                                ?dateToString(data):"---";
                        }
                    }
                ],
                "createdRow": function (row, data, index) {
                    if (data.finishedTime!=null&&data.finishedTime!=undefined) {
                        $(row).addClass("finished");
                    }else{
                        $(row).addClass("unfinished");
                    }
                },
                "drawCallback": function (settings) {
                    $("tbody tr", $batchTable).eq(0).click();
                }
            })
    ).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
    //初始化船选择框
    batchTableManage.initSeleteShip();

    // 监听ship选择框更改事件
    $("#selected-ship").change(function () {
        //重载表格数据
        batchTableManage.shipCode= $("#selected-ship").val();
        batchTable.draw();
    });
    // 监听ship选择框更改事件
    $("#selected-state").change(function () {
        //重载表格数据
        batchTableManage.isFinished= $("#selected-state").val();
        batchTable.draw();
    });
});

var batchTableManage = {
    currentItem: null,
    isFinished:-1,//点击完成1，未完成0
    shipCode: "",
    getQueryCondition: function (data) {
        var param = {};
        //组装参数
        param.isFinished = batchTableManage.isFinished;
        param.shipCode = batchTableManage.shipCode;
        param.draw = data.draw;
        return param;
    },
    initSeleteShip:function () {
        //渲染船选择框
        var shipList = getAllShip();

        var selector =  $("#selected-ship");
        selector.empty();
        var options = "";
        for (var i=0; i<shipList.length;i++){
            //此处渲染选择框 的显示仅做展示，实际是否需要 workplace 再说
            if(i==0) options+= "<option"+
                " value='"+shipList[i].shipCode+ "' selected = 'selected'>"+shipList[i].shipName
                +"</option>";
            else options+= "<option"+
                " value='"+shipList[i].shipCode+ "'>"+shipList[i].shipName
                +"</option>";
        }
        selector.append(options);
        selector.selectpicker('refresh');
    }
};