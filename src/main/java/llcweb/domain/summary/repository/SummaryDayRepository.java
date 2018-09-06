package llcweb.domain.summary.repository;

import llcweb.domain.models.Users;
import llcweb.domain.summary.SummaryDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface SummaryDayRepository extends JpaRepository<SummaryDay,Integer> {
    //同一部门，类型，日期只有一条记录！
    SummaryDay findByDepartmentIdAndDayAndType(int deprtId, Date day,String type);

    //同一部门，类型，日期只有一条记录！
    List<SummaryDay> findByDayAndType(Date day, String type);
}
