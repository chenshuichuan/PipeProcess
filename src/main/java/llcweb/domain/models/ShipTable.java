package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="ship_table")
public class ShipTable {
    @Id
    private String shipCode;

    private String shipName;

    private String shapeShipId;

    private Integer batchNumber;

    private Integer batchUnprocess;

    private Integer batchProcessing;

    private Integer batchProcessed;

    private Date updateTime;

    private Date finishedTime;
    public ShipTable() {

    }
    public ShipTable(String shipCode, String shipName, String shapeShipId) {
        this.shipCode = shipCode;
        this.shipName = shipName;
        this.shapeShipId = shapeShipId;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode == null ? null : shipCode.trim();
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName == null ? null : shipName.trim();
    }

    public String getShapeShipId() {
        return shapeShipId;
    }

    public void setShapeShipId(String shapeShipId) {
        this.shapeShipId = shapeShipId == null ? null : shapeShipId.trim();
    }

    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getBatchUnprocess() {
        return batchUnprocess;
    }

    public void setBatchUnprocess(Integer batchUnprocess) {
        this.batchUnprocess = batchUnprocess;
    }

    public Integer getBatchProcessing() {
        return batchProcessing;
    }

    public void setBatchProcessing(Integer batchProcessing) {
        this.batchProcessing = batchProcessing;
    }

    public Integer getBatchProcessed() {
        return batchProcessed;
    }

    public void setBatchProcessed(Integer batchProcessed) {
        this.batchProcessed = batchProcessed;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }
}