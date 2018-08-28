package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="taoliao")
public class Taoliao {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer planId;
    private Integer batchId;
    private String  pipeMaterial;
    private Integer pipeNumber;
    private Integer totalLength;
    private Integer isTaoliao;//是否已经进行过套料操作。方便下料工人查看，和已经"下料" 的区别不同，下料完成，是说管子以及切出来了

    public Taoliao() {
    }

    public Taoliao(Integer planId, Integer batchId,String pipeMaterial, Integer pipeNumber,
                   Integer totalLength, Integer isTaoliao) {
        this.planId = planId;
        this.batchId = batchId;
        this.pipeMaterial = pipeMaterial;
        this.pipeNumber = pipeNumber;
        this.totalLength = totalLength;
        this.isTaoliao = isTaoliao;
    }

    public String getPipeMaterial() {
        return pipeMaterial;
    }

    public void setPipeMaterial(String pipeMaterial) {
        this.pipeMaterial = pipeMaterial;
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

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getPipeNumber() {
        return pipeNumber;
    }

    public void setPipeNumber(Integer pipeNumber) {
        this.pipeNumber = pipeNumber;
    }

    public Integer getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Integer totalLength) {
        this.totalLength = totalLength;
    }

    public Integer getIsTaoliao() {
        return isTaoliao;
    }

    public void setIsTaoliao(Integer isTaoliao) {
        this.isTaoliao = isTaoliao;
    }
}