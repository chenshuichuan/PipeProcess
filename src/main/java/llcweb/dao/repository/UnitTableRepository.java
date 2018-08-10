package llcweb.dao.repository;

import llcweb.domain.models.UnitTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface UnitTableRepository extends JpaRepository<UnitTable,Integer>{
    UnitTable findByBatchNameAndUnitName(String batchName, String unitName);
    List<UnitTable> findByBatchName(String batchName);
    List<UnitTable> findByUnitNameLike(String unitName);
    List<UnitTable> findByBatchNameAndUnitNameLike(String batchName, String unitName);
    List<UnitTable> findByBatchNameAndPlanIdIsNull(String batchName);
}
