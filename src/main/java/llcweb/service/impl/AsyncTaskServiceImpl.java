package llcweb.service.impl;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/21
 * Time: 22:08
 */

import llcweb.domain.models.PlanTable;
import llcweb.service.AsyncTaskService;
import llcweb.service.TaoliaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

/**
 *@Author: Ricardo
 *@Description: 多线程任务执行类
 *@Date: 21:20 2018/8/28
 *@param:
 **/
@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {


    private final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private TaoliaoService taoliaoService;

    /**
     * 异常调用返回Future
     *
     * @param i
     * @return
     * @throws InterruptedException
     */
    @Async
    public Future<String> asyncInvokeReturnFuture(int i) throws InterruptedException {
        System.out.println("input is " + i);
        Thread.sleep(1000 * 5);
        Future<String> future = new AsyncResult<String>("success:" + i);
        // Future接收返回值，这里是String类型，可以指明其他类型
        return future;
    }

    @Async
    @Override
    public void generateTaoliaoRecord(PlanTable planTable) {
        taoliaoService.generateTaoliao(planTable);
    }
}
