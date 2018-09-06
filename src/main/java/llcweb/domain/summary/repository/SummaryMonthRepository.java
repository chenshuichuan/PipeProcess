package llcweb.domain.summary.repository;

import llcweb.domain.summary.SummaryDay;
import llcweb.domain.summary.SummaryMonth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface SummaryMonthRepository extends JpaRepository<SummaryMonth,Integer> {
    //同一部门，类型，月份只有一条记录！
    SummaryMonth findByDepartmentIdAndMonthAndType(int deprtId, Date month, String type);
}
