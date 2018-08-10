package llcweb.service.impl;

import llcweb.dao.repository.*;
import llcweb.domain.models.BatchTable;
import llcweb.domain.models.Departments;
import llcweb.domain.models.PlanTable;
import llcweb.domain.models.UnitTable;
import llcweb.service.ArrangeTableService;
import llcweb.service.UnitTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */
@Service
public class UnitTableServiceImpl implements UnitTableService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private UnitTableRepository unitTableRepository;
    @Autowired
    private BatchTableRepository batchTableRepository;
    @Autowired
    private PipeTableRepository pipeTableRepository;

    @Autowired
    private PlanTableRepository planTableRepository;
    @Autowired
    private DepartmentsRepository departmentsRepository;

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
     *@Description: 解析所有单元所属的计划
     *@Date: 10:36 2018/8/10
     *@param:
     **/
    @Override
    public void turnUnit(){
        List<UnitTable> unitTableList = unitTableRepository.findAll();

        for (UnitTable unitTable: unitTableList) {
            BatchTable batchTable =
                    batchTableRepository.findByShipCodeAndBatchName(unitTable.getShipCode(),unitTable.getBatchName());

            if(batchTable!=null){
                System.out.println(batchTable.getBatchName()+"--id= "+batchTable.getBatchId());
                unitTable.setBatchId(batchTable.getBatchId());
                unitTableRepository.save(unitTable);
            }else{
                logger.error("unit id="+unitTable.getUnitId()+" can't find a batch!");
            }
        }
    }

    /**
     *@Author: Ricardo
     *@Description: 计算某单元包含的管件数量
     *@Date: 11:04 2018/8/10
     *@param:
     **/
    @Override
    public int countPipeNumberOfUnit(UnitTable unitTable) {
        if(unitTable!= null){
            return pipeTableRepository.countByBatchIdAndUnitName(unitTable.getBatchId(),unitTable.getUnitName());
        }
        return 0;
    }
    /**
     *@Author: Ricardo
     *@Description: 计算所有的单元包含的管件数量，并保存
     *@Date: 11:04 2018/8/10
     *@param:
     **/
    @Override
    public void countPipeNumberOfUnits() {
        List<UnitTable> unitTableList = unitTableRepository.findAll();
        for (UnitTable unitTable: unitTableList){
            int pipeNumber = countPipeNumberOfUnit(unitTable);
            unitTable.setPipeNumber(pipeNumber);
            unitTableRepository.save(unitTable);
            System.out.println("batch="+unitTable.getBatchName()+" unit="+unitTable.getUnitName()+",size="+pipeNumber);
        }
    }

    /**
     *@Author: Ricardo
     *@Description: 根据批次以及管类型，设置该批次下所有单元关联的计划
     *@Date: 17:16 2018/8/9
     *@param: batchName :
     **/
    private void setUnitesPlanId(String batchName,String pattern,PlanTable planTable){
        List<UnitTable> unitTableList1 = unitTableRepository.findByBatchNameAndUnitNameLike(batchName,pattern);
        for (UnitTable unitTable: unitTableList1){
            unitTable.setPlanId(planTable.getId());
            Departments departments = departmentsRepository.findByName(planTable.getProcessPlace());
            if(departments==null){
                logger.error(planTable.getProcessPlace()+" 未找到对应的部门id！请检查！");
                return;
            }
            unitTable.setSection(departments.getId());
            unitTableRepository.save(unitTable);
            logger.info("1批次"+batchName+",单元"+unitTable.getUnitName()+",planid="+planTable.getId()
                    +","+departments.getName());
        }
    }
    /**
     *@Author: Ricardo
     *@Description: 根据信息，设置某个单元关联的计划
     *@Date: 17:16 2018/8/9
     *@param: pattern : 正则表达式-"L.*"等
     * @paran： pattern2 : "大管" 等 一部大管工段 加工车间包含的
     **/
    private boolean setUnitePlanId(UnitTable unitTable,String pattern,String pattern2,List<PlanTable> planTableList){

        boolean isMatch = Pattern.matches(pattern, unitTable.getUnitName());
        if(isMatch){//
            for (PlanTable planTable: planTableList){
                if(planTable.getProcessPlace().contains(pattern2)){
                    unitTable.setPlanId(planTable.getId());
                    Departments departments = departmentsRepository.findByName(planTable.getProcessPlace());
                    if(departments==null){
                        logger.error(planTable.getProcessPlace()+" 未找到对应的部门id！请检查！");
                        return false;
                    }
                    unitTable.setSection(departments.getId());
                    unitTableRepository.save(unitTable);
                    logger.info("2批次"+planTable.getBatchName()+",单元"+unitTable.getUnitName()+",planid="+planTable.getId()
                            +",加工工段"+departments.getName());
                    return true;
                }
            }
        }
        return false;

    }
    //判断某批次下的单元所属的planid
    //正常情况来说，一次上传的批次的计划会设计到该批次的所有单元，所以不需要担心批次的计划会分批上次
    @Override
    public void judgeBatchUnitPlanId(String batchName){
        List<PlanTable> planTableList = planTableRepository.findByBatchName(batchName);
        //处理正常描述情况的单元
        List<PlanTable>planTableList1 = new ArrayList<>();
        for (PlanTable planTable: planTableList){
            if(planTable.getBatchDescription().contains("大管")){
                setUnitesPlanId(batchName,"L%",planTable);
            }
            else if(planTable.getBatchDescription().contains("中小管")){
                setUnitesPlanId(batchName,"M%",planTable);
                setUnitesPlanId(batchName,"S%",planTable);
            }
            else if(planTable.getBatchDescription().contains("中管")){
                setUnitesPlanId(batchName,"M%",planTable);
            }
            else if(planTable.getBatchDescription().contains("小管")){
                setUnitesPlanId(batchName,"S%",planTable);
            }

            else if(planTable.getBatchDescription().contains("特殊管")){
                setUnitesPlanId(batchName,"E%",planTable);
            }
            ///记下描述中没有管件类型的计划
            else planTableList1.add(planTable);
        }
        //计划描述中没有按照规定，加上管件描述
        if (planTableList1.size()>0){
            logger.error("计划涉及单元的解析失败！存在未加上管件描述的计划条目！");
        }
        //处理上述未处理到的单元
        List<UnitTable> unitTableList = unitTableRepository.findByBatchNameAndPlanIdIsNull(batchName);
        //正常来说，只有大管，中管，小管，特殊管，中小管 五种描述
        //所以unitTableList 已经处理完了
        if(unitTableList!=null){
            logger.error("解析"+batchName+"批次下的单元所属的计划出错！");
            String units="";
            //根据单元前缀以及计划对应的加工工段，分配
            for (UnitTable unitTable: unitTableList){
                if(setUnitePlanId(unitTable,"L.*","大管",planTableList));
                else if(setUnitePlanId(unitTable,"M.*","中小管",planTableList));
                else if(setUnitePlanId(unitTable,"S.*","中小管",planTableList));
                else if(setUnitePlanId(unitTable,"E.*","特殊管",planTableList));

                    //计划加工工段中没有该单元相关的工段
                else units+=unitTable.getUnitName()+"-";
            }
            if(units.length()>0){
                logger.error(batchName+"以下单元无法解析对应的加工计划，请检查！"+units);
            }
        }

    }


}
