package llcweb.dao.repository;

import llcweb.domain.models.BatchProcessing;
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
public interface BatchProcessingRepository extends JpaRepository<BatchProcessing,Integer>{
    Page<BatchProcessing> findAll(Specification<BatchProcessing> spec, Pageable pageable);
}
