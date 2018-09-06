package llcweb.domain.summary.repository;

import llcweb.domain.summary.SummaryDay;
import llcweb.domain.summary.SummaryWorkerDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface SummaryWorkerDayRepository extends JpaRepository<SummaryWorkerDay,Integer> {
    //同工人，日期只有一条记录！
    SummaryWorkerDay findByWorkerIdAndDay(int deprtId, Date day);
}
