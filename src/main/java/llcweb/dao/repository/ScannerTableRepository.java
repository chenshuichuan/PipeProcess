package llcweb.dao.repository;

import llcweb.domain.models.ArrangeTable;
import llcweb.domain.models.ScannerTable;
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
public interface ScannerTableRepository extends JpaRepository<ScannerTable,Integer>
        , JpaSpecificationExecutor<ScannerTable> {
    Page<ScannerTable> findAll(Specification<ScannerTable> spec, Pageable pageable);
    //根据绑定的工位查找扫码枪
    ScannerTable findByWorkplaceId(int workplaceId);
}
