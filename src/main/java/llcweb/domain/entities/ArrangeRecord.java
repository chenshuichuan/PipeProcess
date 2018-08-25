package llcweb.domain.entities;

import llcweb.domain.models.ArrangeTable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *@Author: Ricardo
 *@Description: 对ArrangeTable 类的封装
 *@Date: 11:14 2018/8/14
 *@param: 
 **/

public class ArrangeRecord {

    private Integer arrangeId;
    private Integer arrangeType;//1.批次派工，2单元派工。3单管派工
    private String name;//1.批次名 2.单元名 3.管号

    private String plan;

    private String section;
    private String stage;
    private String workplace;

    private String arranger;
    private String worker;

    private Date updateTime;
    private Integer isFinished;
    private Date finishedTime;
    public ArrangeRecord() {

    }
    public ArrangeRecord(ArrangeTable arrangeTable) {
        this.arrangeId = arrangeTable.getArrangeId();
        this.arrangeType = arrangeTable.getArrangeType();
        this.name = arrangeTable.getName();
        this.section = arrangeTable.getSection();
        this.stage = arrangeTable.getStage();
        this.workplace =arrangeTable.getWorkplace();
        this.updateTime = arrangeTable.getUpdateTime();
        this.isFinished = arrangeTable.getIsFinished();
        this.finishedTime = arrangeTable.getFinishedTime();
    }
    public ArrangeRecord(Integer arrangeType, String name, String plan, String section,
                         String stage, String workplace) {
        this.arrangeType = arrangeType;
        this.name = name;
        this.plan = plan;
        this.section = section;
        this.stage = stage;
        this.workplace = workplace;
    }
    public ArrangeRecord(Integer arrangeId, Integer arrangeType, String name, String plan, String section,
                         String stage, String workplace, String arranger, String worker, Date updateTime) {
        this.arrangeId = arrangeId;
        this.arrangeType = arrangeType;
        this.name = name;
        this.plan = plan;
        this.section = section;
        this.stage = stage;
        this.workplace = workplace;
        this.arranger = arranger;
        this.worker = worker;
        this.updateTime = updateTime;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getArranger() {
        return arranger;
    }

    public void setArranger(String arranger) {
        this.arranger = arranger;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
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