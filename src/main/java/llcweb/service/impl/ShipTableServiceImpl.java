package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.ShipTableRepository;
import llcweb.domain.entities.ProcessInfo;
import llcweb.domain.entities.ShipProcessInfo;
import llcweb.domain.models.ShipTable;
import llcweb.domain.models.Workers;
import llcweb.service.ArrangeTableService;
import llcweb.service.BatchTableService;
import llcweb.service.ShipTableService;
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

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */
@Service
public class ShipTableServiceImpl implements ShipTableService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private ShipTableRepository shipTableRepository;
    @Autowired
    private BatchTableService batchTableService;
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
    public Page<ShipTable> getPage(PageParam pageParam, ShipTable example) {
        //规格定义
        Specification<ShipTable> specification = new Specification<ShipTable>() {

            @Override
            public Predicate toPredicate(Root<ShipTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
        return shipTableRepository.findAll(specification,pageable);
    }

    //根据state 查询船
    //state：1 完工状态 ，0未完工状态 -1所有
    @Override
    public List<ShipTable> getAllShipNameByState(int state) {
        if(state==-1)return shipTableRepository.findAll();
        else if(state==1) return shipTableRepository.findFinishedShip();
        else  return shipTableRepository.findUnfinishedShip();
    }
    @Override
    public List<ShipProcessInfo> getShipProcessInfo(List<ShipTable> shipTableList){
        List<ShipProcessInfo> shipProcessInfoList = new ArrayList<>();
        //跟据船列表获取加工信息
        for (ShipTable shipTable: shipTableList){
            //继承船信息，加工批次信息
            ShipProcessInfo shipProcessInfo = new ShipProcessInfo(shipTable);
            //获取所有批次 统计单元信息
            ProcessInfo unitProcessInfo = batchTableService.calUnitProcessOfShip(shipTable.getShipCode());
            shipProcessInfo.setUnitNumber(unitProcessInfo.getNumber());
            shipProcessInfo.setUnitFinished(unitProcessInfo.getFinished());
            shipProcessInfo.setUnitFinishedRate(unitProcessInfo.getFinishedRate());

            //获取所有单元 统计管件信息
            ProcessInfo pipeProcessInfo = unitTableService.calPipeProcessOfShip(shipTable.getShipCode());
            shipProcessInfo.setPipeNumber(unitProcessInfo.getNumber());
            shipProcessInfo.setPipeFinished(unitProcessInfo.getFinished());
            shipProcessInfo.setPipeFinishedRate(unitProcessInfo.getFinishedRate());

            shipProcessInfoList.add(shipProcessInfo);
        }
        return  shipProcessInfoList;
    }

}
