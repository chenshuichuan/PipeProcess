package llcweb.service.impl;

import llcweb.dao.repository.*;
import llcweb.domain.models.*;
import llcweb.service.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/8/24
 * Time: 13:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArrangeTableServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ArrangeTableRepository arrangeTableRepository;
    @Autowired
    private WorkersRepository workersRepository;
    @Autowired
    private PlanTableRepository planTableRepository;
    @Autowired
    private DepartmentsRepository departmentsRepository;
    @Autowired
    private UnitTableService unitTableService;
    @Autowired
    private UnitProcessingService unitProcessingService;
    @Autowired
    private UnitTableRepository unitTableRepository;
    @Autowired
    private PipeTableService pipeTableService;
    @Autowired
    private PipeProcessingService pipeProcessingService;
    @Autowired
    private PipeTableRepository pipeTableRepository;
    @Autowired
    private BatchTableRepository batchTableRepository;
    @Autowired
    private BatchProcessingRepository batchProcessingRepository;
    @Autowired
    private BatchProcessingService batchProcessingService;
    @Autowired
    private ProcessOrderService processOrderService;

    @Autowired
    private ArrangeTableService arrangeTableService;
    //测试下料派工
    @Test
    public void add() throws Exception {
        for (int i=0;i<50;i++){
            Workers arranger = workersRepository.findOne(2);
            Departments workPlace = departmentsRepository.findOne(11);//一部大管下料工位1
            PlanTable planTable = planTableRepository.findOne(909);//LI-21-一部大管
//            Assert.assertThat(arranger,notNullValue());
//            Assert.assertThat(workPlace,notNullValue());
//            Assert.assertThat(planTable,notNullValue());
            logger.info(arranger.getName()+","+workPlace.getName()+","+planTable.getBatchName()+planTable.getProcessPlace());

            int result = arrangeTableService.add(arranger,planTable,workPlace);
            Assert.assertThat(result,greaterThan(0));
        }
        Workers arranger = workersRepository.findOne(2);
        Departments workPlace = departmentsRepository.findOne(11);//一部大管下料工位1
        PlanTable planTable = planTableRepository.findOne(909);//LI-21-一部大管
        Assert.assertThat(arranger,notNullValue());
        Assert.assertThat(workPlace,notNullValue());
        Assert.assertThat(planTable,notNullValue());
        logger.info(arranger.getName()+","+workPlace.getName()+","+planTable.getBatchName()+planTable.getProcessPlace());

        int result = arrangeTableService.add(arranger,planTable,workPlace);
        Assert.assertThat(result,greaterThan(0));

        ArrangeTable arrangeTable = arrangeTableRepository.findOne(result);
        Assert.assertThat(arrangeTable.getArrangerId(),equalTo(arranger.getId()));
        Assert.assertThat(arrangeTable.getPlanId(),equalTo(planTable.getId()));
    }
    //测试单元派工
    //@Transactional
    @Test
    public void add1() throws Exception {

        for (int i=0;i<50;i++) {
            Workers arranger = workersRepository.findOne(2);
            Departments workPlace = departmentsRepository.findOne(11);//一部大管下料工位1
            UnitTable unitTable = unitTableRepository.findOne(451);//LI-21-一部大管

//            Assert.assertThat(arranger,notNullValue());
//            Assert.assertThat(workPlace,notNullValue());
//            Assert.assertThat(unitTable,notNullValue());
//            logger.info(arranger.getName()+","+workPlace.getName()+","+unitTable.getBatchName()+unitTable.getUnitName());

            int result = arrangeTableService.add(arranger,unitTable,workPlace);
            Assert.assertThat(result,greaterThan(0));
        }
        Workers arranger = workersRepository.findOne(2);
        Departments workPlace = departmentsRepository.findOne(11);//一部大管下料工位1
        UnitTable unitTable = unitTableRepository.findOne(451);//LI-21-一部大管

        Assert.assertThat(arranger,notNullValue());
        Assert.assertThat(workPlace,notNullValue());
        Assert.assertThat(unitTable,notNullValue());
        logger.info(arranger.getName()+","+workPlace.getName()+","+unitTable.getBatchName()+unitTable.getUnitName());

        int result = arrangeTableService.add(arranger,unitTable,workPlace);
        Assert.assertThat(result,greaterThan(0));

        ArrangeTable arrangeTable = arrangeTableRepository.findOne(result);
        Assert.assertThat(arrangeTable.getArrangerId(),equalTo(arranger.getId()));
        Assert.assertThat(arrangeTable.getPlanId(),equalTo(unitTable.getPlanId()));
    }

    @Test
    public void updateById() throws Exception {
    }

    @Test
    public void findById() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

    @Test
    public void getPage() throws Exception {
    }

    @Test
    public void getRecord() throws Exception {
    }

    @Test
    public void findArrangeByWorkplace() throws Exception {
    }

    @Test
    public void isWorkpalceVacancy() throws Exception {
    }

    @Test
    public void arrangeBatchToWorkPlace() throws Exception {
    }

    @Test
    public void arrangePipe() throws Exception {
    }

    @Test
    public void arrangeBatchUnitCut() throws Exception {
    }

    @Test
    public void arrangeUnitToWorkPlace() throws Exception {
    }

}