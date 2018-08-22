
//根据planId获取该plan包含的所有单元对象
var urlGetUnits = "/unit/getUnitsByPlanId";
//根据部门工序获取工序下所有工位信息
var urlGetWorkplaceInfoByStageId="/department/getWorkplaceInfoByStageId";

var urlGetDepartmentBySectionAndStage ="/department/getDepartmentBySectionAndStage";
//获取当前用户管理的工段列表
var urlGetUserManageSections = "/users/getUserManageSections";
////获取当前未完工的船名和shipCode
var urlGetAllUnfinishedShip = "/ship/getAllUnfinishedShip";

/*常量*/
Date.prototype.toLocaleString = function() {
    return this.getFullYear() + "/" + (this.getMonth() + 1) + "/" + this.getDate();
};
//后台传来的日期数据，转化为2012/08/02 的格式
function dateToString(date) {
    if(date===null)return "未知";
    else return new Date(date).toLocaleString();
}
var CONSTANT = {
		DATA_TABLES : {
			DEFAULT_OPTION : { //DataTables初始化选项
				language: {
					"sProcessing":   "处理中...",
					"sLengthMenu":   "每页 _MENU_ 项",
					"sZeroRecords":  "没有匹配结果",
					"sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
					"sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
					"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
					"sInfoPostFix":  "",
					"sSearch":       "搜索:",
					"sUrl":          "",
					"sEmptyTable":     "表中数据为空",
					"sLoadingRecords": "载入中...",
					"sInfoThousands":  ",",
					"oPaginate": {
						"sFirst":    "首页",
						"sPrevious": "上页",
						"sNext":     "下页",
						"sLast":     "末页",
						"sJump":     "跳转"
					},
					"oAria": {
						"sSortAscending":  ": 以升序排列此列",
						"sSortDescending": ": 以降序排列此列"
					}
				},
                autoWidth: false,	//禁用自动调整列宽
                stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
                order: [],			//取消默认排序查询,否则复选框一列会出现小箭头
                processing: false,	//隐藏加载提示,自行处理
                serverSide: true,	//启用服务器端分页
                searching: false	//禁用原生搜索
			},
			COLUMN: {
                CHECKBOX: {	//复选框单元格
                    className: "td-checkbox",
                    orderable: false,
                    width: "30px",
                    data: null,
                    render: function (data, type, row, meta) {
                        return '<input type="checkbox" class="iCheck">';
                    }
                }
            },
            RENDER: {	//常用render可以抽取出来，如日期时间、头像等
                ELLIPSIS: function (data, type, row, meta) {
                	data = data||"";
                	return '<span title="' + data + '">' + data + '</span>';
                }
            }
		}
};



//判断下料状态
function stringIsCutted(isCutted) {
    if (isCutted === 1) return "下料完成";
    else if (isCutted === 0) return "已派工";
    else if (isCutted === -1) return "未开始";
    else return "未知";
}

//根据下料状态返回渲染的css class
function classIsCutted(isCutted) {
    if (isCutted === 1) return "text-success";
    else if (isCutted === 0) return "text-info";
    else if (isCutted === -1) return "text-warning";
    else return "text-error";
}

//根据planId获取该plan包含的所有单元对象
function getUnits(planId) {
    var units =null;
    //设置同步
    $.ajax({
        type : "get",
        url : urlGetUnits,
        data :"planId=" + planId,
        async : false,
        success : function(data){
            units = data.data;
        }
    });
    return units;
}
//根据planId获取该plan包含的所有单元名称
function getUnitsName(planId) {
    var units = getUnits(planId);
    var unitNames="";
    for(var i = 0;i<units.length;i++){
        unitNames+=units[i].unitName+",";
    }
    return unitNames;
}


//根据部门工序获取工序下所有工位信息
function getWorkplaceInfoByStageId(stageId) {
    var departmentList =null;
    //设置同步
    $.ajax({
        type : "get",
        url : urlGetWorkplaceInfoByStageId,
        data :"stageId=" + stageId,
        async : false,
        success : function(data){
            departmentList = data.data;
        }
    });
    return departmentList;
}
//根据工段和工段下的部门工序，获取其department信息
function getDepartmentBySectionAndStage(section,stage) {
    var department =null;
    //设置同步
    $.ajax({
        type : "get",
        url : urlGetDepartmentBySectionAndStage,
        data :"section=" + section+"&stage="+stage,
        async : false,
        success : function(data){
            department = data.data;
        }
    });
    return department;
}

//获取当前用户管理的工段列表
function getUserManageSections() {
    var sections =null;
    //设置同步
    $.ajax({
        type : "get",
        url : urlGetUserManageSections,
        data :"",
        async : false,
        success : function(data){
            sections = data.data;
        }
    });
    return sections;
}
////获取当前未完工的船名和shipCode
function getAllUnfinishedShip() {
    var ships =null;
    //设置同步
    $.ajax({
        type : "get",
        url : urlGetAllUnfinishedShip,
        data :"",
        async : false,
        success : function(data){
            ships = data.data;
        }
    });
    return ships;
}