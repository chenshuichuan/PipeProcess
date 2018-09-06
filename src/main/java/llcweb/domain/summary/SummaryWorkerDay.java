package llcweb.domain.summary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *@Author: Ricardo
 *@Description: 工位每日的加工管件总结
 *@Date: 14:56 2018/9/2
 *@param:
 **/
@Entity
@Table(name = "summary_worker_day")
public class SummaryWorkerDay implements Serializable
{
    @Id
    @GeneratedValue
    private Integer id;
    private Integer workerId;
    private Date day;  //日期

    private Integer planning;//其他为当日派工数
    private Integer finished;//当日实际完成数
    public SummaryWorkerDay( ) {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Integer getPlanning() {
        return planning;
    }

    public void setPlanning(Integer planning) {
        this.planning = planning;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }
}
