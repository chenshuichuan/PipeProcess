package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.PipeProcessingRepository;
import llcweb.domain.models.PipeProcessing;
import llcweb.domain.models.PipeTable;
import llcweb.domain.models.Workers;
import llcweb.service.ArrangeTableService;
import llcweb.service.PipeProcessingService;
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
public class PipeProcessingServiceImpl implements PipeProcessingService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private PipeProcessingRepository pipeProcessingRepository;

    @Transactional
    @Override
    public void add() {
        logger.info("service add");
    }

    @Transactional
    @Override
    public int add(PipeTable pipeTable,int processState,int processIndex,int processPlace) {
        PipeProcessing pipeProcessing = new PipeProcessing();
        pipeProcessing.setPipeId(pipeTable.getPipeId());
        pipeProcessing.setProcessState(processState);
        pipeProcessing.setProcessIndex(processIndex);
        pipeProcessing.setProcessPlace(processPlace);
        pipeProcessing.setIsFinished(0);
        PipeProcessing temp  = pipeProcessingRepository.save(pipeProcessing);
        if(temp==null)logger.error("pipeId="+pipeTable.getPipeId()+"无法生成pipeProcessing记录！请检查");
        else return temp.getId();
        return 0;
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
    public Page<PipeProcessing> getPage(PageParam pageParam, PipeProcessing example) {
        //规格定义
        Specification<PipeProcessing> specification = new Specification<PipeProcessing>() {

            @Override
            public Predicate toPredicate(Root<PipeProcessing> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        //分页信息
        Pageable pageable = new PageRequest(pageParam.getCurrentPage() - 1, pageParam.getNumPerPage()); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return pipeProcessingRepository.findAll(specification,pageable);
    }


    /**
     *@Author: Ricardo
     *@Description: //返回pipe在某工序的加工状态：未开始 -1，加工中0，已完工1
     * 未开始：pipeprocessing中不存在该工序的加工记录
     * 加工中：该工序的加工记录中存在未完成的记录
     * 已完工：该工序的加工记录中所有记录都已完成
     *@Date: 11:24 2018/9/1
     *@param:
     **/
    public int processingStateOfSatge(PipeTable pipeTable,int stageId){

        List<PipeProcessing> pipeProcessingList =
                pipeProcessingRepository.findByPipeIdAndProcessState(pipeTable.getPipeId(),stageId);
        int stageProcessStatus = 1;
        for(PipeProcessing pipeProcessing: pipeProcessingList){
            //存在未完成记录
            if(pipeProcessing.getIsFinished()==0){
                stageProcessStatus=0;
                break;
            }
        }
        //不存在加工记录
        if(pipeProcessingList==null||pipeProcessingList.size()==0)stageProcessStatus=-1;
        return stageProcessStatus;
    }
}
