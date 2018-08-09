package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="pipe_processing")
public class PipeProcessing {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer pipeId;

    private Integer processState;

    private Integer processIndex;

    private Integer processPlace;

    private Integer workerId;

    private Integer isFinished;

    private Date finishedTime;
    public PipeProcessing() {

    }
    public PipeProcessing(Integer pipeId, Integer processState,
                          Integer processIndex, Integer processPlace, Integer workerId) {
        this.pipeId = pipeId;
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

    public Integer getPipeId() {
        return pipeId;
    }

    public void setPipeId(Integer pipeId) {
        this.pipeId = pipeId;
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