package llcweb.domain.entities;

import llcweb.domain.models.UnitTable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


public class Units {

    private Integer unitId;
    private String unitName;
    private String batchName;
    private String description;
    private String pipeShape;
    private Integer pipeNumber;
    private Integer processOrder;


    public Units( ) {

    }
    public Units(UnitTable unitTable) {
        this.unitId = unitTable.getUnitId();
        this.unitName = unitTable.getUnitName();
        this.batchName = unitTable.getBatchName();
        this.description = unitTable.getDescription();
        this.pipeShape = unitTable.getPipeShape();
        this.pipeNumber = unitTable.getPipeNumber();
        this.processOrder = unitTable.getProcessOrder();
    }
    public Units(Integer unitId, String unitName, String batchName, String description,
                 String pipeShape, Integer pipeNumber, Integer processOrder) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.batchName = batchName;
        this.description = description;
        this.pipeShape = pipeShape;
        this.pipeNumber = pipeNumber;
        this.processOrder = processOrder;
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
        this.unitName = unitName;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPipeShape() {
        return pipeShape;
    }

    public void setPipeShape(String pipeShape) {
        this.pipeShape = pipeShape;
    }

    public Integer getPipeNumber() {
        return pipeNumber;
    }

    public void setPipeNumber(Integer pipeNumber) {
        this.pipeNumber = pipeNumber;
    }

    public Integer getProcessOrder() {
        return processOrder;
    }

    public void setProcessOrder(Integer processOrder) {
        this.processOrder = processOrder;
    }
}