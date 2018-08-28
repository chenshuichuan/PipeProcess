/**
 * Created by BlueFisher on 2017/6/11 0011.
 */
var util = {
    init: function () {
        //初始化
        this.render();
    },
    render: function () {
        // this.hoverAndSelect();
        this.bind();
    },
    bind: function () {
        var self = this;
        $(".ce > li").click(function () {
            $(this).children().siblings(".er").toggle(300);
        });
        // $("#reload")
        //     .click('on', function () {
        //         util.beforeWebOnLoad();
        //     });
    },
    beforeWebOnLoad: function () {
        var _TabHeight = window.outerHeight - $('#header').height() * 2,
            _TabWidth = window.outerWidth - $('.aside').width();
        var _offsetLeft = $('.aside').width() !== null ? $('.aside').width() : 0,
            _offsetTop = $('#header').height() !== null ? $('#header').height() : 0;
        var _LoadingTop = _TabHeight > 61 ? (_TabHeight - 61) / 2 : 0,
            _LoadingLeft = _TabWidth > 215 ? (_TabWidth - 215) / 2 : 0;
        var _LoadingHtml =
            '<div id="loadingDiv" style="position:absolute;left:' + _offsetLeft + 'px;width:' + _TabWidth + 'px;height:' + _TabHeight + 'px;top:' + _offsetTop + 'px;' +
            'background:#fff;opacity:0.9;filter:alpha(opacity=90);z-index:10000;">' +
            '<div style="position: absolute; cursor1: wait; left: ' + _LoadingLeft + 'px; top:' + _LoadingTop + 'px; ' +
            'width: auto; height: 57px; line-height: 57px; padding-left: 50px; padding-right: 5px;' +
            ' background: #fff url(../css/images/loading.gif) no-repeat scroll 5px 10px;' +
            ' border: 2px solid #95B8E7;' +
            ' color: #696969; font-family:\'Microsoft YaHei\';">页面加载中，请等待...</div>' +
            '</div>';
        //呈现loading效果
        $('body').append(_LoadingHtml);
    },
    hoverAndSelect: function () {
        var $tableTR = $('.table');
        $tableTR
            .on('mouseenter', 'tr', function () {
                var index = $(this).index();
                $(this).closest('tbody').find('tr').each(function (i, item) {
                    if (i == index && !$(item).hasClass('select')) {
                        $(item).addClass('hover');
                    } else {
                        $(item).removeClass('hover');
                    }
                })
            })
            .on('mouseleave', 'tr', function () {
                $tableTR.find('.hover').removeClass('hover')
            });
    },
    typeByEnter: function (event, type, callback) {
        //通过回车输入
        var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;       //处理兼容性问题
        $("input[name='" + type + "']").bind('input propertychange', function () {
            // $("input[name='sure']").attr("value", $(this).val());
        });
        if (keyCode == "13") {
            callback();
        }
    },
    typeByClick: function (callback) {
        //通过点击输入
        // $("input[name='"+type+"']").bind('input propertychange', function () {
        //     // $("input[name='sure']").attr("value", $(this).val());
        // });
        callback();
    },
    smenu: function () {
        $("#smenu").click(function () {
            $(".m_smenu").toggle();
        });

        // $("#smenu").click(function () {
        //     $(this).focus();
        //     $(".m_smenu").removeClass("disappear");
        // });
        //
        // $("#user_text").blur(function () {
        //     $(".m_smenu").addClass("disappear");
        // });
    },
    judgeHeight: function () {
        util.beforeWebOnLoad();
        $("iframe").load(function () {
            // $('#loadingDiv').remove();
            var vHeight = $('.navigation').height() - $('#tabs>ul').outerHeight() - 17;
            $(this).height(vHeight < 300 ? 300 : vHeight);
            //$(this)[0].contentWindow.document.getElementById('iframe_doc_nav').style.height = vHeight - 2 + "px";
            //$(this)[0].contentWindow.document.getElementById('iframe_main').style.height = vHeight - 2 + "px";
            //console.log($('.navigation').height(),$('#tabs>ul').outerHeight(),$(this).height());
        });
    },
    jsonSort: function (array, field, reverse) {
        /**
         * @description    根据某个字段实现对json数组的排序
         * @param array 要排序的json数组对象
         * @param field 排序字段（此参数必须为字符串）
         * @param reverse 是否倒序（默认为false）
         * @returns {array} 返回排序后的json数组
         */
        //数组长度小于2 或 没有指定排序字段 或 不是json格式数据
        if (array.length < 2 || !field || typeof array[0] !== "object") return array;
        //数字类型排序
        if (typeof array[0][field] === "number") {
            array.sort(function (x, y) {
                return x[field] - y[field]
            });
        }
        //字符串类型排序
        if (typeof array[0][field] === "string") {
            array.sort(function (x, y) {
                return x[field].localeCompare(y[field])
            });
        }
        //倒序
        if (reverse) {
            array.reverse();
        }
        return array;
    },
    SetWinHeight: function (obj) {
        var win = obj;
        if (document.getElementById) {
            if (win && !window.opera) {
                // debugger
                if (win.contentDocument && win.contentDocument.body.offsetHeight) {
                    win.height = win.contentDocument.body.offsetHeight;
                } else if (win.Document && win.Document.body.scrollHeight) {
                    win.height = win.Document.body.scrollHeight;
                }
            }
        }
    },
    clearSelection: function () {
        if (document.selection && document.selection.empty) {
            document.selection.empty();
        } else if (window.getSelection) {
            var sel = window.getSelection();
            sel.removeAllRanges();
        }
    },
    converDate: function (date) {
        if (date == null) {
            return ' '
        } else {
            //json的时间格式进行拼接
            var year = new Date(date).getFullYear();
            var month = "0" + (new Date(date).getMonth() + 1);//为什么js获取日期要加1？
            var day = "0" + new Date(date).getDate();
            var hour = new Date(date).getHours();
            var minutes = new Date(date).getMinutes();
            var sec = new Date(date).getSeconds();
            return year + "-" + month.substring(month.length - 2) + "-" + day.substring(day.length - 2) + " " + hour + ":" + minutes + ":" + sec;
        }
    },
    convertDateToJsonDate: function (strDate) {
        //时间格式转换为Json的时间格式
        var t = new Date(strDate);
        return t.getTime();
    },
    formatDateTime: function (inputTime) {
        //时间戳转换*
        var date = new Date(inputTime);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        var m_second = date.getMilliseconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d;
    },
    formatDate: function (v, format) {
        /**
         格式化时间显示方式、用法:format="yyyy-MM-dd hh:mm:ss";
         */
        if (!v) return "";
        var d = new Date(v);
        if (typeof v === 'string') {
            if (v.indexOf("/Date(") > -1)
                d = new Date(parseInt(v.replace("/Date(", "").replace(")/", ""), 10));
            else
                d = new Date(Date.parse(v.replace(/-/g, "/").replace("T", " ").split(".")[0]));//.split(".")[0] 用来处理出现毫秒的情况，截取掉.xxx，否则会出错
        }
        var o = {
            "M+": d.getMonth() + 1,  //month
            "d+": d.getDate(),       //day
            "h+": d.getHours(),      //hour
            "m+": d.getMinutes(),    //minute
            "s+": d.getSeconds(),    //second
            "q+": Math.floor((d.getMonth() + 3) / 3),  //quarter
            "S": d.getMilliseconds() //millisecond
        };
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    },
    voidFunction: function () {

    },
    writeCookie: function (name, value, days) {
        //向cookie写入数据
        // 定义有效日期（cookie的有效时间）
        var expires = "";
        // 为有效日期赋值
        if (days) {
            var date = new Date();
            //设置有效期（当前时间+时间段）
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));//时间段为毫秒数
            expires = "; expires=" + date.toGMTString();
        }
        // 给cookie赋值 name, value和expiration date（有效期）
        document.cookie = name + "=" + value + expires + "; path=/";
    },
    readCookie: function (name) {
        //读取cookie数据
        var searchName = name + "=";
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var c = cookies[i];
            while (c.charAt(0) == ' ')
                c = c.substring(1, c.length);
            if (c.indexOf(searchName) == 0)
                return c.substring(searchName.length, c.length);
        }
        return null;
    },
    eraseCookie: function (name) {
        this.writeCookie(name, "", -1);
    },
    isChecked: function (id) {
        var isOK = true;
        if (id == undefined || id == "" || id == 'null' || id == 'undefined') {
            isOK = false;
            Dialog.alert('您没有选中任何项,请您选中后再操作。');
        }
        return isOK;
    },
    writeCache: function (key, value) {
        //存储，IE6~7 cookie 其他浏览器HTML5本地存储
        if (window.sessionStorage) {
            sessionStorage.setItem(key, value);
            // util.writeCookie("WorkerData", JSON.stringify(self.WorkerData));
        } else {
            util.writeCookie(key, value);
        }
    },
    numInArray: function (num, arr) {
        for (var j = 0; j < arr.length - 1; j++) {
            if (arr[j] == num) {
                return true;
            }
        }
        return false;
    },
    removeChinese: function (strValue) {
        //去掉汉字
        if (strValue != null && strValue != "") {
            var reg = /[\u4e00-\u9fa5]/g;
            return strValue.replace(reg, "");
        }
        else
            return "";
    },
    getChinese: function (strValue) {
        //只提取汉字
        if (strValue != null && strValue != "") {
            var reg = /[\u4e00-\u9fa5]/g;
            return strValue.match(reg).join("");
        }
        else
            return "";
    },
    tabChange: function (id) {
        $('.ScrollBar').find('.tabPanel').hide();
        $('.ScrollBar').find("#" + id).show();
        $(".tab_list_top div").removeClass("actived");
        $(".tab_list_top").find("#Tab" + id).addClass("actived"); //添加选中样式
    },
    removeLoading:function () {
        $(document).ajaxStop(function(){
            var load = window.top.document.getElementById('loadingDiv');
            if (load) {
                load.remove(0);
            }
        });
    },
};

util.init();