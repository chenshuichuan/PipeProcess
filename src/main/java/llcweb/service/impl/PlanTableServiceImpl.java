package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.PlanTableRepository;
import llcweb.dao.repository.WorkstageRepository;
import llcweb.domain.models.*;
import llcweb.service.ArrangeTableService;
import llcweb.service.PlanTableService;
import llcweb.service.UnitTableService;
import llcweb.service.UsersService;
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
public class PlanTableServiceImpl implements PlanTableService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private PlanTableRepository planTableRepository;
    @Autowired
    private UnitTableService unitTableService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private WorkstageRepository workstageRepository;

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
    public Page<PlanTable> getPage(PageParam pageParam, PlanTable example) {
        //规格定义
        Specification<PlanTable> specification = new Specification<PlanTable>() {

            @Override
            public Predicate toPredicate(Root<PlanTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if(StringUtils.isNotBlank(example.getProcessPlace())){ //添加断言
                    Predicate likeName = cb.like(root.get("processPlace").as(String.class),"%"+example.getProcessPlace()+"%");
                    predicates.add(likeName);
                }
                else{
                    CriteriaBuilder.In<Object> in = cb.in(root.get("processPlace"));
                    List<Departments> sectionList =  usersService.getSections(usersService.getCurrentUser());
                    for (Departments section: sectionList){
                        in.value(section.getName());
                    }
                    predicates.add(in);
                }
                if(StringUtils.isNotBlank(example.getBatchName())){ //添加断言
                    Predicate likeName = cb.like(root.get("batchName").as(String.class),"%"+example.getBatchName()+"%");
                    predicates.add(likeName);
                }
                if(StringUtils.isNotBlank(example.getShipName())){ //添加断言
                    Predicate likeName = cb.like(root.get("shipName").as(String.class),"%"+example.getShipName()+"%");
                    predicates.add(likeName);
                }
                if(example.getIsCutted()!=null){ //添加断言
                    Predicate likeName = cb.equal(root.get("isCutted").as(Integer.class),example.getIsCutted());
                    predicates.add(likeName);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        //分页信息
        Pageable pageable = new PageRequest(pageParam.getCurrentPage() - 1, pageParam.getNumPerPage()); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return planTableRepository.findAll(specification,pageable);
    }

    @Override
    public boolean isPlanFinished(PlanTable planTable) {
        if(planTable!=null&&planTable.getActuralEnd()!=null)
            return true;
        return false;
    }

    //根据解析规则，计算计划下单元，再到管件的加工工序
    @Override
    public void calUnitsOfPlanProcessOrder(PlanTable planTable) {
        //
        Workstage underStart = workstageRepository.findByName("未开始");//未开始
        Workstage cut = workstageRepository.findByName("下料");
        Workstage bend = workstageRepository.findByName("弯管");
        Workstage proofread = workstageRepository.findByName("校管");
        Workstage weld = workstageRepository.findByName("焊接");
        Workstage polish = workstageRepository.findByName("打磨");
        Workstage surface = workstageRepository.findByName("表处");
        Workstage finished = workstageRepository.findByName("已完成");

        List<UnitTable> unitTableList = unitTableService.findByPlanId(planTable.getId());
        for (UnitTable unitTable: unitTableList){
            unitTableService.calUnitProcessOrder(unitTable,underStart,cut,bend,
                    proofread,weld,polish,surface,finished,true);
        }
    }
}
