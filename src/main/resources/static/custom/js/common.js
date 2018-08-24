/**
 * Created by ricardo on 2018/8/10.
 * 通用js文件，全局
 */
//设置cookie
function setCookie(name, value, day) {
    var date = new Date();
    date.setDate(date.getDate() + day);
    document.cookie = name + '=' + value + ';expires=' + date;
}

//获取cookie
function getCookie(name) {
    var reg = RegExp(name + '=([^;]+)');
    var arr = document.cookie.match(reg);
    if (arr) {
        return arr[1];
    } else {
        return '';
    }
}

//删除cookie
function delCookie(name) {
    setCookie(name, null, -1);
}

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