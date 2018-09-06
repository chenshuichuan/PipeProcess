package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.BatchTableRepository;
import llcweb.domain.entities.BatchProcessInfo;
import llcweb.domain.entities.ProcessInfo;
import llcweb.domain.models.BatchTable;
import llcweb.domain.models.ProcessOrder;
import llcweb.domain.models.ShipTable;
import llcweb.domain.models.Workers;
import llcweb.service.ArrangeTableService;
import llcweb.service.BatchTableService;
import llcweb.service.UnitTableService;
import llcweb.tools.CalculateUtil;
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
public class BatchTableServiceImpl implements BatchTableService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private BatchTableRepository batchTableRepository;
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
    /*
      * 根据分页参数以及各字段示例查找信息
      * example 为字段可能包含的值
      * 分页应从1开始，而非0
      * */
    @Override
    public Page<BatchTable> getPage(PageParam pageParam, BatchTable example) {
        //规格定义
        Specification<BatchTable> specification = new Specification<BatchTable>() {

            @Override
            public Predicate toPredicate(Root<BatchTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
        return batchTableRepository.findAll(specification,pageable);
    }
    //统计某船 的单元加工情况
    @Override
    public ProcessInfo calUnitProcessOfShip(String shipCode) {
        List<BatchTable> batchTableList = batchTableRepository.findByShipCode(shipCode);
        ProcessInfo processInfo = new ProcessInfo();
        int finished = 0;
        int number = 0;
        for (BatchTable batchTable:batchTableList){
            finished+=batchTable.getUnitFinishedNumber();
            number +=batchTable.getUnitNumber();
        }
        double temp=0;
        if(number!=0)temp = (double)finished/(double)number;
        processInfo.setFinishedRate(CalculateUtil.DecimalDouble(temp,4));

        processInfo.setFinished(finished);
        processInfo.setNumber(number);
        return processInfo;
    }

    /**
     *@Author: Ricardo
     *@Description: 根据船名和完工状态获取批次信息
     *@Date: 23:21 2018/9/6
     *@param: isfinished -1 所有批次， 0未完工批次 1完工批次
     **/
    @Override
    public List<BatchProcessInfo> getBatchProcessInfo(ShipTable shipTable,int isfinished) {
        List<BatchTable> batchTableList = getBatchTableByShipCodeAndSate(shipTable.getShipCode(),isfinished);
        List<BatchProcessInfo> batchProcessInfoList =new ArrayList<>();
        //封装BatchTable信息未BatchProcessInfo信息
        for (BatchTable batchTable:batchTableList){
            BatchProcessInfo batchProcessInfo = new BatchProcessInfo(batchTable);
            batchProcessInfo.setShipName(shipTable.getShipName());
            //计算该批次下管件加工情况
            ProcessInfo processInfo = unitTableService.calPipeProcessOfBatch(batchTable.getBatchName());
            batchProcessInfo.setPipeNumber(processInfo.getNumber());
            batchProcessInfo.setPipeFinished(processInfo.getFinished());
            batchProcessInfo.setPipeFinishedRate(processInfo.getFinishedRate());

            //计算该批次下料状态


            batchProcessInfoList.add(batchProcessInfo);
        }

        return batchProcessInfoList;
    }
    private List<BatchTable> getBatchTableByShipCodeAndSate(String shipCode,int isFinished){
        if(isFinished==0)return batchTableRepository.findByShipCodeAndFinishedTimeIsNull(shipCode);
        else if(isFinished==1)return batchTableRepository.findByShipCodeAndFinishedTimeIsNotNull(shipCode);
        else return batchTableRepository.findByShipCode(shipCode);
    }
}
