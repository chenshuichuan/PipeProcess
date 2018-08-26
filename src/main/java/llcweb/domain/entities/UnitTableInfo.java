package llcweb.domain.entities;

import llcweb.domain.models.UnitTable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


public class UnitTableInfo {

    private Integer unitId;
    private String unitName;
    private String shipCode;
    private Integer batchId;
    private String batchName;
    private String description;

    private Integer planId;
    private String pipeShape;
    private int pipeNumber;

    private String section;
    private String processOrder;
    private String processState;//当前工序
    private String nextStage;//下一工序

    private int pipeProcessingNumber;//次字段可用于1.当前工序加工中管件数量 2.整体加工中管件数量
    private int pipeFinishedNumber;//1. 当前工序加工完成管件 2.整体完成管件数量
    private Date finishedTime;

    public UnitTableInfo( ) {

    }
    public UnitTableInfo(UnitTable unitTable) {
        this.unitId = unitTable.getUnitId();
        this.unitName = unitTable.getUnitName();
        this.shipCode = unitTable.getShipCode();
        this.batchId = unitTable.getBatchId();
        this.batchName = unitTable.getBatchName();
        this.description= unitTable.getDescription();
        this.planId = unitTable.getPlanId();
        this.pipeShape = unitTable.getPipeShape();
        this.pipeNumber = unitTable.getPipeNumber();
        this.pipeProcessingNumber = unitTable.getPipeProcessingNumber();
        this.pipeFinishedNumber= unitTable.getPipeFinishedNumber();
        this.finishedTime = unitTable.getFinishedTime();

    }
    public UnitTableInfo(String unitName, String shipCode, Integer batchId, String description) {
        this.unitName = unitName;
        this.shipCode = shipCode;
        this.batchId = batchId;
        this.description = description;
    }
    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode == null ? null : shipCode.trim();
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId  ;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPipeShape() {
        return pipeShape;
    }

    public void setPipeShape(String pipeShape) {
        this.pipeShape = pipeShape == null ? null : pipeShape.trim();
    }

    public int getPipeNumber() {
        return pipeNumber;
    }

    public void setPipeNumber(int pipeNumber) {
        this.pipeNumber = pipeNumber;
    }

    public int getPipeProcessingNumber() {
        return pipeProcessingNumber;
    }

    public void setPipeProcessingNumber(int pipeProcessingNumber) {
        this.pipeProcessingNumber = pipeProcessingNumber;
    }

    public int getPipeFinishedNumber() {
        return pipeFinishedNumber;
    }

    public void setPipeFinishedNumber(int pipeFinishedNumber) {
        this.pipeFinishedNumber = pipeFinishedNumber;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getProcessOrder() {
        return processOrder;
    }

    public void setProcessOrder(String processOrder) {
        this.processOrder = processOrder;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getNextStage() {
        return nextStage;
    }

    public void setNextStage(String nextStage) {
        this.nextStage = nextStage;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }
}