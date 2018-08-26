package llcweb.service.impl;

import llcweb.dao.repository.*;
import llcweb.domain.models.PlanTable;
import llcweb.domain.models.ProcessOrder;
import llcweb.domain.models.UnitTable;
import llcweb.domain.models.Workstage;
import llcweb.service.PlanTableService;
import llcweb.service.ProcessOrderService;
import llcweb.service.UnitTableService;
import llcweb.tools.PageParam;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/8/22
 * Time: 13:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanTableServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PlanTableService planTableService;
    @Autowired
    private PlanTableRepository planTableRepository;
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
    @Test
    public void findById() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

    @Test
    public void getPage() throws Exception {
        PlanTable planTable = new PlanTable();
        planTable.setProcessPlace("一部特殊管工段");
        Page<PlanTable> planPage = planTableService.getPage(new PageParam(1,20),planTable);
        List<PlanTable> planTableList = planPage.getContent();
        logger.info("size= "+planPage.getTotalElements());
        Assert.assertThat(planTableList.size(),greaterThan(1));
    }

    @Test
    public void isPlanFinished() throws Exception {
    }
    @Test
    public void calUnitsOfPlanProcessOrder() throws Exception {

        List<PlanTable> planTableList = planTableRepository.findAll();
        for (PlanTable planTable:planTableList){
            planTableService.calUnitsOfPlanProcessOrder(planTable);
        }

    }
}