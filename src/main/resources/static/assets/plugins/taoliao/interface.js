/**
 * Created by BlueFisher on 2017/7/23 0023.
 */

/*父接口*/
var Interface = {
    init: function () {
        this.render();
    },
    render: function () {
        this.bind();
    },
    bind: function () {

    },
    /**
     *
     * @param url
     * @param callback
     */
    gainJSON: function (url, callback) {
        $.getJSON(url, function (obj) {
            callback(obj);
        });
    },
    /**
     *
     * @param url
     * @param jsonData
     * @param b_callback
     * @param s_callback
     * @param c_callback
     */
    sentJSON: function (url, jsonData, b_callback, s_callback, c_callback) {
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: url,
            data: jsonData,
            beforeSend: b_callback,
            success: s_callback,
            complete: c_callback
        });
    }
};

/*套料模块*/
var Jacking = {
    init: function () {
        this.render();
    },
    render: function () {
        this.bind();
    },
    bind: function () {

    },
    selectAllUncuttedBatch: function (callback) {
        //查询所有未下料批次
        var batch_url = '/selectAllUncuttedBatch';
        Interface.gainJSON(batch_url, callback);
    },
    selectAllCuttedBatch: function (callback) {
        //查询所有已下料批次
        var batch_url = '/selectAllCuttedBatch';
        Interface.gainJSON(batch_url, callback);
    },
    selectMachiningListByUnitName: function (batchName, callback) {
        //根据批次查询下料表
        var batch_url = '/selectMachiningListByUnitName?batchName=' + batchName;
        Interface.gainJSON(batch_url, callback);
    },
    returnJackingResult: function (data) {
        Interface.sentJSON('/returnJackingResult?batchName=LO-23', data, JackingApp.beforeSenJackingResult, JackingApp.jackingResult, util.voidFunction);
    },
    getKeyByBatchName: function (batchName, callback) {
        //根据批次返回套料的Key
        var batch_url = '/getKeyByBatchName?batchName=' + batchName;
        Interface.gainJSON(batch_url, callback);
    },
    getValueByBatchName: function (batchName, callback) {
        var batcj_url = '/getValueByBatchName?batchName=' + batchName;
        Interface.gainJSON(batcj_url, callback);
    },
    updateAllByPipeId: function (data) {
        Interface.sentJSON('/updateAllByPipeId?isCutted=true', data, util.voidFunction, JackingApp.updateAllPipeId, util.voidFunction);
    },
    selectPipeByPipeId: function (pipeId, callback) {
        //根据管号查下料表的管件
        var batch_url = '/selectPipeByPipeId?pipeId=' + pipeId;
        Interface.gainJSON(batch_url, callback);
    },
};

/*工人模块*/
var User = {
    init: function () {
        this.render();
    },
    render: function () {
        this.bind();
    },
    bind: function () {

    },
    selectWorkersByPrimaryKey: function (id, callback) {
        //根据工人id查工人信息
        var work_url = '/selectWorkersByPrimaryKey?id=' + id;
        Interface.gainJSON(work_url, callback);
    },
    insertWorkers: function (jsonData) {
        //新增工人信息
        Interface.sentJSON('/insertWorkers', jsonData, util.voidFunction, WorkerApp.addWorker, util.voidFunction);
    },
    updateWorkers: function (jsonData) {
        //修改工人信息
        Interface.sentJSON('/updateWorkers', jsonData, util.voidFunction, WorkerApp.editWorker, util.voidFunction);
    },
    deleteWorkersById: function (id, jsonData) {
        Interface.sentJSON('deleteWorkersById?id=' + id, jsonData, util.voidFunction, WorkerApp.deleteWorker, util.voidFunction);
    },
    selectAllWorkers: function (callback) {
        //查询所有工人信息
        var work_url = '/selectAllWorkers';
        Interface.gainJSON(work_url, callback);
    }
};

