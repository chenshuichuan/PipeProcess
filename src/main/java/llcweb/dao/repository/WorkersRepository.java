package llcweb.dao.repository;

import llcweb.domain.models.ArrangeTable;
import llcweb.domain.models.Workers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface WorkersRepository extends JpaRepository<Workers,Integer>
        , JpaSpecificationExecutor<Workers> {
    Page<Workers> findAll(Specification<Workers> spec, Pageable pageable);
    //工号应该是唯一的，应该对应工人码？就是工人码？
    Workers findByNameAndCode(String name,String code);
    //工号应该是唯一的，应该对应工人码？就是工人码？
    List<Workers> findByNameOrCode(String name, String code);
    Workers findByCode(String code);
}
