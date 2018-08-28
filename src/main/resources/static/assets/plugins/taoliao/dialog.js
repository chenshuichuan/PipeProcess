/**
 * Created by BlueFisher on 2017/6/7 0007.
 */

var DiagApp = {
    init: function () {
        this.render();
    },
    render: function () {

    },
    getId: function (diag, Id) {
        var id = diag.innerFrame.contentWindow.document.getElementById(Id);
        return id;
    },
    open1: function () {
        Dialog.open({URL: "https://translate.google.cn/#zh-CN/en/%E6%89%93%E7%A3%A8"});
    },
    open2:function () {
        var diag = new Dialog();
        diag.Height = 300;
        diag.Title = "生产计划上传";
        diag.URL = "./selectStation.html";
        diag.OKEvent = function () {
            Dialog.close();
            window.location.reload();
        };
        diag.show();
    },
    open4: function () {
        var diag = new Dialog();
        // var gw_id = window.localStorage ? localStorage.getItem("gw_id") : Cookie.read("gw_id");
        diag.Width = 700;
        diag.Height = 300;
        diag.Title = "加工单元";
        diag.URL = "./selectgw.html";
        diag.OKEvent = function () {
            var u_id = window.localStorage?localStorage.getItem("u-id"):Cookie.read("u-id");
            var gw_id = window.localStorage?localStorage.getItem("gw_id"):Cookie.read("gw_id");
            // console.log(u_id,gw_id)
            Arrangement.postUnit(u_id,gw_id,success);
            Dialog.close();
            window.location.reload();
        };
        diag.show();
    },
    open5: function () {
        var diag = new Dialog();
        diag.Width = 500;
        diag.Height = 200;
        diag.Title = "故障记录";
        diag.URL = "./recordgz.html";
        diag.OKEvent = function () {
            var form = DiagApp.getId(diag, 'form');
            var $form = $(form);
            var time = DiagApp.getId(diag, 'returnTime');
            var $time = $(time);
            var val = $time[0].value;
            //时间转换为时间戳
            $time.val(shijianchuo(val));
            var data = $form.serializeObject();
            var G_data = JSON.stringify(data);
            // var G_data =  genJson(diag);
            //上传故障记录
            $.ajax({
                type: 'POST',
                contentType: "application/json",
                url: '/insertReworkTable',
                data: G_data,
                success: alert('操作成功！！！')
            });
            Dialog.close();
            window.location.reload();           //成功后刷新页面
        };//点击确定后调用的方法
        diag.show();
    },
    open6: function () {
        var diag = new Dialog();
        diag.Width = 500;
        diag.Height = 300;
        diag.Title = "计划添加";
        diag.URL = "./rcplan.html";
        diag.OKEvent = function () {
            // window.location.reload();
        };//点击确定后调用的方法
        diag.show();
    },
    open7: function () {
        var diag = new Dialog();
        diag.Width = 665;
        diag.Height = 455;
        diag.Title = "管附件集配信息表(新增)";
        diag.URL = "./EditQueJian.html";
        diag.OKEvent = function () {
            var form = DiagApp.getId(diag, 'form1');
            var $form = $(form);
            var data = $form.serializeObject();
            var currentDate = new Date();
            data.updateTime = util.convertDateToJsonDate(currentDate);
            data.receiveTime = util.convertDateToJsonDate(currentDate);
            var jsonData = JSON.stringify(data);
            console.log("data:", jsonData);
            Attachment.insertAttachmentHubHeader(jsonData);
            window.location.reload();           //成功后刷新页面
            diag.close();
        };//点击确定后调用的方法
        diag.show();
        diag.OnLoad = function () {
            var form = DiagApp.getId(diag, 'form1');
            $(':input', form)
                .not(':button, :submit, :reset, :hidden')
                .attr('value','')
                .removeAttr('checked')
                .removeAttr('selected');
        };
    },
    open8: function () {
        var diag = new Dialog();
        diag.Width = 665;
        diag.Height = 425;
        diag.Title = "新增用户";
        diag.URL = "./AddGongRen.html";
        diag.OKEvent = function () {
            var form = DiagApp.getId(diag, 'form1');
            var $form = $(form);
            var data = $form.serializeObject();
            var currentDate = new Date();
            data.updateTime = util.convertDateToJsonDate(currentDate);
            var jsonData = JSON.stringify(data);
            console.log("data:", jsonData);
            User.insertWorkers(jsonData);
            window.location.reload();           //成功后刷新页面
            diag.close();
        };//点击确定后调用的方法
        diag.show();
        diag.OnLoad = function () {
            var form = DiagApp.getId(diag, 'form1');
            $(':input', form)
                .not(':button, :submit, :reset, :hidden')
                .attr('value','')
                .removeAttr('checked')
                .removeAttr('selected');
        };
    },
    open9: function () {
        var diag = new Dialog();
        diag.Width = 665;
        diag.Height = 425;
        diag.Title = "编辑用户";
        diag.URL = "./AddGongRen.html";
        diag.OKEvent = function () {
            var form = DiagApp.getId(diag, 'form1');
            var $form = $(form);
            var data = $form.serializeObject();
            var currentDate = new Date();
            data.updateTime = util.convertDateToJsonDate(currentDate);
            var jsonData = JSON.stringify(data);
            console.log("data:", jsonData);
            User.updateWorkers(jsonData);
            window.location.reload();           //成功后刷新页面
            diag.close();
        };//点击确定后调用的方法
        diag.show();
    },
    open10: function () {
        var diag = new Dialog();
        diag.Width = 985;
        diag.Height = 455;
        diag.Title = "管附件集配信息表";
        diag.URL = "./DetailQueJian.html";
        diag.ShowButtonRow=true;
        diag.OKEvent = function () {
            var data = window.sessionStorage? sessionStorage.getItem("UpAttachmentHubBody"): util.readCookie("UpAttachmentHubBody");
            data = JSON.parse(data);
            console.log("AttachmentHubBody:", JSON.stringify(data));
            Attachment.insertAttachmentHubBody(data);
            window.location.reload();           //成功后刷新页面
            diag.close();
        };//点击确定后调用的方法
        diag.show();
        diag.okButton.value=" 更新 ";
    },
    open11: function () {
        var diag = new Dialog();
        diag.Width = 665;
        diag.Height = 455;
        diag.Title = "管附件集配信息表(编辑)";
        diag.URL = "./EditQueJian.html";
        diag.OKEvent = function () {
            var form = DiagApp.getId(diag, 'form1');
            var $form = $(form);
            var data = $form.serializeObject();
            var currentDate = new Date();
            data.updateTime = util.convertDateToJsonDate(currentDate);
            data.receiveTime = util.convertDateToJsonDate(data.receiveTime);
            var jsonData = JSON.stringify(data);
            console.log("data:", jsonData);
            Attachment.updateAttachmentHubHeader(jsonData);
            // form.reset();
            window.location.reload();           //成功后刷新页面
            diag.close();
        };//点击确定后调用的方法
        diag.show();
    },
    open12: function () {
        var diag = new Dialog();
        diag.Width = 665;
        diag.Height = 455;
        diag.Title = "厂家配送管业欠缺台账";
        diag.URL = "./EditQueJian2.html";
        diag.OKEvent = function () {
            var form = DiagApp.getId(diag, 'form1');
            var $form = $(form);
            var data = $form.serializeObject();
            var currentDate = new Date();
            data.confirmTime = util.convertDateToJsonDate(data.confirmTime);
            data.beginRestrictions = util.convertDateToJsonDate(data.beginRestrictions);
            data.arriveRestriction = util.convertDateToJsonDate(data.arriveRestriction);
            data.actualArriveTime = util.convertDateToJsonDate(data.actualArriveTime);
            data.updateTime = util.convertDateToJsonDate(currentDate);
            var jsonData = JSON.stringify(data);
            console.log("data:", jsonData);
            Attachment.updateFactoryDistribution(jsonData);
            // form.reset();
            window.location.reload();           //成功后刷新页面
            diag.close();
        };//点击确定后调用的方法
        diag.show();
    },
    open13: function () {
        var diag = new Dialog();
        diag.Width = 665;
        diag.Height = 455;
        diag.Title = "厂家配送管业欠缺台账(新增)";
        diag.URL = "./EditQueJian2.html";
        diag.OKEvent = function () {
            var form = DiagApp.getId(diag, 'form1');
            var $form = $(form);
            var $form = $(form);
            var data = $form.serializeObject();
            var currentDate = new Date();
            data.confirmTime = util.convertDateToJsonDate(data.confirmTime);
            data.beginRestrictions = util.convertDateToJsonDate(data.beginRestrictions);
            data.arriveRestriction = util.convertDateToJsonDate(data.arriveRestriction);
            data.actualArriveTime = util.convertDateToJsonDate(data.actualArriveTime);
            data.updateTime = util.convertDateToJsonDate(currentDate);
            var jsonData = JSON.stringify(data);
            console.log("data:", jsonData);
            Attachment.insertFactoryDistribution(jsonData);
            // form.reset();
            window.location.reload();           //成功后刷新页面
            diag.close();
        };//点击确定后调用的方法
        diag.show();
        diag.OnLoad = function () {
            var form = DiagApp.getId(diag, 'form1');
            $(':input', form)
                .not(':button, :submit, :reset, :hidden')
                .attr('value','')
                .removeAttr('checked')
                .removeAttr('selected');
        };
    },
    open14:function () {
        var diag = new Dialog();
        diag.Width = 365;
        diag.Height = 255;
        diag.Title = "补打条形码";
        diag.URL = "./PrintBar.html";
        diag.OKEvent = function () {
            $("#barCodeDiv").jqprint({
                debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
                importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
                printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
                operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
            });
        };//点击确定后调用的方法
        diag.CancelEvent = function () {
            diag.close();
        };
        diag.show();
        diag.okButton.value="打 印";
    },
    open15:function () {
        var diag = new Dialog();
        diag.Width = 700;
        diag.Height = 300;
        diag.Title = "加工单元仓库资材信息";
        diag.URL = "./storemsg.html";
        diag.OKEvent = function () {
            window.location.reload();
            diag.close();
        };
        diag.show();
    },
    open16:function () {
        var diag = new Dialog();
        diag.Width = 700;
        diag.Height = 300;
        diag.Title = "加工单元厂家资材信息";
        diag.URL = "./factorymsg.html";
        diag.OKEvent = function () {
            window.location.reload();
            diag.close();
        };
        diag.show();
    }
};

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function closdlg() {
    Dialog.close();
}
function getId(diag, Id) {
    var id = diag.innerFrame.contentWindow.document.getElementById(Id);
    return id;
}
function shijianchuo(time) {
    var t = new Date(time);
    return t.getTime();
}
//显示工位
function getWorkSpace(process) {
    var b = 0;
    var process = '/selectWorkerplaceByProcess?process=' + process;
    $.getJSON(process, function (obj) {
        if (obj.status) {
            $.each(obj.data, function (i, item) {
                $("#gongwei").append('<tr>');
                $("#gongwei tr").eq(i).append("<td><i class='nomark ops' id='gw" + (b + 1) + "'></i></td>");
                $("#gongwei tr").eq(i).append('<td>' + item.workplace + '</td>');
                $("#gongwei tr").eq(i).append('<td>' + item.workersId + '</td>');
                $("#gongwei tr").eq(i).append('<td>' + item.units + '</td>');
                b++;
            })
        }
    });

}
//派工成功
function success() {
    alert("派工成功");
}



//封装数据为JSON
function genJson() {
    var obj = new Object();
    var s_name = window.localStorage?localStorage.getItem("ship"):Cookie.read("ship");
    var p_name = window.localStorage?localStorage.getItem("pici"):Cookie.read("pici");
    var u_name = window.localStorage?localStorage.getItem("unit"):Cookie.read("unit");
    var location = window.localStorage?localStorage.getItem("process"):Cookie.read("process");
    var p_time = window.localStorage?localStorage.getItem("time"):Cookie.read("time");

    obj.shipCode = s_name;
    obj.batchName= p_name;
    obj.unitName = u_name;
    obj.currentLocation = location;
    obj.weldEndTime = p_time;
    var postdata = JSON.stringify(obj);
    return postdata;


}


