package llcweb.service.impl;

import llcweb.dao.repository.*;
import llcweb.domain.entities.CutPipeInfo;
import llcweb.domain.models.*;
import llcweb.service.*;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */
@Service
public class TaoliaoServiceImpl implements TaoliaoService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private PipeTableRepository pipeTableRepository;
    @Autowired
    private PipeTableService pipeTableService;
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
    private TaoliaoRepository taoliaoRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ArrangeTableRepository arrangeTableRepository;
    @Autowired
    private ArrangeTableService arrangeTableService;
    @Autowired
    private PipeProcessingService pipeProcessingService;


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

    //分页查询mouy
    /**
     *@Author: Ricardo
     *@Description: 分页查询某批次的管件
     *@Date: 10:17 2018/8/10
     *@param: pageParam:分页参数
     *@param: batchId: 要查找的批次id
     **/
    @Override
    public Page<Taoliao> getPage(PageParam pageParam, Taoliao taoliao) {
        //规格定义
        Specification<Taoliao> specification = new Specification<Taoliao>() {
            @Override
            public Predicate toPredicate(Root<Taoliao> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if(taoliao.getPlanId()!=null && taoliao.getPlanId()>0){ //添加断言
                    Predicate likeUserName = cb.equal(root.get("planId").as(Integer.class),taoliao.getPlanId());
                    predicates.add(likeUserName);
                }
                else{//获取所有未下料
                    //获取登录用户能查看的下料未完成的批次派工
                    List<ArrangeTable> arrangeTableList = arrangeTableService.getUsersArrangeTable(1,0);
                    CriteriaBuilder.In<Object> in = cb.in(root.get("planId"));
                    for (ArrangeTable arrangeTable: arrangeTableList){
                        in.value(arrangeTable.getPlanId());
                    }
                    predicates.add(in);
                }
                if(taoliao.getIsTaoliao()!=null){ //空的话，已套料，未套料的都查
                    Predicate likeUserName = cb.equal(root.get("isTaoliao").as(Integer.class),taoliao.getIsTaoliao());
                    predicates.add(likeUserName);
                }
                if(StringUtils.isNotBlank(taoliao.getPipeMaterial())){ //添加断言

                    Predicate likeUserName = cb.equal(root.get("pipeMaterial").as(String.class),taoliao.getPipeMaterial());
                    predicates.add(likeUserName);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        //分页信息
        Pageable pageable = new PageRequest(pageParam.getCurrentPage()-1,pageParam.getNumPerPage()); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return taoliaoRepository.findAll(specification,pageable);
    }


    //计划批次下料派工应该生成套料表taoliao，方便套料时显示
    @Override
    public int generateTaoliao(PlanTable planTable){
        //先删除该计划的套料表信息
        List<Taoliao> taoliaoList = taoliaoRepository.findByPlanId(planTable.getId());
        for (Taoliao taoliao : taoliaoList){
            taoliaoRepository.delete(taoliao.getId());
        }

        //得到planId下的所有单元
        List<UnitTable> unitTableList = unitTableRepository.findByPlanId(planTable.getId());
        List<PipeTable> pipeTableList = new ArrayList<>();
        //根据planId拿到planId下的所有管件
        for (UnitTable unitTable: unitTableList){
            List<PipeTable> temp = pipeTableRepository.findByBatchIdAndUnitName(unitTable.getBatchId(),unitTable.getUnitName());
            pipeTableList.addAll(temp);
        }
        logger.info("generateTaoliao pipesize = "+pipeTableList.size());
        //对管件按照管材字段排好序
        Collections.sort(pipeTableList,new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                PipeTable u1 = (PipeTable)o1;
                PipeTable u2 = (PipeTable)o2;
                int i = u1.getPipeMaterial().compareTo(u2.getPipeMaterial());
                return i;
            }
        });

        BatchTable batchTable = batchTableRepository.findByBatchName(planTable.getBatchName());
        int batchId = 0;
        if(batchTable!=null)batchId = batchTable.getBatchId();
        else logger.error("未找到该计划批次！batchName="+planTable.getBatchName());
        int totalLength=0;
        int pipeNumber =0;
        for (int i=0;i<pipeTableList.size();i++){

            PipeTable pipeTable = pipeTableList.get(i);
            //下一个和当前相同
            if(i<pipeTableList.size()-1&&
                    pipeTable.getPipeMaterial().equals(pipeTableList.get(i+1).getPipeMaterial())){
                totalLength += pipeTable.getCutLength();
                pipeNumber++;
            }
            else{
                //保存前查找数据库是否存在相同planId相同pipeMaterial的记录，存在说明数据不对！
                Taoliao temp = taoliaoRepository.findByPlanIdAndPipeMaterial(planTable.getId(),pipeTable.getPipeMaterial());
                if(temp!=null){
                    logger.error("该计划下料管材记录已经存在！plaId="+
                            planTable.getId()+",pipeMaterial="+pipeTable.getPipeMaterial());
                    taoliaoRepository.delete(temp.getId());
                }
                //下一个和当前不同，立即保存当前
                Taoliao taoliao = new Taoliao();
                taoliao.setPlanId(planTable.getId());
                taoliao.setBatchId(batchId);

                taoliao.setPipeMaterial(pipeTable.getPipeMaterial());
                taoliao.setTotalLength(totalLength+pipeTable.getCutLength());
                taoliao.setPipeNumber(pipeNumber+1);
                taoliao.setIsTaoliao(0);
                //保存到数据库
                taoliaoRepository.save(taoliao);
                logger.info("如果有值，说明有问题！taoliao id="+taoliao.getId());//确实能取得id..但是并不影响保存新数据
                totalLength=0;
                pipeNumber=0;
            }
        }
        return 0;
    }
    //根据taoliaoId 获取该套料下的管件
    @Override
    public List<PipeTable> getPipeTableByTaoliaoId(int taoliaoId){
        Taoliao taoliao = taoliaoRepository.findOne(taoliaoId);
        List<PipeTable>pipeTableList = new ArrayList<>();
        if(taoliao!=null){
            logger.info("查询套料id="+taoliaoId);
            //获取计划批次下所有单元
            List<UnitTable> unitTableList = unitTableRepository.findByPlanId(taoliao.getPlanId());
            for (UnitTable unitTable:unitTableList){
                //获取单元下该管材的管件
                List<PipeTable> temp =
                        pipeTableRepository.findByBatchIdAndUnitNameAndPipeMaterial(unitTable.getBatchId(),
                                unitTable.getUnitName(),taoliao.getPipeMaterial());
                pipeTableList.addAll(temp);
            }
        }
        return  pipeTableList;
    }

    //根据taoliaoId 获取该套料下的管件
    @Override
    public Page<PipeTable> getPipeTableByTaoliaoId( PageParam pageParam,int taoliaoId){
        List<UnitTable> unitTableList =new ArrayList<>();
        PipeTable pipeTable = new PipeTable();
        Taoliao taoliao = taoliaoRepository.findOne(taoliaoId);
        if(taoliao!=null){
            logger.info("查询套料id="+taoliaoId);
            //获取计划批次下所有单元
            unitTableList= unitTableRepository.findByPlanId(taoliao.getPlanId());
            //获取单元下该管材的管件
            pipeTable.setBatchId(taoliao.getBatchId());
            pipeTable.setPipeMaterial(taoliao.getPipeMaterial());
        }
        return  pipeTableService.getPage(pageParam,pipeTable,unitTableList);
    }
    //将套料管件信息封装为CutPipeInfo对象
    @Override
    public List<CutPipeInfo> turnPipeTableToCutPipeInfo(List<PipeTable> pipeTableList,int taoliaoId){
        List<CutPipeInfo> cutPipeInfoList =  new ArrayList<>();

        Taoliao taoliao = taoliaoRepository.findOne(taoliaoId);
        if(taoliao!=null) {
            logger.info("查询套料id=" + taoliaoId);
            BatchTable batchTable = batchTableRepository.findOne(taoliao.getBatchId());
            //下料加工记录
            Workstage workstage = workstageRepository.findByName("下料");

            if(batchTable!=null)
            for(PipeTable pipeTable:pipeTableList){
                CutPipeInfo cutPipeInfo = new CutPipeInfo(pipeTable.getPipeId(),pipeTable.getCutLength(),pipeTable.getPipeCode());
                cutPipeInfo.setBatchName(batchTable.getBatchName());
                cutPipeInfo.setUnitName(pipeTable.getUnitName());

                int status = pipeProcessingService.processingStateOfSatge(pipeTable,workstage.getId());
                if(status==1)cutPipeInfo.setIsCutted(1);
                else cutPipeInfo.setIsCutted(0);
                cutPipeInfoList.add(cutPipeInfo);
            }
        }
        return cutPipeInfoList;
    }
}
