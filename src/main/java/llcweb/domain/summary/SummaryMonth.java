package llcweb.domain.summary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *@Author: Ricardo
 *@Description: 部门的每月加工情况总结
 *@Date: 14:56 2018/9/2
 *@param:
 **/
@Entity
@Table(name = "summary_month")
public class SummaryMonth implements Serializable
{
    @Id
    @GeneratedValue
    private Integer id;
    private Integer departmentId;
    private Date month;  //月份
    private String type;  //月总结类型，计划，批次，单元，管件
    private Integer planning;//对于计划，为当月计划完成数，其他为当月派工数
    private Integer finished;//当月实际完成数
    public SummaryMonth( ) {

    }

    public SummaryMonth(Integer departmentId, Date month, String type) {
        this.departmentId = departmentId;
        this.month = month;
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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
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
