package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="pipe_table")
public class PipeTable {
    @Id
    @GeneratedValue
    private Integer pipeId;

    private String shipCode;

    private Integer batchId;

    private Integer unitId;

    private String areaCode;

    private String pipeMaterial;

    private Integer pipeMaterialLevel;

    private Integer noInstalled;

    private Integer cutLength;

    private String pipeCode;

    private String pipeShape;

    private String surfaceTreat;

    private String surfaceName;

    private Integer outfield;

    private Date updateTime;



    private String unitName;
    private Integer isOutOfUnit;
    public PipeTable() {
    }
    public PipeTable(String shipCode, Integer batchId, Integer unitId,
                     Integer cutLength, String pipeCode, Integer isOutOfUnit) {
        this.shipCode = shipCode;
        this.batchId = batchId;
        this.unitId = unitId;
        this.cutLength = cutLength;
        this.pipeCode = pipeCode;
        this.isOutOfUnit = isOutOfUnit;
    }

    public PipeTable(String shipCode, Integer batchId, Integer unitId, String areaCode,
                     String pipeMaterial, Integer pipeMaterialLevel, Integer noInstalled,
                     Integer cutLength, String pipeCode, String pipeShape,
                     String surfaceTreat, String surfaceName, Integer outfield, Integer isOutOfUnit) {
        this.shipCode = shipCode;
        this.batchId = batchId;
        this.unitId = unitId;
        this.areaCode = areaCode;
        this.pipeMaterial = pipeMaterial;
        this.pipeMaterialLevel = pipeMaterialLevel;
        this.noInstalled = noInstalled;
        this.cutLength = cutLength;
        this.pipeCode = pipeCode;
        this.pipeShape = pipeShape;
        this.surfaceTreat = surfaceTreat;
        this.surfaceName = surfaceName;
        this.outfield = outfield;
        this.isOutOfUnit = isOutOfUnit;
    }
    public Integer getIsOutOfUnit() {
        return isOutOfUnit;
    }

    public void setIsOutOfUnit(Integer isOutOfUnit) {
        this.isOutOfUnit = isOutOfUnit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    public Integer getPipeId() {
        return pipeId;
    }

    public void setPipeId(Integer pipeId) {
        this.pipeId = pipeId;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode == null ? null : shipCode.trim();
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getPipeMaterial() {
        return pipeMaterial;
    }

    public void setPipeMaterial(String pipeMaterial) {
        this.pipeMaterial = pipeMaterial == null ? null : pipeMaterial.trim();
    }

    public Integer getPipeMaterialLevel() {
        return pipeMaterialLevel;
    }

    public void setPipeMaterialLevel(Integer pipeMaterialLevel) {
        this.pipeMaterialLevel = pipeMaterialLevel;
    }

    public Integer getNoInstalled() {
        return noInstalled;
    }

    public void setNoInstalled(Integer noInstalled) {
        this.noInstalled = noInstalled;
    }

    public Integer getCutLength() {
        return cutLength;
    }

    public void setCutLength(Integer cutLength) {
        this.cutLength = cutLength;
    }

    public String getPipeCode() {
        return pipeCode;
    }

    public void setPipeCode(String pipeCode) {
        this.pipeCode = pipeCode == null ? null : pipeCode.trim();
    }

    public String getPipeShape() {
        return pipeShape;
    }

    public void setPipeShape(String pipeShape) {
        this.pipeShape = pipeShape == null ? null : pipeShape.trim();
    }

    public String getSurfaceTreat() {
        return surfaceTreat;
    }

    public void setSurfaceTreat(String surfaceTreat) {
        this.surfaceTreat = surfaceTreat == null ? null : surfaceTreat.trim();
    }

    public String getSurfaceName() {
        return surfaceName;
    }

    public void setSurfaceName(String surfaceName) {
        this.surfaceName = surfaceName == null ? null : surfaceName.trim();
    }

    public Integer getOutfield() {
        return outfield;
    }

    public void setOutfield(Integer outfield) {
        this.outfield = outfield;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}