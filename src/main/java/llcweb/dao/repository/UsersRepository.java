package llcweb.dao.repository;

import llcweb.domain.models.ArrangeTable;
import llcweb.domain.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface UsersRepository extends JpaRepository<Users,Integer>
        , JpaSpecificationExecutor<Users> {
    Page<Users> findAll(Specification<Users> spec, Pageable pageable);
    Users findByUsernameAndPassword(String userName,String password);
    Users findByUsername(String userName);
}
