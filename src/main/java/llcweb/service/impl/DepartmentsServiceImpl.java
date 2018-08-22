package llcweb.service.impl;

import llcweb.dao.repository.DepartmentsRepository;
import llcweb.dao.repository.ScannerTableRepository;
import llcweb.dao.repository.WorkersRepository;
import llcweb.domain.entities.DepartmentInfo;
import llcweb.domain.entities.DepartmentTree;
import llcweb.domain.entities.WorkplaceInfo;
import llcweb.domain.models.Departments;
import llcweb.domain.models.ScannerTable;
import llcweb.domain.models.Users;
import llcweb.domain.models.Workers;
import llcweb.service.ArrangeTableService;
import llcweb.service.DepartmentsService;
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
public class DepartmentsServiceImpl implements DepartmentsService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private DepartmentsRepository departmentsRepository;
    @Autowired
    private WorkersRepository workersRepository;
    @Autowired
    private ScannerTableRepository scannerTableRepository;

    @Autowired
    private ArrangeTableService arrangeTableService;
    @Autowired
    private UsersService usersService;

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
    public Page<Departments> getPage(PageParam pageParam, Departments example) {
        //规格定义
        Specification<Departments> specification = new Specification<Departments>() {

            @Override
            public Predicate toPredicate(Root<Departments> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if (StringUtils.isNotBlank(example.getName())) { //添加断言
                    Predicate likeName = cb.like(root.get("name").as(String.class), "%" + example.getName() + "%");
                    predicates.add(likeName);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        //分页信息
        Pageable pageable = new PageRequest(pageParam.getCurrentPage() - 1, pageParam.getNumPerPage()); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return departmentsRepository.findAll(specification,pageable);
    }

    /**
     *@Author: Ricardo
     *@Description: 根据用户获取其可以查看修改的部门
     *@Date: 20:55 2018/8/17
     *@param: users：role，0 管理员.1 工段长. 2工人
     **/
    @Override
    public List<Departments> getDepartments(Users users) {
        if (users==null||users.getRole()>=2){
            logger.error("获取部门树失败！");
            return null;
        }
        List<Departments> departmentsList= new ArrayList<>();
        //工段列表
        List<Departments> sectionList =  usersService.getSections(users);
        for(Departments departments: sectionList){
            //工段下的工序列表
            List<Departments> dstage = departmentsRepository.findByUpDepartment(departments.getId());
            departmentsList.add(departments);
            departmentsList.addAll(dstage);

            for (Departments stage: dstage){
                if (stage.getLevel()!=1)logger.error("找到的部门非工序！不合法！请检查");
                else{
                    //工序下的工位列表
                    List<Departments> dWorkPlace = departmentsRepository.findByUpDepartment(stage.getId());
                    departmentsList.addAll(dWorkPlace);
                }
            }
        }
        return departmentsList;
    }
    /**
     *@Author: Ricardo
     *@Description: 根据用户获取其可以派工的的部门树，根据showState决定是否加上工位的加工状态
     *@Date: 20:55 2018/8/17
     *@param: users：role，0 管理员.1 工段长. 2工人
     **/
    @Override
    public List<DepartmentTree> getDepartmentTree(Users users,boolean showState) {
        if (users==null||users.getRole()>=2){
            logger.error("获取部门树失败！");
            return null;
        }

        List<Departments> departmentsList= getDepartments(users);

        //转换为 tree类型
        List<DepartmentTree> departmentTreeList = new ArrayList<>();
        for (Departments departments:departmentsList){
            DepartmentTree departmentTree = new DepartmentTree(departments);
            if(departments.getLevel()==0){
                departmentTree.setOpen(true);
                departmentTree.setIconSkin("pIcon01");
            }
            else if(departments.getLevel()==1){
                departmentTree.setOpen(false);
                departmentTree.setIconSkin("");
            }
            else if(departments.getLevel()==2){
                departmentTree.setOpen(false);
                departmentTree.setIconSkin("");
                //查找某工位是否存在未完成派工
                if(showState){
                    if(arrangeTableService.isWorkpalceVacancy(departments))
                        departmentTree.setName(departments.getName()+"--<空闲>");
                    else departmentTree.setName(departments.getName()+"--<加工中>");
                }
            }
            departmentTreeList.add(departmentTree);
        }
        return departmentTreeList;
    }

    //根据部门id获取某个部门的详细信息
    @Override
    public DepartmentInfo getDepartmentInfo(int id) {
        Departments departments = departmentsRepository.findOne(id);
        if(departments!=null){
            DepartmentInfo departmentInfo = new DepartmentInfo(departments);
            Departments upDe = departmentsRepository.findOne(departments.getUpDepartment());
            if(upDe!=null)departmentInfo.setUpDepartment(upDe.getName());
            else if(departments.getLevel()!=0)logger.error("非工段部门未找到上级部门！");

            //工位的话，查询工位当前绑定的工人
            if(departments.getLevel()==2){
                ScannerTable scannerTable = scannerTableRepository.findByWorkplaceId(departments.getId());
                if(scannerTable!=null){
                    Workers workers = workersRepository.findOne(scannerTable.getWorkerId());
                    if(workers!=null)
                        departmentInfo.setBangdingWorker(workers.getCode()+"-"+workers.getName());
                    departmentInfo.setScanner(scannerTable.getScannerCode());
                    departmentInfo.setIsLock(scannerTable.getIsLock());
                }
            }
            return departmentInfo;
        }
        return null;
    }

    /**
     *@Author: Ricardo
     *@Description: 根据部门工序，获取该工序下所有的工位及工位状态
     *@Date: 20:55 2018/8/17
     *@param: users：role，0 管理员.1 工段长. 2工人
     **/
    @Override
    public List<WorkplaceInfo> getWorkPlaceByStage(int stageId) {

        List<WorkplaceInfo> workplaceInfoList= new ArrayList<>();
        //工位列表
        List<Departments> departmentsList =  departmentsRepository.findByUpDepartment(stageId);
        for(Departments departments: departmentsList){
           WorkplaceInfo workplaceInfo = new WorkplaceInfo(departments);
           workplaceInfo.setVaccancy(arrangeTableService.isWorkpalceVacancy(departments));
           workplaceInfoList.add(workplaceInfo);
        }
        return workplaceInfoList;
    }
}
