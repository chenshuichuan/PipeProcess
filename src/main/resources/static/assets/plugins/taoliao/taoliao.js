/**
 * Created by BlueFisher on 2017/7/31 0031.
 */
var JackingApp = {
    MachiningList: [],                  //存储下料表
    ResultArr: [],                      //存储套料结果
    ResultArrIndex: 0,                  //存储套料结果序号
    PipeId: [],                         //返回打印结果的管号
    s_length: 0,                        //剩余长度
    count: 0,                           //
    pyl_length: 0,                      //管件长度
    trNum: 0,                           //待下料表中空白表格的数量
    treeSetting: {      //树设置
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: this.treeOnClick
        }
    },
    zNodes: [
        {"id": 1, "pId": 0, "name": "已完成批次", "open": true, "icon": "./zTreeStyle/img/diy/1_open.png"}
    ],                       //树结构数据
    init: function () {
        this.render();
    },            //初始化
    render: function () {
        var self = this;
        util.removeLoading();
        this.getData();
        this.bind();
        util.hoverAndSelect();
        this.autoHeight();
        var interval;
        //循环检查套料结果
        interval = setInterval(function () {
            if (self.ResultArr.length != 0) {
                self.fillData();
                clearInterval(interval);
            }
        }, 500);

        $.ajaxSettings.async = false;
        Jacking.selectMachiningListByUnitName('LO-23', function (obj) {
            if (obj.status) {
                JackingApp.MachiningList = obj.data;
            }
        });
        $.ajaxSettings.async = true;

    },          //渲染
    bind: function () {
        var self = this;
        $('#not-table')
        //展示子类
            .on('click', 'i.treeRoot', function () {
                var _this = $(this);
                var $tr = _this.parent().parent();
                var parentValue = $tr.attr('id');
                parentValue = parentValue.split('_')[1];
                $('.child_' + parentValue).toggle();
                $(this).toggleClass('in');
            })
            //选中表格中的管材
            .on('click', 'tr', function () {
                var _this = $(this);
                var className = _this.attr("class").split(' ')[0];
                if (className === "primary") {
                    var index = _this.attr("id").split('_')[1];
                    var $td = _this.children();
                    var _isCutted = $td[5].innerText;
                    var $item = $("#n-xialiao" + index);
                    var $bortherItem = _this.siblings().find('i.ops');
                    if ($item.hasClass('nomark') && _isCutted == '否') {
                        $item.removeClass('nomark').addClass('okmark');
                        _this.removeClass("hover").addClass("select");
                        if ($bortherItem.hasClass('okmark')) {
                            $bortherItem.removeClass('okmark').addClass('nomark');
                            _this.siblings().removeClass('select');
                        }
                        self.addList(_this);
                        // $('#sure').removeAttr('disabled');
                        // $('#taoliao').removeAttr('disabled');
                        // $('#tl-del').removeAttr('disabled');
                    } else {
                        $item.removeClass('okmark').addClass('nomark');
                        self.delList(_this);
                        // $('#sure').attr('disabled', 'disabled');
                        // $('#taoliao').attr('disabled', 'disabled');
                        // $('#tl-del').attr('disabled', 'disabled');
                    }
                } else {
                    var parentIndex = _this[0].cells[3].lastChild.id.split('_')[0];
                    var childIndex = _this[0].cells[3].lastChild.id.split('_')[1];
                    var $item = $("#" + parentIndex + "_" + childIndex);
                    var $bortherItem = _this.siblings().find('i.ops').not("i[id^='" + parentIndex + "']")
                }
                ;
                // $item.attr('checked', 'true');
            });
        $('#yuchuli')
        //选择输错的原材料长度
            .on('click', 'tr', function () {
                var _this = $(this);
                var index = _this.index();
                var $item = $("#yl" + (index + 1));
                var $bortherItem = _this.siblings().find('i.ops');
                // $item.attr('checked', 'true');
                if ($item.hasClass('nomark')) {
                    $item.removeClass('nomark').addClass('okmark');
                    _this.removeClass("hover").addClass("select");
                    if ($bortherItem.hasClass('okmark')) {
                        $bortherItem.removeClass('okmark').addClass('nomark');
                        _this.siblings().removeClass('select');
                    }
                } else {
                    $item.removeClass('okmark').addClass('nomark');
                    _this.removeClass('select');
                }
            })
            //删除输错的原材料长度
            .on('click', 'i.destroy', function () {
                var _this = $(this);
                var $tr = _this.parent().parent();
                var index = $tr.index();
                var $td = $tr.children();
                var num = $td[2].innerText;
                self.s_length = self.s_length + parseInt(num);
                $("#s-length").val(self.s_length);
                $('#yuchuli tbody tr').eq(index).remove();
                var $trNum = $("#yuchuli tbody").find('tr').length;
                if ($trNum < 20) {
                    $('#yuchuli tbody').append('<tr>' +
                        '<td width="35%"></td>' +
                        '<td width="20%"></td>' +
                        '<td width="30%"></td>' +
                        '</tr>');
                }
            });
        //点击生成套料方案
        $("#taoliao")
            .on('click', function () {
                var $tbody = $(".tl_choice table").find('tbody')[0];
                if ($tbody.innerText != '') {
                    var data = self.jsonData();
                    Jacking.returnJackingResult(data);
                }
            });
        //选择原材料管材
        $('.filetree')
            .on('click', 'li', function () {
                var _this = $(this);
                self.ResultArrIndex = _this.index();
                $('.result-table tbody').html('');
                for (var key in self.ResultArr[self.ResultArrIndex]) {
                    var objType = self.ResultArr[self.ResultArrIndex][key];      //获取值
                    if (key == pipeLength) {
                        JackingApp.pyl_length = objType;
                    }
                    $('#' + key).text(objType);
                }
                $.each(self.ResultArr[self.ResultArrIndex].pipeJackingList, function (i, item) {
                    if (!item.noInstalled) {
                        var notInstalled = "▲";
                    } else {
                        notInstalled = " ";
                    }
                    // $('.result-table tbody').append('<tr>');
                    $('.result-table tbody').append('<tr>' +
                        '<td class = "id">' + item.id + '</td><td class = "batchName">' + item.batchName + '</td>' +
                        '<td class = "pipeId">' + item.pipeId + '</td>' +
                        '<td class = "surfaceTraet">' + item.surfaceTraet + '</td><td class = "shape">' + item.shape + '</td>' +
                        '<td class = "pipeMaterial">' + notInstalled + '</td><td class = "cuttingLength">' + item.pipeMaterial + '</td>' +
                        '<td class = "cuttingLength">' + item.cuttingLength + '</td>');
                });
            });
        //修改打印内容
        $('#r1')
            .on('dblclick', 'td', function (event) {
                //td中已经有了input,则不需要响应点击事件
                var _this = $(this);
                var index = _this.parent().index();
                var $className = _this.attr('class');
                var data = JackingApp.ResultArr[JackingApp.ResultArrIndex].pipeJackingList;
                if ($(this).children("input").length > 0)
                    return false;
                var tdObj = $(this);
                var preText = tdObj.html();
                //得到当前文本内容
                var inputObj = $("<input type='text' />");
                //创建一个文本框元素
                tdObj.html(""); //清空td中的所有元素
                inputObj
                    .width(tdObj.width() + 20)
                    //设置文本框宽度与td相同
                    .height(tdObj.height())
                    .css({border: "0px", fontSize: "17px", font: "宋体"})
                    .val(preText)
                    .appendTo(tdObj)
                    //把创建的文本框插入到tdObj子节点的最后
                    .trigger("focus")
                    //用trigger方法触发事件
                    .trigger("select");
                inputObj.keyup(function (event) {
                    if (13 == event.which)
                    //用户按下回车
                    {
                        var text = $(this).val();
                        tdObj.html(text);
                        data[index][$className] = text;
                    }
                    else if (27 == event.which)
                    //ESC键
                    {
                        tdObj.html(preText);
                    }
                });
                inputObj.blur(function () {
                    var text = $(this).val();
                    tdObj.html(text);
                    data[index][$className] = text;
                });
                //已进入编辑状态后，不再处理click事件
                inputObj.click(function () {
                    return false;
                });
            });
        //打印成功后
        $('#print_success')
            .on('click', function () {
                var flag = 0;
                var childResultArr = self.ResultArr[self.ResultArrIndex].pipeJackingList;
                $('#barCode').addClass('disappear');
                $('#barCodeConfirm').addClass('disappear');
                $('.filetree li').eq(self.ResultArrIndex).addClass('select');
                $('.filetree li').each(function (i, item) {
                    if ($('.filetree li').eq(i).hasClass('select')) {
                        flag++;
                    }
                });
                $.each(childResultArr, function (i, item) {
                    var obj = {};
                    obj.id = item.id;
                    obj.pipeId = item.pipeId;
                    self.PipeId.push(obj);
                });
                var ResultArrLength = self.ResultArr.length;
                if (flag == ResultArrLength) {
                    var postdata = JSON.stringify(self.PipeId);
                    console.log("postdata:", postdata);
                    Jacking.updateAllByPipeId(postdata);

                }
            });
        //打印失败后
        $('#print_failure')
            .on('click', function () {
                $("#barCodeDiv").jqprint({
                    debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
                    importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
                    printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
                    operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
                });
            });
    },            //绑定事件
    getData: function () {
        var self = this;
        Jacking.selectAllCuttedBatch(self.getAllCuttedBatch);
    },         //获取数据
    addList: function (obj) {
        var $tr = obj;
        var $td = $tr.children();
        var name = $td[1].innerText;
        var g_length = $td[2].innerHTML;
        $("#name").val(name);
        $("#g-length").val(g_length);
        $("#s-length").val(g_length);
        $("#y-length").attr("autofocus");
        $(".tl_choice table tbody").html('');
        for (var i = 0; i <= JackingApp.trNum; i++) {
            $('#yuchuli tbody').append('<tr>' +
                '<td width="35%"></td>' +
                '<td width="20%"></td>' +
                '<td width="30%"></td>' +
                '</tr>');
        }
    },      //选中管材并输入到待计算列表
    autoHeight: function () {
        var divkuangH = window.outerHeight;
        divkuangH = divkuangH - 68 - $('#tabs>ul').height() - 26;
        vHright = divkuangH - $('.tools_bar').height() - $('.search_bar').height() - 100;
        $('.xl_table').height(vHright);
        $('.tl_choice').height(vHright);
        $('.ui-jqgrid-bdiv').height(vHright - $('.ui-state-default').height());
        JackingApp.trNum = (vHright - $('.ui-state-default').height()) / 23;
        for (var i = 0; i <= JackingApp.trNum; i++) {
            $('.tl_choice .ui-jqgrid-bdiv').find('tbody').append('<tr><td width="35%"></td><td width="20%"></td><td width="30%"></td></tr>');
        }

    },     //自动调节页面的大小
    beforeSenJackingResult: function () {
        var _TabHeight = $('.content').outerHeight(),
            _TabWidth = $('.content').outerWidth();
        var _LoadingTop = _TabHeight > 61 ? (_TabHeight - 61) / 2 : 0,
            _LoadingLeft = _TabWidth > 215 ? (_TabWidth - 215) / 2 : 0;
        var _LoadingHtml =
            '<div id="loadingDiv" style="position:absolute;left:0;width:100%;height:' + _TabHeight + 'px;top:0;' +
            'background:#fff;opacity:1;filter:alpha(opacity=80);z-index:10000;">' +
            '<div style="position: absolute; cursor1: wait; left: ' + _LoadingLeft + 'px; top:' + _LoadingTop + 'px; ' +
            'width: auto; height: 57px; line-height: 57px; padding-left: 50px; padding-right: 5px;' +
            ' background: #fff url(../css/images/loading.gif) no-repeat scroll 5px 10px;' +
            ' border: 2px solid #95B8E7;' +
            ' color: #696969; font-family:\'Microsoft YaHei\';">计算中，请等待...</div>' +
            '</div>';
        //呈现loading效果
        $('.content').append(_LoadingHtml);
    }, //数据发送前显示待加载中
    delList: function (obj) {
        var $tr = obj;
        var $td = $tr.children();
        var name = $td[1].innerHTML;
        $($tr).removeClass("select");
        // $("#sl option[value="+name+"]").remove();//变量要有+
        $("#name").val("");
        $("#g-length").val("");
        $("#y-length").val("");
        $("#s-length").val("");
        $(".tl_choice table tbody").html('');
        for (var i = 0; i <= JackingApp.trNum; i++) {
            $('#yuchuli tbody').append('<tr>' +
                '<td width="35%"></td>' +
                '<td width="20%"></td>' +
                '<td width="30%"></td>' +
                '</tr>');
        }
    },      //取消选中管材并从待计算列表删除
    fillData: function () {
        var _this = this;
        $.each(_this.ResultArr, function (i, item) {
            $('.filetree').append('<li><span class="folder" id="y' + (i + 1) + '">原材料' + (i + 1) + ':' + item.pipeLength + '</span></li>');
        });
        $('#pipeLength').text(_this.ResultArr[0].pipeLength);
        JackingApp.pyl_length = _this.ResultArr[0].pipeLength;
        $('#cloutLength').text(_this.ResultArr[0].cloutLength);

        $('.result-table tbody').html('');
        _this.ResultArrIndex = 0;
        $.each(_this.ResultArr[0].pipeJackingList, function (i, item) {
            if (!item.noInstalled) {
                var notInstalled = "▲";
            } else {
                notInstalled = " ";
            }
            // $('.result-table tbody').append('<tr>');
            $('.result-table tbody').append('<tr>' +
                '<td class = "id">' + item.id + '</td><td class = "batchName">' + item.batchName + '</td>' +
                '<td class = "pipeId">' + item.pipeId + '</td>' +
                '<td class = "surfaceTraet">' + item.surfaceTraet + '</td><td class = "shape">' + item.shape + '</td>' +
                '<td class = "pipeMaterial">' + notInstalled + '</td><td class = "cuttingLength">' + item.pipeMaterial + '</td>' +
                '<td class = "cuttingLength">' + item.cuttingLength + '</td>');
        });
        $('#loadingDiv').remove();
        $('.tl-result').removeClass('disappear');
        window.location.href = '#result';
    },        //把套料结果填充到结果列表中
    getAllCuttedBatch: function (obj) {
        var num = 10;
        var batchName = "";
        if (obj.status) {
            $.each(obj.data, function (i, item) {
                // $("#not").append("<li>" + item.batchName + "</li>");
                var nodeTemplete = {"id": "", "pId": 1, "name": "", "icon": "../../../css/zTreeStyle/img/diy/3.png"};
                nodeTemplete.name = item.batchName;
                num++;
                nodeTemplete.id = num;
                JackingApp.zNodes.push(nodeTemplete);
            });
            var uncuttedTitle = {
                "id": 2,
                "pId": 0,
                "name": "未完成批次",
                "open": true,
                "icon": "./zTreeStyle/img/diy/1_close.png"
            };
            JackingApp.zNodes.push(uncuttedTitle);
        }
        Jacking.selectAllUncuttedBatch(JackingApp.getAllUncuttedBatch);
    },      //查询所有已下料批次
    getAllUncuttedBatch: function (obj) {
        var num = 20;
        var batchName = "";
        if (obj.status) {
            $.each(obj.data, function (i, item) {
                // $("#not").append("<li>" + item.batchName + "</li>");
                var nodeTemplete = {"id": "", "pId": 2, "name": "", "icon": "./zTreeStyle/img/diy/3.png"};
                nodeTemplete.name = item.batchName;
                num++;
                nodeTemplete.id = num;
                JackingApp.zNodes.push(nodeTemplete);
            });
        }
        $.fn.zTree.init($("#treeDemo"), treeSetting, JackingApp.zNodes);
        console.log("zNodes:", JSON.stringify(JackingApp.zNodes));
        $.each(JackingApp.zNodes, function (i, item) {
            if (item.pId == 2) {
                batchName = JackingApp.zNodes[i].name;
                console.log("batchName:", batchName);
                return false;
            }
        });
        Jacking.getKeyByBatchName("LO-23", JackingApp.returnKeyByBatchName);
    },  //查询所有未下料批次
    getMachiningListByUnitName: function (obj) {
        var i = 0, maxI, TotalLength = 0,
            arr = [];
        if (obj.status) {
            /*对相同的pipeMaterial分类计算总长度*/
            obj.data.sort(function (x, y) {
                return x.pipeMaterial.localeCompare(y.pipeMaterial)
            });    //对json中的pipeMaterial字段进行排序
            for (i = 0; maxI = obj.data.length, i < maxI; i++) {
                var key = obj.data[i].pipeMaterial, item = {};
                if ((i + 1) < maxI && obj.data[i].pipeMaterial == obj.data[i + 1].pipeMaterial) {
                    TotalLength = TotalLength + obj.data[i].cuttingLength;
                } else {
                    TotalLength = TotalLength + obj.data[i].cuttingLength;
                    item.pipeMaterial = key;
                    item.cuttingLength = TotalLength;
                    item.iscutted = obj.data[i].iscutted;
                    arr.push(item);
                    item = {};
                    TotalLength = 0;
                }
            }
            JSON.stringify(arr);
            $('#not-table tbody').html('');
            $.each(arr, function (i, item) {
                var _isCutted = true;
                if (item.iscutted) {
                    _isCutted = '是';
                } else {
                    _isCutted = '否';
                }
                $('#not-table tbody').append('<tr class="primary" id="parent_' + (i + 1) + '">' +
                    '<td width="10%">' + (i + 1) + '</td><td width="40%"><i class="treeRoot"></i><span>' + item.pipeMaterial + '</span></td>' +
                    '<td width="40%">' + item.cuttingLength + '</td>' +
                    '<td role="gridcell" style="text-align:center;" title="ops" aria-describedby = "gridTable_enabled" width="10%">' +
                    '<i class="nomark ops" id="n-xialiao' + (i + 1) + '"></i>' +
                    '</td>' +
                    '<td width="30%">' + _isCutted + '</td>');
            });
        }
    },  //根据批次查询下料表
    jackingResult: function (obj) {
        var _this = this;
        if (obj.status) {
            var BatchResult = obj.data.cloutPipeList;
            JackingApp.ResultArr = BatchResult;
        } else {
            var info = obj.data;
            info = info.split(':');
            Dialog.alert(info[1] + '请重新选择下料原材料。', function () {
                $('#loadingDiv').remove();
            });
        }
    }, //获取套料结果
    jsonData: function () {
        var obj = new Object();
        var arr = new Array();
        var $tableData = $('#yuchuli tbody').find('tr');
        $.each($tableData, function (i, item) {
            if (item.cells[0].innerText != "") {
                obj = {};
                obj.id = i + 1;
                obj.material = item.cells[0].innerText;
                obj.length = item.cells[2].childNodes[0].innerText;
                arr.push(obj);
            }
        });
        var postdata = JSON.stringify(arr);
        console.log("postdata:", postdata);
        return postdata;
    },         //封装json数据
    printResult: function (data) {
        var reg = /[^\(\)]+(?=\))/g;   //匹配括号中的文字（不包括括号）
        var reg1 = /\(.*?\)/g;     //匹配括号中的文字（包括括号）
        $('#barCodeDiv').html('');
        $.each(data.pipeJackingList, function (i, item) {
            /*去除Ⅲ标记*/
            var spec = item.pipeMaterial;
            var mark = spec.match(reg);
            if (mark == 'Ⅲ') {
                mark = ' ';
            }
            spec = spec.replace(reg1, mark);
            /*加小三角*/
            if (!item.noInstalled) {
                var notInstalled = "▲";
            } else {
                notInstalled = " ";
            }
            /*RS加滑*/
            var pipeRS = item.pipeId;
            if (pipeRS.indexOf('RS') != -1) {
                var rs = "滑";
            } else {
                rs = " ";
            }

            var _CodeHtml = '<div style="overflow: hidden;width: 100%;margin-top: 20px;" class="barDiv">' +
                // <div class="yl_length">原料：' + JackingApp.pyl_length + '&nbsp;</div>
                '<div class="yl_length">L：' + item.cuttingLength + '&ensp;&ensp;&ensp;&ensp;' + rs + '</div>' +
                '<div id="bcTarget_' + (i + 1) + '" class="bcTarget"></div>' +
                '<div class="pipeDetail">' +
                // '<div class="l_detail">' +
                '<span class="STYLE444">(' + item.id + ')&ensp;</span><span class="STYLE444">' + item.batchName + '&ensp;</span><span class="STYLE444">' + util.removeChinese(spec) + '&ensp;</span>' +
                // '</div>' +    <span class="STYLE444">' + item.pipeId + '</span>
                // '<div class="r_detail">' +
                '<span class="STYLE444">' + item.surfaceTraet + '&ensp;</span><span class="STYLE444">' + item.shape + '&ensp;</span><span class="STYLE444">' + notInstalled + '</span>' +
                // '</div>' +
                '</div>' +
                '</div>';
            $('#barCodeDiv').append(_CodeHtml);
            $('#bcTarget_' + (i + 1)).barcode(item.pipeId, "code128", {barWidth: 1, barHeight: 40, fontSize: 13});
        });
        $('#barCode').removeClass('disappear');
        $("#barCodeDiv").jqprint({
            debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
            importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
            printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
            operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
        });
        $('#barCodeConfirm').removeClass('disappear');
        $('#barCodeConfirm')[0].style.height = $('.content').outerHeight() + 'px';
        $('#barCode')[0].style.height = $('.content').outerHeight() + 'px';
    },  //显示打印页面，生成条形码
    returnKeyByBatchName: function (obj) {
        if (obj.status) {
            $('#not-table tbody').html('');
            $.each(obj.data, function (i, item) {
                var _isCutted = true;
                if (item.isCutting) {
                    _isCutted = '是';
                } else {
                    _isCutted = '否';
                }
                $('#not-table tbody').append('<tr class="primary" id="parent_' + (i + 1) + '">' +
                    '<td width="10%">' + (i + 1) + '</td><td width="30%"><i class="treeRoot"></i><span>' + item.meterial + '</span></td>' +
                    '<td width="20%">' + item.totalLength + '</td>' +
                    '<td width="10%" id="num_' + (i + 1) + '" align="center"></td>' +
                    '<td role="gridcell" style="text-align:center;" title="ops" aria-describedby = "gridTable_enabled" width="10%">' +
                    '<i class="nomark ops" id="n-xialiao' + (i + 1) + '"></i>' +
                    '</td>' +
                    '<td width="20%" align="center">' + _isCutted + '</td>');
            });
            Jacking.getValueByBatchName("LO-23", JackingApp.returnValueByBatchName);
        }
    },  //返回下料父类
    returnValueByBatchName: function (obj) {
        if (obj.status) {
            var $parentNum = $('#not-table tbody').find('tr').length;
            $.each(obj.data, function (i, parentItem) {
                $.each(parentItem, function (j, childItem) {
                    var _isCutted = true;
                    if (childItem.iscutted) {
                        _isCutted = '是';
                    } else {
                        _isCutted = '否';
                    }
                    $('#not-table tbody').find('#parent_' + (i + 1)).after('<tr class="child child_' + (i + 1) + '">' +
                        '<td width="10%">' + childItem.id + '</td><td width="30%" class="first"><span>' + childItem.pipeMaterial + '</span></td>' +
                        '<td width="20%">' + childItem.cuttingLength + '</td>' +
                        '<td width="10%" align="center"></td>' +
                        '<td role="gridcell" style="text-align:center;" title="ops" aria-describedby = "gridTable_enabled" width="10%">' +
                        // '<i class="nomark ops" id="n-xialiao' + (i + 1) + '_' + (j + 1) + '"></i>' +
                        '</td>' +
                        '<td width="20%" align="center">' + _isCutted + '</td>' +
                        '</tr>');
                });
                var childNum = parentItem.length;
                // var pipeLength = $('#parent_' + (i + 1))[0].cells[2].innerText;
                $('#num_' + (i + 1)).text(childNum);
                $('.child_' + (i + 1)).toggle();
                $('#parent_' + (i + 1)).toggleClass('in');
            });
        }
    },//返回下料子类
    sureFill: function () {
        var $trNum = $("#yuchuli tbody").find('tr').length;
        var $voidTr = $("#yuchuli tbody").find('tr');
        if ($("#y-length").val() != '') {
            var gc = $("#name").val();
            var g_length = $("#g-length").val();
            var y_length = $("#y-length").val();
            JackingApp.s_length = $("#s-length").val();
            var num = $("#y-num").val();
            if (num == 0 || num == 1) {
                JackingApp.s_length = JackingApp.s_length - y_length * 1;
                $("#yuchuli tbody").prepend("<tr><td width='35%'>" + gc + "</td><td width='20%'>" + g_length + "</td><td  width='30%'><span>" + y_length + "</span><i class='destroy'></i></td></tr>");
            } else {
                JackingApp.s_length = JackingApp.s_length - y_length * num;
                for (var i = 1; i <= num; i++) {
                    $("#yuchuli tbody").prepend("<tr><td width='35%'>" + gc + "</td><td width='20%'>" + g_length + "</td><td width='30%'><span>" + y_length + "</span><i class='destroy'></i></td></tr>");
                }
            }
            // if ($trNum) {
            //     $voidTr.eq($trNum - 1).remove();
            //     $trNum--;
            // }
            $("#s-length").val(JackingApp.s_length);
            $('#y-length').val("");
            $('#y-num').val("1");
            // JackingApp.count++;
        }
    },         //选中管材并输入到待计算列表
    sureSearch: function () {
        var gh = $("#pipe_id").val();
        if (util.isChecked(gh)) {
            util.writeCache("PiepId", gh);
            DiagApp.open14();
        }
    },      //确定输入的管件ID
    treeOnClick: function (event, treeId, treeNode, clickFlag) {
        Jacking.getKeyByBatchName(treeNode.name, JackingApp.returnKeyByBatchName);
        Jacking.getValueByBatchName(treeNode.name, JackingApp.returnValueByBatchName);
    },   //选择未下料的批次
    updateAllPipeId: function (obj) {
        if (obj.status) {
            window.location.reload();           //成功后刷新页面
        }
    }       //更新整个下料表
};

