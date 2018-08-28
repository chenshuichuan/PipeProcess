package llcweb.service.impl;

import llcweb.dao.repository.*;
import llcweb.domain.entities.ArrangeRecord;
import llcweb.domain.models.*;
import llcweb.service.*;
import llcweb.tools.DateUtil;
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

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */
@Service
public class ArrangeTableServiceImpl implements ArrangeTableService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
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
    private ScannerTableRepository scannerTableRepository;
    @Autowired
    private ProcessOrderService processOrderService;
    @Autowired
    private UsersService usersService;

    @Transactional
    @Override
    public void add() {
        logger.info("service add");
    }


    /**
     *@Author: Ricardo
     *@Description: 构建批次派工记录
     *@Date: 13:50 2018/8/24
     *@param:
     **/
    @Transactional
    @Override
    public int add(Workers arranger, PlanTable planTable, Departments workPlace) {
        ArrangeTable arrangeTable = new ArrangeTable();
        arrangeTable.setArrangeType(1);//1代表单元派工
        arrangeTable.setName(planTable.getBatchName());
        arrangeTable.setPlanId(planTable.getId());
        arrangeTable.setSection(planTable.getProcessPlace());
        arrangeTable.setWorkplace(workPlace.getName());
        Departments stage = departmentsRepository.findOne(workPlace.getUpDepartment());
        if(stage!=null){
            arrangeTable.setStage(stage.getName());
        }
        else {
            logger.error("工位"+workPlace.getName()+"的工序不存在！");
            return 0;
        }
        arrangeTable.setArrangerId(arranger.getId());
        logger.info("updateTime = "+ DateUtil.formatDateTimeString(arrangeTable.getUpdateTime()));
        ArrangeTable temp = arrangeTableRepository.save(arrangeTable);

        if(temp!=null)return  temp.getArrangeId();
        else{
            logger.error("批次派工无法生成派工记录！请检查");
            return 0;
        }
    }
    //构建单元派工记录
    @Transactional
    @Override
    public int add(Workers arranger, UnitTable unitTable, Departments workPlace) {
        ArrangeTable arrangeTable = new ArrangeTable();
        arrangeTable.setArrangeType(2);//2代表单元派工
        arrangeTable.setName(unitTable.getBatchName()+"---"+unitTable.getUnitName());
        arrangeTable.setPlanId(unitTable.getPlanId());
        Departments section = departmentsRepository.findOne(unitTable.getSection());
        arrangeTable.setWorkplace(workPlace.getName());
        if(section!=null){
            arrangeTable.setSection(section.getName());
        }
        else logger.error("要派工单元"+unitTable.getBatchName()+"---"+unitTable.getUnitName()+"的加工工段不存在！请检查");
        Departments stage = departmentsRepository.findOne(workPlace.getUpDepartment());
        if(stage!=null){
            arrangeTable.setStage(stage.getName());
        }
        else {
            logger.error("工位"+workPlace.getName()+"的工序不存在！");
            return 0;
        }
        arrangeTable.setArrangerId(arranger.getId());
        logger.info("updateTime = "+ DateUtil.formatDateTimeString(arrangeTable.getUpdateTime()));
        ArrangeTable temp = arrangeTableRepository.save(arrangeTable);
        if(temp!=null)return  temp.getArrangeId();
        else{
            logger.error("无法生成arrangeTable记录！");
            return 0;
        }
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

    /*
   * 根据分页参数以及各字段示例查找信息
   * example 为字段可能包含的值
   * 分页应从1开始，而非0
   * */
    @Override
    public Page<ArrangeTable> getPage(PageParam pageParam, ArrangeTable example) {
        //规格定义
        Specification<ArrangeTable> specification = new Specification<ArrangeTable>() {
            /**
             * @param root  实体对象引用
             * @param query 规则查询对象
             * @param cb    规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<ArrangeTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if (StringUtils.isNotBlank(example.getName())) { //添加断言
                    Predicate likeName = cb.like(root.get("name").as(String.class), "%" + example.getName() + "%");
                    predicates.add(likeName);
                }
                if (example.getArrangeType()!=null) { //添加断言
                    Predicate predicate = cb.equal(root.get("arrangeType").as(Integer.class), example.getArrangeType());
                    predicates.add(predicate);
                }
                if (StringUtils.isNotBlank(example.getSection())) { //添加断言
                    Predicate predicate = cb.equal(root.get("section").as(String.class), example.getSection());
                    predicates.add(predicate);
                }
                else{//默认仅显示用户管理的部门
                    CriteriaBuilder.In<Object> in = cb.in(root.get("section"));
                    List<Departments> sectionList =  usersService.getSections(usersService.getCurrentUser());
                    for (Departments section: sectionList){
                        in.value(section.getName());
                    }
                    predicates.add(in);
                }
                if (StringUtils.isNotBlank(example.getStage())) { //添加断言
                    Predicate predicate = cb.equal(root.get("stage").as(String.class), example.getStage());
                    predicates.add(predicate);
                }
                if (example.getIsFinished()!=null) { //添加断言
                    Predicate predicate = cb.equal(root.get("isFinished").as(Integer.class), example.getIsFinished());
                    predicates.add(predicate);
                }
                if (example.getWorkerId()!=null) { //添加断言
                    Predicate predicate = cb.equal(root.get("workerId").as(Integer.class), example.getWorkerId());
                    predicates.add(predicate);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        //分页信息
        Pageable pageable = new PageRequest(pageParam.getCurrentPage() - 1, pageParam.getNumPerPage()); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return arrangeTableRepository.findAll(specification,pageable);
    }

    //封装派工记录
    @Override
    public ArrangeRecord getRecord(ArrangeTable arrangeTable) {
        ArrangeRecord arrangeRecord =new ArrangeRecord(arrangeTable);
        PlanTable planTable = planTableRepository.findOne(arrangeTable.getPlanId());
        String plan = "";
        if (planTable!=null)
            plan = planTable.getBatchName()+"-->"+ planTable.getBatchDescription()+"-->"+planTable.getProcessPlace();
        else {
            plan = "派工记录对应批次未找到！请检查数据库";
            logger.error(plan);
        }
        arrangeRecord.setPlan(plan);
        String worker ="";
        Workers workers ;
        if(arrangeTable.getWorkerId()!=null){
            //新的派工记录，没有工人领取，则此处workerId为空，查找会出错！
            workers = workersRepository.findOne(arrangeTable.getWorkerId());
            if (workers!=null)
                worker = workers.getCode()+"-"+workers.getName();
            else worker = "暂无工人领取该派工";
        }
        else worker = "暂无工人领取该派工";
        arrangeRecord.setWorker(worker);

        workers = workersRepository.findOne(arrangeTable.getArrangerId());
        worker ="";
        if (workers!=null)
            worker = workers.getCode()+"-"+workers.getName();
        else{
            worker = "派工记录对应派工者未找到！请检查数据库";
            logger.error(plan);
        }
        arrangeRecord.setArranger(worker);
        return arrangeRecord;
    }

    //封装派工记录
    @Override
    public List<ArrangeRecord> arrangeTableToArrangeRecord(List<ArrangeTable> arrangeTableList){
        List<ArrangeRecord> arrangeRecordList = new ArrayList<>();
        for (ArrangeTable arrangeTable: arrangeTableList){
            ArrangeRecord arrangeRecord = getRecord(arrangeTable);
            arrangeRecordList.add(arrangeRecord);
        }
        return arrangeRecordList;
    }

    @Override
    public List<ArrangeTable> findArrangeByWorkplace(String section, String stage, String workplace, int isFinished) {

        return arrangeTableRepository.
                findBySectionAndStageAndWorkplaceAndIsFinished(section,stage,workplace,isFinished);
    }


    /**
     *@Author: Ricardo
     *@Description:  //判断工位是否空闲 此函数应该直接放在departments表，做一个计数，计数未完成派工，计数为0时代表空闲，
     *@Date: 21:49 2018/8/22
     *@param:
     **/
    @Override
    public boolean isWorkpalceVacancy(Departments workplace) {
        //Departments workplace = departmentsRepository.findOne(workplaceId);
        if(workplace==null||workplace.getLevel()!=2){
            logger.error("未找到或查找的不是工位！");
            return false;
        }
        Departments stage = departmentsRepository.findOne(workplace.getUpDepartment());
        Departments section = departmentsRepository.findOne(stage.getUpDepartment());
        //查找未完成派工
        List<ArrangeTable> arrangeTableList =
                findArrangeByWorkplace(section.getName(),stage.getName(),workplace.getName(),0);
        if(arrangeTableList!=null && arrangeTableList.size()>0)return false;

        return true;
    }

    /**
     *@Author: Ricardo
     *@Description: plan批次派工到工位,出错返回0，正常返回1
     *@Date: 19:13 2018/8/22
     *@param: arranger:派工者， planTable: 被派工的计划批次，workPlace：被派工工位
     * 待实现: 此函数内的操作应保证数据完整性，故产生异常，则应该回滚！
     **/
    @Transactional
    @Override
    public int arrangeBatchToWorkPlace(Workers arranger, PlanTable planTable, Departments workPlace) {
        //构建批次派工记录
        int arrangeId = add(arranger,planTable,workPlace);
        UnitTable unitTable = new UnitTable();
        int pipeNumber =0;//batchProcessing表需要
        //获取计划批次下所有单元，
        List<UnitTable> unitTableList = unitTableRepository.findByPlanId(planTable.getId());
        //进行单元派工
        for (int i=0; i<unitTableList.size();++i){
            unitTable = unitTableList.get(i);
            if(unitTable.getPipeNumber()<=0)
                continue;//跳过这类单元 应产生一个异常记录！
            if(unitTableService.isFinished(unitTable)||unitTable.getProcessState()!=10){
                logger.error("批次："+planTable.getBatchName()+"的单元："+unitTable.getUnitName()+"已经完工！不处于未加工状态请检查数据库！");
            }
            //，进行单元派工
            arrangeBatchUnitCut(unitTable, workPlace,arrangeId);

            pipeNumber+=unitTable.getPipeNumber();
        }

        planTable.setIsCutted(0);//设置计划为下料派工状态
        planTableRepository.save(planTable);
        //构建batchProcessing记录
        return batchProcessingService.add(unitTable.getBatchId(),planTable.getProcessPlace(),
                pipeNumber,unitTableList.size(),arrangeId);
    }


    /**
     *@Author: Ricardo
     *@Description:  //管件派工到下一个工序
     *@Date: 13:51 2018/8/23
     *@param: arrangeId 可以留着到管件级别的派工时使用
     **/
    @Transactional
    @Override
    public int arrangePipe(PipeTable pipeTable, Departments workPlace,int arrangeId) {
        //构建PipeProcessing
        //得到管件的下一个工序,比如当前为未加工状态，下一工序为下料工序
        int nextStage = pipeTable.getNextStage();
        int nextProcessIndex = processOrderService.currentStageIndex(pipeTable.getProcessOrder(),nextStage);

        //这里有个逻辑，就是nextStage是最后一个完成状态时，这时是不可能也不需要派工到完成状态的，所以不必担心会产生下面的记录
        int pipeProcessingId = pipeProcessingService.add(pipeTable,nextStage,nextProcessIndex,workPlace.getId());
        //更改PipeTable信息，推进管件到下一个工序
        int pipeTableId =
                pipeTableService.updatePipeToNextStage(pipeTable,nextStage,nextProcessIndex);
        if(pipeProcessingId<=0||pipeTableId<=0)logger.error("管件"+pipeTable.getPipeId()+"派工到"+workPlace.getName()+"出错！");
        else return 1;
        return 0;
    }

    /**
     *@Author: Ricardo
     *@Description: 下料工序的的单元派工到工位,出错返回0，正常返回1 ，不需要产生单元派工记录！
     *@Date: 19:13 2018/8/22
     *@param: arranger:派工者， unitTable: 被派工的单元，workPlace：被派工工位, 对应的派工记录id
     * 待实现: 此函数内的操作应保证数据完整性，故产生异常，则应该回滚！
     **/
    @Transactional
    @Override
    public int arrangeBatchUnitCut(UnitTable unitTable, Departments workPlace,int arrangeId) {

        //构建unitProcessing
        int unitProcessingId =  unitProcessingService.add(unitTable.getUnitId(),workPlace.getStageId(),1,
                workPlace.getId(),unitTable.getPipeNumber(),arrangeId);
        //更改UnitTable信息，推进单元到下一个工序
        int unitTbaleId=  unitTableService.updateUnitToNextStage(unitTable,workPlace,true);
        //获取单元下所有管件，进行管件派工
        List<PipeTable> pipeTableList =
                pipeTableRepository.findByBatchIdAndUnitName(unitTable.getBatchId(),unitTable.getUnitName());
        for (PipeTable pipeTable: pipeTableList){
            //检查管件当前状态是否正常
            if(pipeTable.getProcessState()!=10||pipeTable.getNextStage().intValue()!=workPlace.getStageId()){
                //非未开工状态 下一工序和要派工工位所属工序不符合
                logger.error(pipeTable.getPipeId()+"管件非未开工状态！请检查！");
                continue;
            }
            // 管件派工
            arrangePipe(pipeTable,workPlace,arrangeId);
        }
        if(unitProcessingId<=0||unitTbaleId<=0)
            logger.error("下料单元"+unitTable.getUnitId()+"派工到"+workPlace.getName()+"出错！");
        else return 1;
        return 0;
    }
    /**
     *@Author: Ricardo
     *@Description: //除下料外其他的工序的单元派工 单元派工到工位,出错返回0，正常返回1
     *@Date: 19:13 2018/8/22
     *@param: arranger:派工者， unitTable: 被派工的单元，workPlace：被派工工位
     * 待实现: 此函数内的操作应保证数据完整性，故产生异常，则应该回滚！
     **/
    @Transactional
    @Override
    public int arrangeUnitToWorkPlace(Workers arranger, UnitTable unitTable, Departments workPlace) {
        //产生单元派工记录
        int arrangeId = add(arranger,unitTable,workPlace);

        //构建unitProcessing
        int unitProcessingId =  unitProcessingService.add(unitTable.getUnitId(),workPlace.getStageId(),1,
                workPlace.getId(),unitTable.getPipeNumber(),arrangeId);
        //更改UnitTable信息，推进单元到下一个工序
        int unitTbaleId=  unitTableService.updateUnitToNextStage(unitTable,workPlace,false);
        //获取单元下所有管件，进行管件派工
        List<PipeTable> pipeTableList =
                pipeTableRepository.findByBatchIdAndUnitName(unitTable.getBatchId(),unitTable.getUnitName());
        for (PipeTable pipeTable: pipeTableList){

            if(pipeTable.getNextStage().intValue()!=workPlace.getStageId().intValue()){//Integer的值比较
                //非未开工状态 下一工序和要派工工位所属工序不符合
                logger.error(pipeTable.getPipeId()+"管件非未开工状态！请检查！");
                continue;
            }
            // 管件派工
            arrangePipe(pipeTable,workPlace,arrangeId);
        }
        if(unitProcessingId<=0||unitTbaleId<=0)
            logger.error("下料单元"+unitTable.getUnitId()+"派工到"+workPlace.getName()+"出错！");
        else return 1;
        return 0;
    }

    /**
     *@Author: Ricardo
     *@Description: //根据当前登录用户获取其可以查看的派工记录
     *@Date: 14:32 2018/8/27
     *@param:
     **/
    @Override
    public List<ArrangeTable> getUsersArrangeTable(int arrangeType, int isFinished) {
        Users users = usersService.getCurrentUser();
        List<ArrangeTable> arrangeTableList = new ArrayList<>();
        if(users.getRole()<2){//非工人,根据管理的工段获取
            logger.info("管理员查看派工情况");
            //得到管理的工段
            List<Departments> sectionList =  usersService.getSections(users);
            for (Departments section: sectionList){
                //得到工段下相关的所有下料派工记录
                List<ArrangeTable> temp =
                        arrangeTableRepository.findBySectionAndArrangeTypeAndIsFinished(section.getName(),arrangeType,isFinished);
                arrangeTableList.addAll(temp);
                logger.info("1temp.size="+temp.size());
            }
        }else {
            logger.info("工人查看派工情况");
            //工人，看其是否绑定工位，根据工位是否被派工记录获取
            ScannerTable scannerTable = scannerTableRepository.findByWorkerId(users.getWorkerId());
            if(scannerTable!=null){
                //找到该工位
                Departments workPlace = departmentsRepository.findOne(scannerTable.getWorkplaceId());
                if(workPlace!=null&&workPlace.getLevel()==2){
                    List<ArrangeTable> temp = arrangeTableRepository.
                            findByWorkplaceAndArrangeTypeAndIsFinished(workPlace.getName(),arrangeType,isFinished);
                    arrangeTableList.addAll(temp);
                    logger.info("1temp.size="+temp.size());
                }
                else logger.error("扫码枪未绑定到工位！");
            }

        }
        return arrangeTableList;
    }

}
