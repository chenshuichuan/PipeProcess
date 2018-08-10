package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="unit_table")
public class UnitTable {
    @Id
    @GeneratedValue
    private Integer unitId;

    private String unitName;

    private String shipCode;

    private Integer batchId;
    private String batchName;
    private String description;

    private Integer planId;

    private String pipeShape;

    private Integer pipeNumber;

    private Integer section;

    private Integer processOrder;

    private Integer processState;

    private Integer pipeProcessingNumber;

    private Integer pipeFinishedNumber;

    private Date finishedTime;

    public UnitTable( ) {

    }

    public UnitTable(String unitName, String shipCode, Integer batchId, String description) {
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

    public Integer getPipeNumber() {
        return pipeNumber;
    }

    public void setPipeNumber(Integer pipeNumber) {
        this.pipeNumber = pipeNumber;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getProcessOrder() {
        return processOrder;
    }

    public void setProcessOrder(Integer processOrder) {
        this.processOrder = processOrder;
    }

    public Integer getProcessState() {
        return processState;
    }

    public void setProcessState(Integer processState) {
        this.processState = processState;
    }

    public Integer getPipeProcessingNumber() {
        return pipeProcessingNumber;
    }

    public void setPipeProcessingNumber(Integer pipeProcessingNumber) {
        this.pipeProcessingNumber = pipeProcessingNumber;
    }

    public Integer getPipeFinishedNumber() {
        return pipeFinishedNumber;
    }

    public void setPipeFinishedNumber(Integer pipeFinishedNumber) {
        this.pipeFinishedNumber = pipeFinishedNumber;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }
}