package llcweb.domain.summary;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *@Author: Ricardo
 *@Description: 部门的每日加工情况总结
 *@Date: 14:56 2018/9/2
 *@param:
 **/
@Entity
@Table(name = "summary_day")
public class SummaryDay implements Serializable
{
    @Id
    @GeneratedValue
    private Integer id;
    private Integer departmentId;//部门id，工段、工序、工位
    private Date day;
    private String type;     //日总结类型，批次，单元，管件
    private Integer planning;//计划量，对于批次，单元，管件等类型，即当日派工数量
    private Integer finished;//当日完成数量 对应类型的完成日期在当日的数量
    public SummaryDay( ) {

    }

    public SummaryDay(Integer departmentId, Date day, String type) {
        this.departmentId = departmentId;
        this.day = day;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
