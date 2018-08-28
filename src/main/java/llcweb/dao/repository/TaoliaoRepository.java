package llcweb.dao.repository;

import llcweb.domain.models.PipeTable;
import llcweb.domain.models.Taoliao;
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
public interface TaoliaoRepository extends JpaRepository<Taoliao,Integer>
        , JpaSpecificationExecutor<Taoliao> {
    Page<Taoliao> findAll(Specification<Taoliao> spec, Pageable pageable);
    List<Taoliao> findByPlanIdAndBatchId(int planId, int batchId);
    List<Taoliao> findByPlanId(int planId);
    Taoliao findByPlanIdAndPipeMaterial(int planId, String pipeMaterial);
}
