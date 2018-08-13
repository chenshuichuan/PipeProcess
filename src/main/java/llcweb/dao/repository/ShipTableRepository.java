package llcweb.dao.repository;

import llcweb.domain.models.ShipTable;
import llcweb.domain.models.Workers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface ShipTableRepository extends JpaRepository<ShipTable,String>{
    Page<ShipTable> findAll(Specification<ShipTable> spec, Pageable pageable);
}
