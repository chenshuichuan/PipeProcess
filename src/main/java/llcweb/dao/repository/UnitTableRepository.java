package llcweb.dao.repository;

import llcweb.domain.entities.Units;
import llcweb.domain.models.UnitTable;
import llcweb.domain.models.Workers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface UnitTableRepository extends JpaRepository<UnitTable,Integer>{
    Page<UnitTable> findAll(Specification<UnitTable> spec, Pageable pageable);

    UnitTable findByBatchNameAndUnitName(String batchName, String unitName);
    List<UnitTable> findByBatchName(String batchName);
    List<UnitTable> findByUnitNameLike(String unitName);
    List<UnitTable> findByBatchNameAndUnitNameLike(String batchName, String unitName);
    List<UnitTable> findByBatchNameAndPlanIdIsNull(String batchName);
    List<UnitTable> findByPlanId(int planId);

//    @Query( nativeQuery = true,
//            value = "select unit_id,unit_name,batch_name,description,pipe_shape,pipe_number,process_order from unit_table where plan_id = ?1")
//    List<Object> findByPlanId(int planId);

//    @Query( nativeQuery = true,
//            value = "select unitId,unitName,batchName,description,pipeShape,pipeNumber,processOrder from UnitTable where planId = ?1")
//    List<Units> findByPlanId(int planId);

//    private Integer unitId;
//    private String unitName;
//    private String batchName;
//    private String description;
//    private String pipeShape;
//    private Integer pipeNumber;
//    private Integer processOrder;
//    //查找记录录入次数少于2次的记录
//    @Query("from Score s where s.editTimes<:edit_times")
//    List<Score> findScoreLessThanEditTimes(@Param("edit_times")int edit_times);
//    //transaction要加在modify后面！
//    @Modifying
//    @Transactional
//    @Query("delete from Score where model = ?1")
//    void deleteByModel(String model);
}
