package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="scanner_table")
public class ScannerTable {
    @Id
    @GeneratedValue
    private int id;

    private Integer workerId;

    private Integer workplaceId;

    private Integer isLock;

    private Date workerBindingTime;

    private String description;
    private String scannerCode;

    public ScannerTable(Integer workerId, Integer workplaceId, String description, String scannerCode) {
        this.workerId = workerId;
        this.workplaceId = workplaceId;
        this.description = description;
        this.scannerCode =scannerCode;
    }
    public String getScannerCode() {
        return scannerCode;
    }

    public void setScannerCode(String scannerCode) {
        this.scannerCode = scannerCode;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Integer getWorkplaceId() {
        return workplaceId;
    }

    public void setWorkplaceId(Integer workplaceId) {
        this.workplaceId = workplaceId;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Date getWorkerBindingTime() {
        return workerBindingTime;
    }

    public void setWorkerBindingTime(Date workerBindingTime) {
        this.workerBindingTime = workerBindingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}