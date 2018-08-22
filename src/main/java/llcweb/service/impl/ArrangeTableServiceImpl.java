package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.DepartmentsRepository;
import llcweb.dao.repository.PlanTableRepository;
import llcweb.dao.repository.WorkersRepository;
import llcweb.domain.entities.ArrangeRecord;
import llcweb.domain.models.ArrangeTable;
import llcweb.domain.models.Departments;
import llcweb.domain.models.PlanTable;
import llcweb.domain.models.Workers;
import llcweb.service.ArrangeTableService;
import llcweb.tools.PageParam;
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
    public Page<ArrangeTable> getPage(PageParam pageParam, ArrangeTable example) {
        //规格定义
        Specification<ArrangeTable> specification = new Specification<ArrangeTable>() {

            /**
             * 构造断言
             *
             * @param root  实体对象引用
             * @param query 规则查询对象
             * @param cb    规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<ArrangeTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
        return arrangeTableRepository.findAll(specification,pageable);
    }

    @Override
    public ArrangeRecord getRecord(ArrangeTable arrangeTable) {
        ArrangeRecord arrangeRecord =new ArrangeRecord(arrangeTable);
        PlanTable planTable = planTableRepository.findOne(arrangeTable.getPlanId());
        String plan = "";
        if (planTable!=null)
            plan = planTable.getBatchName()+"--"+ planTable.getBatchDescription()+"--"+planTable.getProcessPlace();
        else {
            plan = "派工记录对应批次未找到！请检查数据库";
            logger.error(plan);
        }
        arrangeRecord.setPlan(plan);

        Workers workers = workersRepository.findOne(arrangeTable.getWorkerId());
        String worker ="";
        if (workers!=null)
            worker = workers.getCode()+"-"+workers.getName();
        else{
            worker = "暂无";
        }
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

    @Override
    public List<ArrangeTable> findArrangeByWorkplace(String section, String stage, String workplace, int isFinished) {

        return arrangeTableRepository.
                findBySectionAndStageAndWorkplaceAndIsFinished(section,stage,workplace,isFinished);
    }

    //判断工位是否空闲
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


}
