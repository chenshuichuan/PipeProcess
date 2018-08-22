package llcweb.dao.repository;

import llcweb.domain.models.ArrangeTable;
import llcweb.domain.models.BatchTable;
import llcweb.domain.models.PipeTable;
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
public interface BatchTableRepository extends JpaRepository<BatchTable,Integer>
        , JpaSpecificationExecutor<BatchTable> {
    Page<BatchTable> findAll(Specification<BatchTable> spec, Pageable pageable);
    BatchTable findByBatchName(String batchName);
    BatchTable findByShipCodeAndBatchName(String shipCode, String batchName);
}
