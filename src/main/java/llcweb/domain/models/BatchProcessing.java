package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="batch_processing")
public class BatchProcessing {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer batchId;

    private String sectionName;

    private Integer pipeNumber;

    private Integer unitNumber;

    private Integer unitProcessingNumber;

    private Integer unitFinishedNumber;

    private Date finishedTime;

    private Integer cutterId;

    private Date cuttedTime;

    private Integer batchArrangeId;
    public BatchProcessing() {

    }
    public BatchProcessing(Integer batchId, String sectionName,
                           Integer pipeNumber, Integer unitNumber) {
        this.batchId = batchId;
        this.sectionName = sectionName;
        this.pipeNumber = pipeNumber;
        this.unitNumber = unitNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName == null ? null : sectionName.trim();
    }

    public Integer getPipeNumber() {
        return pipeNumber;
    }

    public void setPipeNumber(Integer pipeNumber) {
        this.pipeNumber = pipeNumber;
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

    public Integer getCutterId() {
        return cutterId;
    }

    public void setCutterId(Integer cutterId) {
        this.cutterId = cutterId;
    }

    public Date getCuttedTime() {
        return cuttedTime;
    }

    public void setCuttedTime(Date cuttedTime) {
        this.cuttedTime = cuttedTime;
    }

    public Integer getBatchArrangeId() {
        return batchArrangeId;
    }

    public void setBatchArrangeId(Integer batchArrangeId) {
        this.batchArrangeId = batchArrangeId;
    }
}