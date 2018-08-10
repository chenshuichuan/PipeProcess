package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.PlanTableRepository;
import llcweb.domain.models.PlanTable;
import llcweb.service.ArrangeTableService;
import llcweb.service.PlanTableService;
import llcweb.service.UnitTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */
@Service
public class PlanTableServiceImpl implements PlanTableService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private PlanTableRepository planTableRepository;
    @Autowired
    private UnitTableService unitTableService;

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

    @Override
    public void analyzePlan() {
        List<PlanTable> planTableList = planTableRepository.findAll();
        for (PlanTable planTable:planTableList){
            unitTableService.judgeBatchUnitPlanId(planTable.getBatchName());
        }
    }
}