var PrintApp = {
    PipeId: "",
    init: function () {
        this.render();
    },
    render: function () {
        this.PipeId = window.sessionStorage ? sessionStorage.getItem("PiepId") : util.readCookie("PiepId");
        Jacking.selectPipeByPipeId(this.PipeId, this.printBar);
        this.bind();
    },
    bind: function () {

    },
    printBar: function (obj) {
        if (obj.status) {
            var item = obj.data[0];
            var reg = /[^\(\)]+(?=\))/g;   //匹配括号中的文字（不包括括号）
            var reg1 = /\(.*?\)/g;     //匹配括号中的文字（包括括号）
            $('#barCodeDiv').html('');
            /*去除Ⅲ标记*/
            var spec = item.pipeMaterial;
            var mark = spec.match(reg);
            if (mark == 'Ⅲ') {
                mark = ' ';
            }
            spec = spec.replace(reg1, mark);
            /*加小三角*/
            if (!item.noInstalled) {
                var notInstalled = "▲";
            } else {
                notInstalled = " ";
            }
            /*RS加滑*/
            var pipeRS = item.pipeId;
            if (pipeRS.indexOf('RS') != -1) {
                var rs = "滑";
            } else {
                rs = " ";
            }
            var _CodeHtml = '<div style="overflow: hidden;width: 100%;margin-top: 20px;" class="barDiv">' +
                // <div class="yl_length">原料：' + JackingApp.pyl_length + '&nbsp;</div>
                '<div class="yl_length">L：' + item.cuttingLength + '&ensp;&ensp;&ensp;&ensp;' + rs + '</div>' +
                '<div id="bcTarget_1" class="bcTarget"></div>' +
                '<div class="pipeDetail">' +
                // '<div class="l_detail">' +
                '<span class="STYLE444">(' + item.id + ')&ensp;</span><span class="STYLE444">' + item.batchName + '&ensp;</span><span class="STYLE444">' + util.removeChinese(spec) + '&ensp;</span>' +
                // '</div>' +    <span class="STYLE444">' + item.pipeId + '</span>
                // '<div class="r_detail">' +
                '<span class="STYLE444">' + item.surfaceTreat + '&ensp;</span><span class="STYLE444">' + item.shape + '&ensp;</span><span class="STYLE444">' + notInstalled + '</span>' +
                // '</div>' +
                '</div>' +
                '</div>';
            $('#barCodeDiv').append(_CodeHtml);
            $('#bcTarget_1').barcode(item.pipeId, "code128", {barWidth: 1, barHeight: 40, fontSize: 13});
            $('#barCode').removeClass('disappear');
            $("#barCodeDiv").jqprint({
                debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
                importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
                printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
                operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
            });
            $('#barCodeConfirm').removeClass('disappear');
            $('#barCodeConfirm')[0].style.height = $('.content').outerHeight() + 'px';
            $('#barCode')[0].style.height = $('.content').outerHeight() + 'px';
        }
    }
};