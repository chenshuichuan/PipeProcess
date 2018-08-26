package llcweb.service.impl;

import llcweb.dao.repository.*;
import llcweb.domain.models.PipeTable;
import llcweb.domain.models.ProcessOrder;
import llcweb.domain.models.UnitTable;
import llcweb.domain.models.Workstage;
import llcweb.service.ProcessOrderService;
import llcweb.service.UnitTableService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/8/25
 * Time: 19:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTableServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ArrangeTableRepository arrangeTableRepository;
    @Autowired
    private PipeTableRepository pipeTableRepository;
    @Autowired
    private BatchTableRepository batchTableRepository;
    @Autowired
    private UnitTableRepository unitTableRepository;
    @Autowired
    private ProcessOrderService processOrderService;
    @Autowired
    private WorkstageRepository workstageRepository;
    @Autowired
    private ProcessOrderRepository processOrderRepository;
    @Autowired
    private PipeTableServiceImpl pipeTableService;
    @Autowired
    private UnitTableService unitTableService;
    @Transactional
    @Test
    public void judgeBatchUnitPlanId() throws Exception {
    }

    @Test
    public void analyzePlanOfUnits() throws Exception {
    }

    @Test
    public void getPage() throws Exception {
    }

    @Test
    public void findByPlanId() throws Exception {
    }

    @Test
    public void findUnitsByPlanId() throws Exception {
    }

    @Test
    public void getUnitsByStageId() throws Exception {
    }

    @Test
    public void isFinished() throws Exception {
    }

    @Test
    public void updateUnitToNextStage() throws Exception {
    }

    @Test
    public void calUnitProcessOrder() throws Exception {
        Workstage underStart = workstageRepository.findByName("未开始");//未开始
        Workstage cut = workstageRepository.findByName("下料");
        Workstage bend = workstageRepository.findByName("弯管");
        Workstage proofread = workstageRepository.findByName("校管");
        Workstage weld = workstageRepository.findByName("焊接");
        Workstage polish = workstageRepository.findByName("打磨");
        Workstage surface = workstageRepository.findByName("表处");
        Workstage finished = workstageRepository.findByName("已完成");
        UnitTable unitTable = unitTableRepository.findOne(121);//50 121 是带F的

        String processOrder = unitTableService.calUnitProcessOrder(unitTable,underStart,cut,bend,proofread,
                weld,polish,surface,finished,true);
        logger.info("解析的加工顺序："+processOrder);
        Assert.assertThat(processOrder,notNullValue());
        Assert.assertThat(processOrder.length(),greaterThan(1));

        ProcessOrder processOrder1 = processOrderRepository.findByOrderList(processOrder);
        logger.info(""+processOrder1.getId());
        logger.info(processOrder1.getOrderList());
        logger.info(processOrder1.getName());
    }

    //初始化单元信息，当前所处工序，下一工序
    @Test
    public void initUnitTable(){

        List<UnitTable> unitTableList = unitTableRepository.findAll();
        for(UnitTable unitTable:unitTableList){
            if(unitTable.getPipeNumber()!=null&&unitTable.getPipeNumber()>0){
                unitTable.setProcessState(10);
                unitTable.setNextStage(1);
                unitTableRepository.save(unitTable);
            }

        }
    }

}