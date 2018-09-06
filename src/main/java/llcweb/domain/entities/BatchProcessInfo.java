package llcweb.domain.entities;

import llcweb.domain.models.BatchTable;
import llcweb.domain.models.ShipTable;
import llcweb.tools.CalculateUtil;

import java.util.Date;

public class BatchProcessInfo {

    private int batchId;
    private String shipName;
    private String batchName;
    private String batchDescription;
    private int lackNumber;//暂时没有上
    private Date finishedTime;

    private Integer unitNumber;
    private Integer unitFinished;
    private double unitFinishedRate;

    private Integer pipeNumber;
    private Integer pipeFinished;
    private double pipeFinishedRate;

    private int isCutted;//批次是否已经下料0/1 综合plan 表的所有结果

    public BatchProcessInfo() {

    }
    public BatchProcessInfo(BatchTable batchTable) {
        this.batchId = batchTable.getBatchId();
        this.batchName = batchTable.getBatchName();
        this.batchDescription = batchTable.getBatchDescription();
        this.unitNumber = batchTable.getUnitNumber();
        this.unitFinished = batchTable.getUnitFinishedNumber();
        this.lackNumber = batchTable.getLackNumber();
        this.finishedTime = batchTable.getFinishedTime();

        double temp =0;
        if(unitNumber!=0)temp = (double)unitFinished/(double)unitNumber;
        this.unitFinishedRate = CalculateUtil.DecimalDouble(temp,4);
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

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String bacthName) {
        this.batchName = bacthName;
    }

    public String getBatchDescription() {
        return batchDescription;
    }

    public void setBatchDescription(String batchDescription) {
        this.batchDescription = batchDescription;
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

    public int getIsCutted() {
        return isCutted;
    }

    public void setIsCutted(int isCutted) {
        this.isCutted = isCutted;
    }

    public int getLackNumber() {
        return lackNumber;
    }

    public void setLackNumber(int lackNumber) {
        this.lackNumber = lackNumber;
    }
}