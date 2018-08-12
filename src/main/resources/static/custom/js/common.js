/**
 * Created by ricardo on 2018/8/10.
 * 通用js文件，全局
 */
//设置cookie
function setCookie(name, value, day) {
    var date = new Date();
    date.setDate(date.getDate() + day);
    document.cookie = name + '=' + value + ';expires=' + date;
};

//获取cookie
function getCookie(name) {
    var reg = RegExp(name + '=([^;]+)');
    var arr = document.cookie.match(reg);
    if (arr) {
        return arr[1];
    } else {
        return '';
    }
};

//删除cookie
function delCookie(name) {
    setCookie(name, null, -1);
};

$(document).ready(function(){

    //根据权限和model设置页面显示
    // var role = getCookie("role");
    // var model = getCookie("model");
    // //不是管理员，则隐藏count页面
    // if(role != 0){
    //     var count=document.getElementById("count");
    //     count.style.display='none';
    //     var total_final=document.getElementById("total_final");
    //     total_final.style.display='none';
    //     var system_manage=document.getElementById("system-manage");
    //     system_manage.style.display='none';
    // }
    //
    // $("#logout").click(function (e) {
    //     delCookie("user");
    //     delCookie("pswd");
    //     delCookie("model");
    //     delCookie("role");
    //     window.location.href ="/logout";
    // });
    //
    // var userName = getCookie("user");
    // $("#record1").click(function (e) {
    //     var url = "/record1?model="+model;
    //     window.location.href = url;
    // });
    //
    // $("#record3").click(function (e) {
    //     var url = "/record3?model="+model;
    //     window.location.href = url;
    // });
    // $("#count").click(function (e) {
    //     var url = "/count?model="+model;
    //     window.location.href = url;
    // });
    // $("#total_final").click(function (e) {
    //     var url = "/total_final";
    //     window.location.href = url;
    // });
});


// panel collapsible
$('.panel .tools .fa').click(function () {
    var el = $(this).parents(".panel").children(".panel-body");
    if ($(this).hasClass("fa-chevron-down")) {
        $(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
        el.slideUp(200);
    } else {
        $(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
        el.slideDown(200); }
});