package llcweb.domain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 *@Author: Ricardo
 *@Description:  下料页面管件表格封装数据
 *@Date: 11:07 2018/9/1
 *@param:
 **/

public class CutPipeInfo {

    private Integer pipeId;
    private String batchName;
    private String unitName;
    private Integer cutLength;
    private String pipeCode;
    private int isCutted;

    public int getIsCutted() {
        return isCutted;
    }

    public void setIsCutted(int isCutted) {
        this.isCutted = isCutted;
    }

    public CutPipeInfo() {
    }

    public CutPipeInfo(Integer pipeId, Integer cutLength, String pipeCode) {
        this.pipeId = pipeId;
        this.cutLength = cutLength;
        this.pipeCode = pipeCode;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
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
        this.pipeCode = pipeCode;
    }
}