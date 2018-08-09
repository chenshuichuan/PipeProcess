package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="departments")
public class Departments {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Integer lever;

    private Integer upDepartment;

    private String description;

    private Integer stageId;

    private Date updatatTime;

    public Departments() {

    }
    public Departments(String name, Integer lever,
                       Integer upDepartment, String description, Integer stageId) {
        this.name = name;
        this.lever = lever;
        this.upDepartment = upDepartment;
        this.description = description;
        this.stageId = stageId;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLever() {
        return lever;
    }

    public void setLever(Integer lever) {
        this.lever = lever;
    }

    public Integer getUpDepartment() {
        return upDepartment;
    }

    public void setUpDepartment(Integer upDepartment) {
        this.upDepartment = upDepartment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public Date getUpdatatTime() {
        return updatatTime;
    }

    public void setUpdatatTime(Date updatatTime) {
        this.updatatTime = updatatTime;
    }
}