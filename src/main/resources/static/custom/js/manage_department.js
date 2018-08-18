/*
* 作者：ricardo
* 描述：部门管理页面js
* 编写结构说明：
*     接口URL-->
*     页面加载-->
*     事件监听-->
*
**/
var urlGetDepartmentInfoById = "/department/getDepartmentInfoById";

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
$144(document).ready(function () {
    //alert("treeList="+treeList);
    $144.fn.zTree.init($144("#treeDemo"), setting, treeList);
    var $jq = jQuery.noConflict(true);

    //侧边栏 //侧边栏用的是jq1.11 和ztree的1.44不兼容
    $110(".dropdown-button").dropdown();
    $110("#sideNav").click(function () {
        if ($110(this).hasClass('closed')) {
            $110('.navbar-side').animate({left: '0px'});
            $110(this).removeClass('closed');
            $110('#page-wrapper').animate({'margin-left': '260px'});

        }
        else {
            $110(this).addClass('closed');
            $110('.navbar-side').animate({left: '-260px'});
            $110('#page-wrapper').animate({'margin-left': '0px'});
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

    $("#btn-add").click(function () {
        addPanel();
    });
    $("#btn-del").click(function () {
        var department = getCookie("department");
        deleteDepartment(department);
    });
    $("#btn-edit").click(function () {
        var department = getCookie("department");
        editPanel(department);
    });

    $("#btn-add-save").click(function () {

    });
    $("#btn-edit-save").click(function () {

    });
    $("#btn-add-cancel").click(function () {
        $("#view-panel").show().siblings(".panel").hide();
    });
    $("#btn-edit-cancel").click(function () {
        $("#view-panel").show().siblings(".panel").hide();
    });
});

//这里要记录当前选择的工位
function onClick(event, treeId, treeNode, clickFlag) {
//        var log = "[ onClick ]&nbsp;&nbsp;clickFlag = "
//            + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")";
    //保存当前选择的节点
    setCookie("treeNode",treeNode);
    var department = getDepartmentInfo(treeNode.id);
    //保存当前选择的部门信息
    setCookie("department",department);

    showPanel(department);
}

//根据idd获取departmentInfo信息
function getDepartmentInfo(id) {
    var department =null;
    //设置同步
    $.ajax({
        type : "get",
        url : urlGetDepartmentInfoById,
        data :"id=" + id,
        async : false,
        success : function(data){
            department = data.data;
        }
    });
    return department;
}

//渲染部门信息的面板
function showPanel(department) {
    $("#view-panel").show().siblings(".panel").hide();
    $("#view-title").text(department.name);
    $("#view-name").text(department.name);
    $("#view-level").text(department.level);
    $("#view-upDepartment").text(department.upDepartment);
    $("#view-description").text(department.description);
    $("#view-updateTime").text(department.updateTime);
    $("#view-worker").text(department.bangdingWorker);
    $("#view-scanner").text(department.scanner);
    $("#view-isLock").text(department.isLock?"是":"否");
}

//渲染添加信息的面板
function addPanel() {
    $("#add-panel").show().siblings(".panel").hide();
    $("#")
}

//渲染修改信息的面板
function editPanel(department) {

    $("#edit-panel").show().siblings(".panel").hide();

    // $("#view-title").text(department.name);
    // $("#view-name").text(department.name);
    // $("#view-level").text(department.level);
    // $("#view-upDepartment").text(department.upDepartment);
    // $("#view-description").text(department.description);
    // $("#view-updateTime").text(department.updateTime);
    // $("#view-worker").text(department.bangdingWorker);
    // $("#view-scanner").text(department.scanner);
    // $("#view-isLock").text(department.isLock?"是":"否");
}
function deleteDepartment(department) {

}