/*缺件模块*/
var Attachment = {
    init: function () {
        this.render();
    },
    render: function () {
        this.bind();
    },
    bind: function () {

    },
    selectAllAttachmentHubBody: function (callback) {
        //查询所有的级配表表身
        var attachment_url = '/selectAllAttachmentHubBody';
        Interface.gainJSON(attachment_url, callback);
    },
    selectAllAttachmentHubHeader: function (callback) {
        //查询所有的级配表表头
        var attachment_url = '/selectAllAttachmentHubHeader';
        Interface.gainJSON(attachment_url, callback);
    },
    selectAttachmentHubBodyByHubNum: function (hubNum, callback) {
        //根据集配号查附件集配表表身
        var attachment_url = '/selectAttachmentHubBodyByHubNum?hubNum=' + hubNum;
        Interface.gainJSON(attachment_url, callback);
    },
    updateAttachmentHubHeader: function (jsonData) {
        //新增工人信息
        Interface.sentJSON('/updateAttachmentHubHeader', jsonData);
    },
    selectAttachmentHubHeaderById: function (id, callback) {
        //根据id查附件集配表表头
        var attachment_url = '/selectAttachmentHubHeaderById?id=' + id;
        Interface.gainJSON(attachment_url, callback);
    },
    updateAttachmentHubBody: function (jsonData) {
        //修改附件集配表表身
        Interface.sentJSON('/updateAttachmentHubBody', jsonData, util.voidFunction, util.voidFunction, util.voidFunction);
    },
    deleteAttachmentHubBody: function (hubNum, callback) {
        //根据图号删除附件集配表表身
        var attachment_url = '/deleteAttachmentHubBody?hubNum=' + hubNum;
        Interface.gainJSON(attachment_url, callback);
    },
    insertAttachmentHubBody: function (jsonData) {
        //新增附件集配表表身
        Interface.sentJSON('/insertAttachmentHubBody', jsonData);
    },
    insertAttachmentHubHeader: function (jsonData) {
        //新增一条附件集配表表头
        Interface.sentJSON('/insertAttachmentHubHeader', jsonData);
    },
    deleteAttachmentHubHeaderById: function (id, callback) {
        //根据id删除附件集配表表头
        var attachment_url = '/deleteAttachmentHubHeader?id=' + id;
        Interface.gainJSON(attachment_url, callback);
    },
    selectAllFactoryDistribution: function (callback) {
        //查询所有的厂家配送缺件台账
        var attachment_url = '/selectAllFactoryDistribution';
        Interface.gainJSON(attachment_url, callback);
    },
    selectFactoryDistributionByComponentId: function (componentId, callback) {
        //根据部件id查找厂家配送缺件台账
        var attachment_url = '/selectFactoryDistributionByComponentId?componentId=' + componentId;
        Interface.gainJSON(attachment_url, callback);
    },
    selectFactoryDistributionById: function (id, callback) {
        //根据id查找厂家配送缺件台账
        var attachment_url = '/selectFactoryDistributionById?id=' + id;
        Interface.gainJSON(attachment_url, callback);
    },
    updateFactoryDistribution: function (jsonData, callback) {
        //修改一条厂家配送缺件台账
        Interface.sentJSON('/updateFactoryDistribution', jsonData, util.voidFunction, callback, util.voidFunction);
    },
    deleteFactoryDistributionById: function (id, callback) {
        //根据id删除一条厂家配送缺件台账
        var attachment_url = '/deleteFactoryDistributionById?id=' + id;
        Interface.gainJSON(attachment_url, callback);
    },
    insertFactoryDistribution: function (jsonData) {
        //新增一条厂家配送缺件台账
        Interface.sentJSON('/insertFactoryDistribution', jsonData, util.voidFunction, util.voidFunction, util.voidFunction);
    },
    getFactoryDistributionByShipBatchSession:function (jsonData, callback) {
        //根据特征筛选厂家配送台账
        Interface.sentJSON('/getFactoryDistributionByShipBatchSession', jsonData, util.voidFunction, callback, util.voidFunction);
    },
    updateFactoryDistributionByList: function (jsonData, callback) {
        //批量更新厂家配送台账
        Interface.sentJSON('/updateFactoryDistributionByList', jsonData, util.voidFunction, callback, util.voidFunction);
    }

};