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
var urlGetShipProcessingInfo="/ship/processingInfo"
var $wrapper = $('#table-panel-body');
$(document).ready(function(){
    /***
     * //shipTable
     */
    // $(document).ready(function() {
    //     $('#ship-table').DataTable();
    // } );
    var $shipTable = $('#ship-table');
    var  ship_table = $shipTable.dataTable(
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
                    var param = shipTableManage.getQueryCondition(data);
                    $.ajax({
                        type: "GET",
                        url: urlGetShipProcessingInfo,
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
                        data: "shapeShipId",
                        width: "80px"
                    },
                    {
                        data: "shipCode",
                        width: "120px"
                    },
                    {
                        className: "ellipsis",
                        render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                        data: "shipName"
                    },
                    {
                        data: "batchNumber",
                        width: "100px"

                    },
                    {
                        data: "batchFinished",
                        width: "80px"
                    }
                    ,
                    {
                        data: "batchFinishedRate",
                        width: "80px",
                        render: function (data, type, row, meta) {
                            return data*100 +"%";
                        }
                    },
                    {
                        data: "unitNumber",
                        width: "100px"

                    },
                    {
                        data: "unitFinished",
                        width: "80px"
                    }
                    ,
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
                    $("tbody tr", $shipTable).eq(0).click();
                }
            })
    ).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

    //刷选 完成的船
    $('#btn-finished-ship').click( function () {
        shipTableManage.isFinished=1;
       ship_table.draw();
    } );
    //未完成的船
    $('#btn-unfinished-ship').click( function () {
        shipTableManage.isFinished=0;
        ship_table.draw();
    } );
});

var shipTableManage = {
    currentItem: null,
    isFinished:-1,//点击完成1，未完成0
    getQueryCondition: function (data) {
        var param = {};
        //组装参数
        param.isFinished = shipTableManage.isFinished;
        param.draw = data.draw;
        return param;
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