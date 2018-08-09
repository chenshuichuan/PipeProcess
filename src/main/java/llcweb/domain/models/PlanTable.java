package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="plan_table")
public class PlanTable {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer planId;

    private String planName;

    private Integer serialNumber;

    private String shipName;

    private String batchName;

    private String batchDescription;

    private String stocks;

    private String sections;

    private String processPlace;

    private Integer number;

    private Integer lightBodyPip;

    private Date planStart;

    private Date planEnd;

    private Date actualStart;

    private Date acturalEnd;

    private Date sentPicTime;

    private String remark;

    private Date upldateTime;

    private Integer modifyRole;

    private String extend;

    private Date planCutFinish;

    private Integer oneBcutNum;

    private Integer oneBendCut;

    private Integer oneVerCut;

    private Integer oneBigCut;

    private Integer twoSpeBendCut;

    private Integer twoSpeVerCut;

    private String cutRemark;

    private Integer isCutted;

    public PlanTable() {
    }
    public PlanTable(Integer planId, String planName, Integer serialNumber,
                     String shipName, String batchName, String batchDescription,
                     String stocks, String sections, String processPlace,
                     Integer number, Integer lightBodyPip) {
        this.planId = planId;
        this.planName = planName;
        this.serialNumber = serialNumber;
        this.shipName = shipName;
        this.batchName = batchName;
        this.batchDescription = batchDescription;
        this.stocks = stocks;
        this.sections = sections;
        this.processPlace = processPlace;
        this.number = number;
        this.lightBodyPip = lightBodyPip;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName == null ? null : shipName.trim();
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName == null ? null : batchName.trim();
    }

    public String getBatchDescription() {
        return batchDescription;
    }

    public void setBatchDescription(String batchDescription) {
        this.batchDescription = batchDescription == null ? null : batchDescription.trim();
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks == null ? null : stocks.trim();
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections == null ? null : sections.trim();
    }

    public String getProcessPlace() {
        return processPlace;
    }

    public void setProcessPlace(String processPlace) {
        this.processPlace = processPlace == null ? null : processPlace.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getLightBodyPip() {
        return lightBodyPip;
    }

    public void setLightBodyPip(Integer lightBodyPip) {
        this.lightBodyPip = lightBodyPip;
    }

    public Date getPlanStart() {
        return planStart;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;
    }

    public Date getPlanEnd() {
        return planEnd;
    }

    public void setPlanEnd(Date planEnd) {
        this.planEnd = planEnd;
    }

    public Date getActualStart() {
        return actualStart;
    }

    public void setActualStart(Date actualStart) {
        this.actualStart = actualStart;
    }

    public Date getActuralEnd() {
        return acturalEnd;
    }

    public void setActuralEnd(Date acturalEnd) {
        this.acturalEnd = acturalEnd;
    }

    public Date getSentPicTime() {
        return sentPicTime;
    }

    public void setSentPicTime(Date sentPicTime) {
        this.sentPicTime = sentPicTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpldateTime() {
        return upldateTime;
    }

    public void setUpldateTime(Date upldateTime) {
        this.upldateTime = upldateTime;
    }

    public Integer getModifyRole() {
        return modifyRole;
    }

    public void setModifyRole(Integer modifyRole) {
        this.modifyRole = modifyRole;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend == null ? null : extend.trim();
    }

    public Date getPlanCutFinish() {
        return planCutFinish;
    }

    public void setPlanCutFinish(Date planCutFinish) {
        this.planCutFinish = planCutFinish;
    }

    public Integer getOneBcutNum() {
        return oneBcutNum;
    }

    public void setOneBcutNum(Integer oneBcutNum) {
        this.oneBcutNum = oneBcutNum;
    }

    public Integer getOneBendCut() {
        return oneBendCut;
    }

    public void setOneBendCut(Integer oneBendCut) {
        this.oneBendCut = oneBendCut;
    }

    public Integer getOneVerCut() {
        return oneVerCut;
    }

    public void setOneVerCut(Integer oneVerCut) {
        this.oneVerCut = oneVerCut;
    }

    public Integer getOneBigCut() {
        return oneBigCut;
    }

    public void setOneBigCut(Integer oneBigCut) {
        this.oneBigCut = oneBigCut;
    }

    public Integer getTwoSpeBendCut() {
        return twoSpeBendCut;
    }

    public void setTwoSpeBendCut(Integer twoSpeBendCut) {
        this.twoSpeBendCut = twoSpeBendCut;
    }

    public Integer getTwoSpeVerCut() {
        return twoSpeVerCut;
    }

    public void setTwoSpeVerCut(Integer twoSpeVerCut) {
        this.twoSpeVerCut = twoSpeVerCut;
    }

    public String getCutRemark() {
        return cutRemark;
    }

    public void setCutRemark(String cutRemark) {
        this.cutRemark = cutRemark == null ? null : cutRemark.trim();
    }

    public Integer getIsCutted() {
        return isCutted;
    }

    public void setIsCutted(Integer isCutted) {
        this.isCutted = isCutted;
    }
}