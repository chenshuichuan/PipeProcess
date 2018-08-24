package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.BatchTableRepository;
import llcweb.dao.repository.PipeTableRepository;
import llcweb.dao.repository.UnitTableRepository;
import llcweb.domain.models.BatchTable;
import llcweb.domain.models.PipeTable;
import llcweb.domain.models.UnitTable;
import llcweb.service.ArrangeTableService;
import llcweb.service.PipeTableService;
import llcweb.service.ProcessOrderService;
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
public class PipeTableServiceImpl implements PipeTableService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private PipeTableRepository pipeTableRepository;
    @Autowired
    private BatchTableRepository batchTableRepository;
    @Autowired
    private UnitTableRepository unitTableRepository;
    @Autowired
    private ProcessOrderService processOrderService;

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

    //更新PipeTable 的 当前工序信息
    @Override
    public int updatePipeToNextStage(PipeTable pipeTable, int processState, int processIndex) {
        pipeTable.setProcessSate(processState);
        pipeTable.setProcessIndex(processIndex);
        int nextStage = processOrderService.nextStage(pipeTable.getProcessOrder(),processState);
        pipeTable.setNextStage(nextStage);
        PipeTable temp = pipeTableRepository.save(pipeTable);
        if(temp==null||temp.getPipeId().intValue()!=pipeTable.getPipeId())
            logger.error("更新PipeTable："+pipeTable.getPipeId()+"的 当前工序信息出错！");
        else return temp.getPipeId();
        return 0;
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
    public Page<PipeTable> getPage(PageParam pageParam, Integer batchId) {
        //规格定义
        Specification<PipeTable> specification = new Specification<PipeTable>() {

            /**
             * 构造断言
             * @param root 实体对象引用
             * @param query 规则查询对象
             * @param cb 规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<PipeTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if(batchId>0){ //添加断言

                    Predicate likeUserName = cb.equal(root.get("batchId").as(Integer.class),batchId);
                    predicates.add(likeUserName);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        //分页信息
        Pageable pageable = new PageRequest(pageParam.getCurrentPage()-1,pageParam.getNumPerPage()); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return pipeTableRepository.findAll(specification,pageable);
    }


    /**
     *@Author: Ricardo
     *@Description: 将某批次内的管件
     *@Date: 10:16 2018/8/10
     *@param:
     **/
    @Override
    public void turnUnit(int startPage, String batchName){
        //找到对应批次
        BatchTable temp = batchTableRepository.findByBatchName(batchName);
        Integer batchId = 0;
        if(temp!=null)batchId = temp.getBatchId();
        Page<PipeTable> page =  getPage(new PageParam(1,50), batchId);
        int pageSize = page.getTotalPages();
        for (int i = startPage; i <= pageSize; i++) {
            page =  getPage(new PageParam(i,50), batchId);
            System.out.println("page size ="+pageSize+" currrent page="+i);
            List<PipeTable> pipeTableList = page.getContent();

            for (PipeTable pipeTable: pipeTableList) {
                BatchTable batchTable = batchTableRepository.findOne(pipeTable.getBatchId());
                if (batchTable!=null){
                    UnitTable unitTable =
                            unitTableRepository.findByBatchNameAndUnitName(batchTable.getBatchName(),pipeTable.getUnitName());
                    if(unitTable!=null){
                        System.out.println("pipeid= "+pipeTable.getPipeId()+","
                                +batchTable.getBatchName()+"id= "+unitTable.getBatchId()+"-"
                                +pipeTable.getUnitName()+"-id="+unitTable.getUnitId());

                        pipeTable.setBatchId(unitTable.getBatchId());
                        pipeTable.setShipCode(unitTable.getShipCode());
                        pipeTable.setUnitId(unitTable.getUnitId());
                        pipeTableRepository.save(pipeTable);
                    }else{
                        System.out.println("pipe id="+pipeTable.getPipeId()+" can't find a batch!");
                        logger.error("pipe id="+pipeTable.getPipeId()+" can't find a batch!");
                    }
                }
                else logger.error("管件对应的批次未找到！");
            }
        }

    }
}
