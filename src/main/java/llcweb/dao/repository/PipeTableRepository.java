package llcweb.dao.repository;

import llcweb.domain.models.ArrangeTable;
import llcweb.domain.models.PipeTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/8/06
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface PipeTableRepository extends JpaRepository<PipeTable,Integer>
        , JpaSpecificationExecutor<PipeTable> {
    Page<PipeTable> findAll(Specification<PipeTable> spec, Pageable pageable);

    List<PipeTable> findByBatchIdAndUnitName(int batchId, String unitName);
    int countByBatchIdAndUnitName(int batchId, String unitName);
}
