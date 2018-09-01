package llcweb.dao.repository;

import llcweb.domain.models.ArrangeTable;
import llcweb.domain.models.PipeProcessing;
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
public interface PipeProcessingRepository extends JpaRepository<PipeProcessing,Integer>
        , JpaSpecificationExecutor<PipeProcessing> {
    Page<PipeProcessing> findAll(Specification<PipeProcessing> spec, Pageable pageable);
    //考虑到可能管件会被重新派工加工，所以同一根管的同一个工序会有多条记录
    List<PipeProcessing> findByPipeIdAndProcessState(int pipeId, int processState);

}
