package llcweb.service;


import llcweb.domain.models.PlanTable;

/**
 *@Author: Ricardo
 *@Description: 多线程任务执行类
 *@Date: 21:20 2018/8/28
 *@param:
 **/
public interface AsyncTaskService {

    //批次派工时生成套料管材表记录
    void generateTaoliaoRecord(PlanTable planTable);
}
