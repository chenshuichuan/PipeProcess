package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.ProcessOrderRepository;
import llcweb.dao.repository.WorkstageRepository;
import llcweb.domain.models.ProcessOrder;
import llcweb.domain.models.Workstage;
import llcweb.service.ArrangeTableService;
import llcweb.service.ProcessOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */
@Service
public class ProcessOrderServiceImpl implements ProcessOrderService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private ProcessOrderRepository processOrderRepository;
    @Autowired
    private WorkstageRepository workstageRepository;

    @Transactional
    @Override
    public void add() {
        logger.info("service add");
    }

    @Transactional
    @Override
    public void updateById(int id) {
        logger.info("service updateById id="+id);
    }

    @Override
    public void findById(int id) {
        logger.info("service findById id="+id);
    }
    @Transactional
    @Override
    public void deleteById(int id) {
        logger.info("service add id="+id);
    }

    /**
     *@Author: Ricardo
     *@Description:
     * 根据跟定的加工工序id，以及当前工序id，获得下一工序id
     * 加工工序就是由一系列工序id组成的字符串，并包含未开始、已完工两个标志工序
     *@Date: 10:28 2018/8/23
     *@param:
     **/
    @Override
    public int nextStage(int processOrderId, int currentStage) {
        ProcessOrder processOrder = processOrderRepository.findOne(processOrderId);
        if(processOrder!=null&&processOrder.getOrderList()!=null){
            String[] orderList = processOrder.getOrderList().split(",");
            for (int i = 0;i<orderList.length;i++){
                int stageId= Integer.parseInt(orderList[i]);
                if(currentStage==stageId&&i!=(orderList.length-1)){//currentStage不是最后一个
                    return Integer.parseInt(orderList[i+1]);//返回下一个
                }
                else if(currentStage==stageId&&i==(orderList.length-1)){//currentStage就等于最后一个
                    return currentStage;
                }
            }
        }
        else logger.error("查找的工序id"+processOrderId+"不存在或其加工工序列表为空！请检查数据库！");
        logger.error("查找的工序id"+processOrderId+"当前工序："+currentStage+"无法解析下一工序！");
        return 0;
    }

    /**
     *@Author: Ricardo
     *@Description:
     * 根据跟定的加工工序列表，以及当前工序id，获得下一工序id
     * 加工工序就是由一系列工序id组成的字符串，并包含未开始、已完工两个标志工序
     *@Date: 10:28 2018/8/23
     *@param:
     **/
    @Override
    public int nextStage(String processOrderList, int currentStage) {

        String[] orderList = processOrderList.split(",");
        for (int i = 0;i<orderList.length;i++){
            int stageId= Integer.parseInt(orderList[i]);
            if(currentStage==stageId&&i!=(orderList.length-1)){//currentStage不是最后一个
                return Integer.parseInt(orderList[i+1]);//返回下一个
            }
            else if(currentStage==stageId&&i==(orderList.length-1)){//currentStage就等于最后一个
                return currentStage;
            }
        }
        logger.error("查找的工序"+processOrderList+"当前工序："+currentStage+"无法解析下一工序！");
        return 0;
    }

    /**
     *@Author: Ricardo
     *@Description:
     * 根据跟定的加工工序id，以及当前工序id，获得当前工序所处的工序，从1开始的index
     *@Date: 10:28 2018/8/23
     *@param:
     **/
    @Override
    public int currentStageIndex(int processOrderId, int currentStage) {
        ProcessOrder processOrder = processOrderRepository.findOne(processOrderId);
        if(processOrder!=null&&processOrder.getOrderList()!=null){
            String[] orderList = processOrder.getOrderList().split(",");
            for (int i = 0;i<orderList.length;i++){
                int stageId= Integer.parseInt(orderList[i]);
                if(currentStage==stageId){//currentStage就等于最后一个
                    return i+1;
                }
            }
        }
        else logger.error("查找的工序id"+processOrderId+"不存在或其加工工序列表为空！请检查数据库！");

        return 0;
    }

    @Override
    public String getProcessOrderString(String orderList) {
        //根据加工工序id列表 1,,2,3,4 等解析其名字列表 下料,弯管,校管
        String nameList ="";
        String[] idList= orderList.split(",");
        for (int i=0;i<idList.length;i++){
            String id = idList[i];
            Workstage workstage = workstageRepository.findOne(Integer.parseInt(id));
            if(i==0)nameList += workstage.getName();
            else nameList +=","+ workstage.getName();
        }
        return nameList;
    }

    //返回除去未开始，已完成两个工序的字符串
    @Override
    public String getProcessOrderString(int processOrderId) {
        ProcessOrder processOrder = processOrderRepository.findOne(processOrderId);
        if(processOrder!=null){
            String order = processOrder.getName();
            int first = order.indexOf(",");
            int last =order.lastIndexOf(",");
            return order.substring(first+1,last);
        }
        return "";
    }

}
