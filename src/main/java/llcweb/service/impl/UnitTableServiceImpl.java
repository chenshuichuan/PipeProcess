package llcweb.service.impl;

import llcweb.dao.repository.*;
import llcweb.domain.entities.UnitTableInfo;
import llcweb.domain.entities.Units;
import llcweb.domain.models.*;
import llcweb.service.ArrangeTableService;
import llcweb.service.PipeTableService;
import llcweb.service.ProcessOrderService;
import llcweb.service.UnitTableService;
import llcweb.tools.PageParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private UnitProcessingRepository unitProcessingRepository;

    @Autowired
    private BatchTableRepository batchTableRepository;
    @Autowired
    private PipeTableRepository pipeTableRepository;

    @Autowired
    private PlanTableRepository planTableRepository;
    @Autowired
    private DepartmentsRepository departmentsRepository;
    @Autowired
    private ProcessOrderService processOrderService;
    @Autowired
    private ProcessOrderRepository processOrderRepository;
    @Autowired
    private WorkstageRepository workstageRepository;
    @Autowired
    private PipeTableService pipeTableService;


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


    @Override
    public void analyzePlanOfUnits() {
        List<PlanTable> planTableList = planTableRepository.findAll();
        for (PlanTable planTable:planTableList){
            judgeBatchUnitPlanId(planTable.getBatchName());
        }
    }

    /*
  * 根据分页参数以及各字段示例查找信息
  * example 为字段可能包含的值
  * 分页应从1开始，而非0
  * */
    @Override
    public Page<UnitTable> getPage(PageParam pageParam, UnitTable example) {
        //规格定义
        Specification<UnitTable> specification = new Specification<UnitTable>() {

            @Override
            public Predicate toPredicate(Root<UnitTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言

                if (example.getSection()>0) { //添加断言
                    Predicate likeName = cb.equal(root.get("section").as(Integer.class),example.getSection());
                    predicates.add(likeName);
                }
                if (StringUtils.isNotBlank(example.getBatchName())) { //添加断言
                    Predicate likeName = cb.like(root.get("batchName").as(String.class), "%" + example.getBatchName() + "%");
                    predicates.add(likeName);
                }
                if (StringUtils.isNotBlank(example.getUnitName())) { //添加断言
                    Predicate likeName = cb.like(root.get("unitName").as(String.class), "%" + example.getUnitName() + "%");
                    predicates.add(likeName);
                }
                if (example.getNextStage()>0) { //添加断言
                    Predicate likeName = cb.equal(root.get("nextStage").as(Integer.class), example.getNextStage());
                    predicates.add(likeName);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        //分页信息
        Pageable pageable = new PageRequest(pageParam.getCurrentPage() - 1, pageParam.getNumPerPage()); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return unitTableRepository.findAll(specification,pageable);
    }
    @Override
    public List<UnitTable> findByPlanId(int planId) {
        return unitTableRepository.findByPlanId(planId);
    }
    //将UnitTable 封装为Units对象
    @Override
    public List<Units> findUnitsByPlanId(int planId) {
        List<Units> unitsList = new ArrayList<>();
        List<UnitTable> unitTableList = unitTableRepository.findByPlanId(planId);
        for (UnitTable unitTable: unitTableList){
            Units units = new Units(unitTable);
            unitsList.add(units);
        }
        return unitsList;
    }

    //根据部门工序获取可派给该工序的单元
    @Override
    public List<UnitTable> getUnitsByStageId(int departmentStageId) {
        //获取该部门工序信息以及所属工段信息
        Departments stage = departmentsRepository.findOne(departmentStageId);
        if(stage!=null){
            int stageId = stage.getStageId();//得到加工工序，（注意加工工序和部门工序的差别！）
            Departments section = departmentsRepository.findOne(stage.getUpDepartment());
            if(section!=null){
                List<UnitTable> unitTableList = new ArrayList<>();

                //获取可派给该工段的未完成计划的批次
                List<PlanTable> planTableList =
                        planTableRepository.findByProcessPlaceAndActuralEndIsNull(section.getName());

                for (PlanTable planTable:planTableList){
                    //获取计划下所有的下一工序为所求工序的单元
                    List<UnitTable> temp =
                            unitTableRepository.findByPlanIdAndNextStage(planTable.getId(),stageId);
                    unitTableList.addAll(temp);
                }
                return  unitTableList;
            }
        }
        return null;
    }

    //如果当前所在工序为workstage 表的11，则为完成！
    @Override
    public boolean isFinished(UnitTable unitTable) {
        if (unitTable!=null&&unitTable.getProcessState()==11)return true;
        else  return false;
    }

    //将单元信息推进到下一个工序
    //单元派工时更改单元信息 type==1时，是下料派工，要更改管件信息，其他工序不需要更改管件数量信息
    // workPlace 是将要派工去的工位，带有下一工序信息 ,isCut : 下料派工
    @Override
    public int updateUnitToNextStage(UnitTable unitTable,Departments workPlace, boolean isCut) {

        int nextStage = unitTable.getNextStage();
        //更改unitTable 信息，将单元加工顺序推进到这个派工工序
        if(isCut){
            // 此处就是下料
            if(unitTable.getProcessState()!=10||nextStage!=workPlace.getStageId()){
                //10 是未开始下料状态, 此时，单元的下一工序和工位所属工序应该一致！
                logger.error("批次："+unitTable.getBatchName()
                        +"--单元："+unitTable.getUnitName()+"当前状态非未下料或单元的下一工序和工位所属工序应该不一致");
            }
            unitTable.setPipeProcessingNumber(unitTable.getPipeNumber());
            unitTable.setPipeFinishedNumber(0);
        }
        unitTable.setProcessState(nextStage);
        unitTable.setNextStage(processOrderService.nextStage(unitTable.getProcessOrder(),nextStage));

        UnitTable temp = unitTableRepository.save(unitTable);
        if(temp==null || (unitTable.getUnitId()!=temp.getUnitId())){
            logger.error("单元："+unitTable.getUnitId()+"推进下到一工序出错！请检查");
        }
        else return temp.getUnitId();
        return 0;
    }

    /**
     *@Author: Ricardo
     *@Description: 解析单元的加工顺序，以单元内管件最复杂(工序数量，多的)的加工顺序为单元加工顺序
     *@Date: 10:04 2018/8/25
     *@param:
     **/
    @Override
    public String calUnitProcessOrder(UnitTable unitTable,Workstage underStart,Workstage cut,
                                      Workstage bend,Workstage proofread,Workstage weld,
                                      Workstage polish,Workstage surface,Workstage finished,boolean saveToDataBase) {
        List<PipeTable> pipeTableList =
                pipeTableRepository.findByBatchIdAndUnitName(unitTable.getBatchId(),unitTable.getUnitName());
       if(pipeTableList.size()>0){
           String processOrder = "";
           for (PipeTable pipeTable:pipeTableList){
               String order = pipeTableService.calPipeProcessOrder(pipeTable,underStart,cut,bend,proofread,
                       weld,polish,surface,finished,saveToDataBase);
               //找到工序最多的
               if(order.split(",").length>processOrder.split(",").length)
                   processOrder=order;
           }
           //保存到数据库
           if(saveToDataBase){
               ProcessOrder processOrder1 = processOrderRepository.findByOrderList(processOrder);
               if(processOrder1==null){
                   processOrder1 =new ProcessOrder(processOrderService.getProcessOrderString(processOrder),processOrder);
                   processOrder1 = processOrderRepository.save(processOrder1);
               }
               unitTable.setProcessOrder(processOrder1.getId());
               unitTableRepository.save(unitTable);
           }
           logger.info("批次"+unitTable.getBatchName()+"单元"+unitTable.getUnitName()+":加工顺序为");
           return processOrder;
       }
        else return null;
    }

    @Override
    public List<UnitTableInfo> unitToUnitInfo(List<UnitTable> unitTableList,Departments section) {
        List<UnitTableInfo> unitTableInfoList = new ArrayList<>();
        for (UnitTable unitTable: unitTableList){
            UnitTableInfo unitTableInfo = new UnitTableInfo(unitTable);
            unitTableInfo.setSection(section.getName());
            unitTableInfo.setProcessOrder(processOrderService.getProcessOrderString(unitTable.getProcessOrder()));
            Workstage workstage = workstageRepository.findOne(unitTable.getProcessState());
            if (workstage!=null){
                unitTableInfo.setProcessState(workstage.getName());
                //获取当前工序加工情况
                if(workstage.getId()!=10&&workstage.getId()!=11){
                    List<UnitProcessing> unitProcessingList =
                            unitProcessingRepository.findByUnitIdAndProcessState(unitTable.getUnitId(),unitTable.getProcessState());
                    if(unitProcessingList.size()!=1)logger.info("单元加工信息异常！unitId="+unitTable.getUnitId());
                    if(unitProcessingList.size()>1){
                        unitTableInfo.setPipeProcessingNumber(unitProcessingList.get(0).getPipeProcessingNumber());
                        unitTableInfo.setPipeFinishedNumber(unitProcessingList.get(0).getPipeFinishedNumber());
                    }
                    //否则默认就用unittable里的
                }
            }
             workstage = workstageRepository.findOne(unitTable.getNextStage());
            if (workstage!=null)unitTableInfo.setNextStage(workstage.getName());
            unitTableInfoList.add(unitTableInfo);
        }
        return unitTableInfoList;
    }
}
