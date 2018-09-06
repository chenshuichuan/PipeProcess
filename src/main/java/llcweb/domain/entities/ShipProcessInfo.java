package llcweb.domain.entities;

import llcweb.domain.models.ShipTable;
import llcweb.tools.CalculateUtil;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

public class ShipProcessInfo {

    private String shipCode;
    private String shipName;
    private String shapeShipId;

    private Integer batchNumber;
    private Integer batchFinished;
    private double batchFinishedRate;
    private Date finishedTime;

    private Integer unitNumber;
    private Integer unitFinished;
    private double unitFinishedRate;

    private Integer pipeNumber;
    private Integer pipeFinished;
    private double pipeFinishedRate;
    public ShipProcessInfo() {

    }
    public ShipProcessInfo(ShipTable shipTable) {
        this.shipCode = shipTable.getShipCode();
        this.shipName = shipTable.getShipName();
        this.shapeShipId = shipTable.getShapeShipId();
        this.batchNumber = shipTable.getBatchNumber();
        this.batchFinished = shipTable.getBatchProcessed();
        double temp =0;
        if(batchNumber!=0)temp = (double)batchFinished/(double)batchNumber;

        this.batchFinishedRate = CalculateUtil.DecimalDouble(temp,4);
        this.finishedTime = shipTable.getFinishedTime();
    }
    public ShipProcessInfo(String shipCode, String shipName, String shapeShipId) {
        this.shipCode = shipCode;
        this.shipName = shipName;
        this.shapeShipId = shapeShipId;
    }
    public void setUnit(int unitNumber,int unitFinished){
        double temp =0;
        if(unitNumber!=0)temp = (double)unitFinished/(double)unitNumber;
        this.unitFinishedRate = CalculateUtil.DecimalDouble(temp,4);
        this.unitNumber = unitNumber;
        this.unitFinished = unitFinished;
    }
    public void setPipe(int pipeNumber,int pipeFinished){
        double temp =0;
        if(pipeNumber!=0)temp = (double)pipeFinished/(double)pipeNumber;
        this.pipeFinishedRate = CalculateUtil.DecimalDouble(temp,4);
        this.pipeNumber = pipeNumber;
        this.pipeFinished = pipeFinished;
    }
    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShapeShipId() {
        return shapeShipId;
    }

    public void setShapeShipId(String shapeShipId) {
        this.shapeShipId = shapeShipId;
    }

    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getBatchFinished() {
        return batchFinished;
    }

    public void setBatchFinished(Integer batchFinished) {
        this.batchFinished = batchFinished;
    }

    public double getBatchFinishedRate() {
        return batchFinishedRate;
    }

    public void setBatchFinishedRate(double batchFinishedRate) {
        this.batchFinishedRate = batchFinishedRate;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Integer getUnitFinished() {
        return unitFinished;
    }

    public void setUnitFinished(Integer unitFinished) {
        this.unitFinished = unitFinished;
    }

    public double getUnitFinishedRate() {
        return unitFinishedRate;
    }

    public void setUnitFinishedRate(double unitFinishedRate) {
        this.unitFinishedRate = unitFinishedRate;
    }

    public Integer getPipeNumber() {
        return pipeNumber;
    }

    public void setPipeNumber(Integer pipeNumber) {
        this.pipeNumber = pipeNumber;
    }

    public Integer getPipeFinished() {
        return pipeFinished;
    }

    public void setPipeFinished(Integer pipeFinished) {
        this.pipeFinished = pipeFinished;
    }

    public double getPipeFinishedRate() {
        return pipeFinishedRate;
    }

    public void setPipeFinishedRate(double pipeFinishedRate) {
        this.pipeFinishedRate = pipeFinishedRate;
    }
}