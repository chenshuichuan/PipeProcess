package llcweb.dao.repository;

import llcweb.domain.models.Departments;
import llcweb.domain.models.Workers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface DepartmentsRepository extends JpaRepository<Departments,Integer>{
    Page<Departments> findAll(Specification<Departments> spec, Pageable pageable);
    Departments findByName(String name);
    List<Departments> findByUpDepartment(int upDepartment);
    List<Departments> findByLevel(int level);
}
