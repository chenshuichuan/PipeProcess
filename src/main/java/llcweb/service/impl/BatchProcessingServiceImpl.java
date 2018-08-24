package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.BatchProcessingRepository;
import llcweb.domain.models.BatchProcessing;
import llcweb.domain.models.Workers;
import llcweb.service.ArrangeTableService;
import llcweb.service.BatchProcessingService;
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
public class BatchProcessingServiceImpl implements BatchProcessingService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private BatchProcessingRepository batchProcessingRepository;

    @Transactional
    @Override
    public void add() {
        logger.info("service add");
    }

    //根据信息新增batchProcessing记录
    @Transactional
    @Override
    public int add(int batchId, String sectionName, int pipeNumber, int unitNumber, int arrangeId) {
        BatchProcessing batchProcessing = new BatchProcessing();
        batchProcessing.setBatchId(batchId);
        batchProcessing.setSectionName(sectionName);
        batchProcessing.setPipeNumber(pipeNumber);
        batchProcessing.setUnitNumber(unitNumber);
        batchProcessing.setUnitProcessingNumber(unitNumber);//初始为所有
        batchProcessing.setUnitFinishedNumber(0);//初始为0
        batchProcessing.setBatchArrangeId(arrangeId);
        BatchProcessing batchProcessing1 = batchProcessingRepository.save(batchProcessing);
        if(batchProcessing1!=null)return batchProcessing1.getId();
        else{
            logger.error("无法生成BatchProcessing记录！");
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
    public Page<BatchProcessing> getPage(PageParam pageParam, BatchProcessing example) {
        //规格定义
        Specification<BatchProcessing> specification = new Specification<BatchProcessing>() {

            /**
             * 构造断言
             *
             * @param root  实体对象引用
             * @param query 规则查询对象
             * @param cb    规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<BatchProcessing> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
//                if (StringUtils.isNotBlank(example.getName())) { //添加断言
//                    Predicate likeName = cb.like(root.get("name").as(String.class), "%" + example.getName() + "%");
//                    predicates.add(likeName);
//                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        //分页信息
        Pageable pageable = new PageRequest(pageParam.getCurrentPage() - 1, pageParam.getNumPerPage()); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return batchProcessingRepository.findAll(specification,pageable);
    }
}
