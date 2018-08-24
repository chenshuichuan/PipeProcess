package llcweb.domain.models;

import llcweb.tools.DateUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="arrange_table")
public class ArrangeTable {
    @Id
    @GeneratedValue
    private Integer arrangeId;

    private Integer arrangeType;//1.批次派工，2单元派工。3单管派工

    private String name;//1.批次名 2.单元名 3.管号

    private Integer planId;

    private String section;

    private String stage;

    private String workplace;

    private Integer arrangerId;

    private Integer workerId;

    private Date updateTime;

    private Integer isFinished;

    private Date finishedTime;
    public ArrangeTable() {
        this.isFinished=0;
        this.updateTime = new Date();
    }
    public ArrangeTable(Integer arrangeType, String name, Integer planId, String section,
                        String stage, String workplace) {
        this.arrangeType = arrangeType;
        this.name = name;
        this.planId = planId;
        this.section = section;
        this.stage = stage;
        this.workplace = workplace;
    }
    public ArrangeTable(Integer arrangeId, Integer arrangeType, String name, Integer planId, String section,
                        String stage, String workplace, Integer arrangerId, Integer workerId, Date updateTime) {
        this.arrangeId = arrangeId;
        this.arrangeType = arrangeType;
        this.name = name;
        this.planId = planId;
        this.section = section;
        this.stage = stage;
        this.workplace = workplace;
        this.arrangerId = arrangerId;
        this.workerId = workerId;
        this.updateTime = updateTime;
    }

    public Integer getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(Integer arrangeId) {
        this.arrangeId = arrangeId;
    }

    public Integer getArrangeType() {
        return arrangeType;
    }

    public void setArrangeType(Integer arrangeType) {
        this.arrangeType = arrangeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section == null ? null : section.trim();
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage == null ? null : stage.trim();
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace == null ? null : workplace.trim();
    }

    public Integer getArrangerId() {
        return arrangerId;
    }

    public void setArrangerId(Integer arrangerId) {
        this.arrangerId = arrangerId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }
}