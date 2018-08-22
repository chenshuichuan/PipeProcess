package llcweb.dao.repository;

import llcweb.domain.models.PlanTable;
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
public interface PlanTableRepository extends JpaRepository<PlanTable,Integer>,
        JpaSpecificationExecutor<PlanTable> {

    Page<PlanTable> findAll(Specification<PlanTable> spec, Pageable pageable);

    List<PlanTable> findByBatchName(String batchName);
    //批次和加工点可以决定唯一一条数据，约定
    PlanTable findByBatchNameAndProcessPlace(String batchName,String processPlace);

    //根据加工工段查找未完工计划，约定未完工的计划的实际完工时间为null
    List<PlanTable> findByProcessPlaceAndActuralEndIsNull(String processPlace);
}
