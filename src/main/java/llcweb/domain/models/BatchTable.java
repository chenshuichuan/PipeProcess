package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="batch_table")
public class BatchTable {

    @Id
    @GeneratedValue
    private Integer batchId;

    private String batchName;

    private String batchDescription;

    private String shipCode;

    private Integer unitNumber;

    private Integer unitProcessingNumber;

    private Integer unitFinishedNumber;

    private Date finishedTime;

    private Integer lackNumber;
    public BatchTable() {

    }
    public BatchTable(String batchName, String batchDescription, String shipCode, Integer unitNumber) {
        this.batchName = batchName;
        this.batchDescription = batchDescription;
        this.shipCode = shipCode;
        this.unitNumber = unitNumber;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
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

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode == null ? null : shipCode.trim();
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Integer getUnitProcessingNumber() {
        return unitProcessingNumber;
    }

    public void setUnitProcessingNumber(Integer unitProcessingNumber) {
        this.unitProcessingNumber = unitProcessingNumber;
    }

    public Integer getUnitFinishedNumber() {
        return unitFinishedNumber;
    }

    public void setUnitFinishedNumber(Integer unitFinishedNumber) {
        this.unitFinishedNumber = unitFinishedNumber;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Integer getLackNumber() {
        return lackNumber;
    }

    public void setLackNumber(Integer lackNumber) {
        this.lackNumber = lackNumber;
    }
}