package llcweb.dao.repository;

import llcweb.domain.models.PlanTable;
import llcweb.domain.models.Workers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface PlanTableRepository extends JpaRepository<PlanTable,Integer>{

    Page<PlanTable> findAll(Specification<PlanTable> spec, Pageable pageable);

    List<PlanTable> findByBatchName(String batchName);
    //批次和加工点可以决定唯一一条数据，约定
    PlanTable findByBatchNameAndProcessPlace(String batchName,String processPlace);
//    PlanTable findByPlanId(int planId);
}
