package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name="unit_processing")
public class UnitProcessing {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer unitId;

    private Integer processState;

    private Integer processIndex;

    private Integer processPlace;

    private Integer workerId;

    private Integer pipeProcessingNumber;

    private Integer pipeFinishedNumber;

    private Date finishedTime;

    private Date beginTime;

    private Integer arrangeId;
    public UnitProcessing( ) {
    }
    public UnitProcessing(Integer unitId, Integer processState,
                          Integer processIndex, Integer processPlace,
                          Integer workerId) {
        this.unitId = unitId;
        this.processState = processState;
        this.processIndex = processIndex;
        this.processPlace = processPlace;
        this.workerId = workerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getProcessState() {
        return processState;
    }

    public void setProcessState(Integer processState) {
        this.processState = processState;
    }

    public Integer getProcessIndex() {
        return processIndex;
    }

    public void setProcessIndex(Integer processIndex) {
        this.processIndex = processIndex;
    }

    public Integer getProcessPlace() {
        return processPlace;
    }

    public void setProcessPlace(Integer processPlace) {
        this.processPlace = processPlace;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(Integer arrangeId) {
        this.arrangeId = arrangeId;
    }
}