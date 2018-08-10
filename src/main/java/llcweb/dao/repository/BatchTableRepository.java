package llcweb.dao.repository;

import llcweb.domain.models.BatchTable;
import llcweb.domain.models.PipeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface BatchTableRepository extends JpaRepository<BatchTable,Integer>{
    BatchTable findByBatchName(String batchName);
    BatchTable findByShipCodeAndBatchName(String shipCode, String batchName);
}
