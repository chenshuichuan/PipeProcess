package llcweb.dao.repository;

import llcweb.domain.models.ArrangeTable;
import llcweb.domain.models.FactoryDistribution;
import llcweb.domain.models.Workers;
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
public interface FactoryDistributionRepository extends JpaRepository<FactoryDistribution,Integer>
        , JpaSpecificationExecutor<FactoryDistribution> {
    Page<FactoryDistribution> findAll(Specification<FactoryDistribution> spec, Pageable pageable);
}
