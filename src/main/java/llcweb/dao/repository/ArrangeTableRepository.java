package llcweb.dao.repository;

import llcweb.domain.models.ArrangeTable;
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
public interface ArrangeTableRepository extends JpaRepository<ArrangeTable,Integer>,
        JpaSpecificationExecutor<ArrangeTable> {
    Page<ArrangeTable> findAll(Specification<ArrangeTable> spec, Pageable pageable);

    //查找某工位的派工情况
    List<ArrangeTable> findBySectionAndStageAndWorkplaceAndIsFinished(String section,String stage,String workpalce,int isFinished);
    //查找某工序下的派工情况
    List<ArrangeTable> findBySectionAndArrangeTypeAndIsFinished(String section,int arrangeType,int isFinished);
    //查找某工位的派工情况
    List<ArrangeTable> findByWorkplaceAndArrangeTypeAndIsFinished(String workpalce,int arrangeType,int isFinished);

}
