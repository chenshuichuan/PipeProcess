package llcweb.service;

import llcweb.domain.entities.DepartmentInfo;
import llcweb.domain.entities.DepartmentTree;
import llcweb.domain.models.Departments;
import llcweb.domain.models.Users;
import llcweb.domain.models.Workers;
import llcweb.tools.PageParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */

public interface DepartmentsService {

    /*
     *添加信息
     */
    void add();
    /*
    *更新信息
    */
    void updateById(int id);
    /*
    *根据id查找
    */
    void findById(int id);
    /*
    *删除
    * */
    void deleteById(int id);
    /*
  * 根据分页参数以及各字段示例查找信息
  * example 为字段可能包含的值
  * */
    Page<Departments> getPage(PageParam pageParam, Departments example);

    //根据当前登录用户角色获取对应的部门树,根据showState决定是否在其后加上工位的加工状态//是否存在未完成的派工记录
    List<DepartmentTree> getDepartmentTree(Users users, boolean showState);
    List<Departments> getDepartments(Users users);
    DepartmentInfo getDepartmentInfo(int id);
}
