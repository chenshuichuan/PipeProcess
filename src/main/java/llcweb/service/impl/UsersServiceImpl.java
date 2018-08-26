package llcweb.service.impl;

import llcweb.dao.repository.DepartmentsRepository;
import llcweb.dao.repository.UsersRepository;
import llcweb.dao.repository.WorkersRepository;
import llcweb.domain.models.Departments;
import llcweb.domain.models.Users;
import llcweb.domain.models.Workers;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class UsersServiceImpl implements UsersService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private DepartmentsRepository departmentsRepository;
    @Autowired
    private WorkersRepository workersRepository;

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

    @Override
    public Page<Users> getPage(PageParam pageParam, Users users) {
        //规格定义
        Specification<Users> specification = new Specification<Users>() {

            @Override
            public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if(StringUtils.isNotBlank(users.getUsername())){ //添加断言
                    Predicate likeUserName = cb.like(root.get("username").as(String.class),users.getUsername()+"%");
                    predicates.add(likeUserName);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        //分页信息
        Pageable pageable = new PageRequest(pageParam.getCurrentPage()-1,pageParam.getNumPerPage()); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return this.usersRepository.findAll(specification,pageable);
    }

    @Override
    public Users getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        logger.info("username = "+userDetails.getUsername());
        //logger.info("username = "+userDetails.getPassword());
        Users users = usersRepository.findByUsernameAndPassword(userDetails.getUsername(),userDetails.getPassword());
        if(users!=null)logger.info("users id = "+users.getId()+",worker's id="+users.getWorkerId());
        return users;
    }

    /**
     *@Author: Ricardo
     *@Description: 根据登录用户获取其可以查看修改的工段部门列表
     *@Date: 20:55 2018/8/17
     *@param: users：role，0 管理员.1 工段长. 2工人
     *
     * 此函数可以试着优化为，用户登录直接就获取，信息，然后存到Redis缓存中
     **/
    @Override
    public List<Departments> getSections(Users users) {
        if (users==null||users.getRole()>=2){
            logger.error("获取部门树失败！");
            return null;
        }

        List<Departments> departmentsList= new ArrayList<>();
        //管理员，查看全部工段
        if(users.getRole()==0){
            departmentsList = departmentsRepository.findByLevel(0);
        }
        //工段长，根据workers表管理的部门进行查找
        else if (users.getRole()==1){
            Workers workers =workersRepository.findOne(users.getWorkerId());
            if (workers==null){
                logger.error("用户："+users.getUsername()+"未找到对应工段长信息！请检查数据！");
                return null;
            }
            //管理的工段id列表
            String [] dList = workers.getDepartments().split(",");
            logger.info("管理部门="+dList.toString());
            for (String str:dList){
                int sectionId = Integer.parseInt(str);
                //某工段
                Departments dSection = departmentsRepository.findOne(sectionId);
                if (dSection==null||dSection.getLevel()!=0){
                    logger.error("找到用户管理的部门不是工段！不合法！请检查");
                    return  null;
                }
                departmentsList.add(dSection);
            }
        }
        return departmentsList;
    }

    ///获取users管理下的第一个非下料工序，用于单元派工
    @Override
    public Departments getOneStage(Users users) {
        if (users==null||users.getRole()>=2){
            logger.error("获取部门树失败！");
            return null;
        }
        Departments dSection;
        List<Departments> departmentsList= new ArrayList<>();
        //管理员，查看全部工段
        if(users.getRole()==0){
            departmentsList = departmentsRepository.findByLevel(0);
             dSection = departmentsList.get(0);
            List<Departments>stageList1 = departmentsRepository.findByUpDepartment(dSection.getId());
            return stageList1.get(stageList1.size()-1);
        }
        //工段长，根据workers表管理的部门进行查找
        else if (users.getRole()==1){
            Workers workers =workersRepository.findOne(users.getWorkerId());
            if (workers==null){
                logger.error("用户："+users.getUsername()+"未找到对应工段长信息！请检查数据！");
                return null;
            }
            //管理的工段id列表
            String [] dList = workers.getDepartments().split(",");
            logger.info("管理部门="+dList.toString());
            if(dList.length>0){
                String str=dList[0];//取第一个部门
                int sectionId = Integer.parseInt(str);
                //某工段
                 dSection = departmentsRepository.findOne(sectionId);
                if (dSection==null||dSection.getLevel()!=0){
                    logger.error("找到用户管理的部门不是工段！不合法！请检查");
                    return  null;
                }
                else{
                    List<Departments>stageList1 = departmentsRepository.findByUpDepartment(dSection.getId());
                    return stageList1.get(stageList1.size()-1);
                }
            }
        }
        return null;
    }
}
