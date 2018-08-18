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
var urlGetUnitsByStage = "/department/getDepartmentInfoById";

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
$144(document).ready(function(){
    $144.fn.zTree.init($144("#treeDemo"), setting, treeList);
    var $jq =jQuery.noConflict(true);
    var $table = $('#table-plan');$110('#dataTables-example').dataTable(
        {
            "scrollY": 500,
            "scrollX": true
        }
    );

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

    //mainApp.initFunction();
});
//这里要记录当前选择的工位，及其父部门(也就是工序)的id,如若选择的工序与当前cookie保存的不一致，则重新加载表格数据
function onClick(event, treeId, treeNode, clickFlag) {
//        var log = "[ onClick ]&nbsp;&nbsp;clickFlag = "
//            + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")";

    $("#selected-view").text(treeNode.name);
}
