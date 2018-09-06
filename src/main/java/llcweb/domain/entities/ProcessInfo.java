package llcweb.domain.entities;

import llcweb.domain.models.ShipTable;
import llcweb.tools.CalculateUtil;

import java.util.Date;

/**
 *@Author: Ricardo
 *@Description: 一个封装加工信息的类
 *@Date: 19:24 2018/9/6
 *@param:
 **/
public class ProcessInfo {

    private Integer number;
    private Integer finished;
    private double  finishedRate;
    private Date    finishedTime;
    public ProcessInfo() {
    }

    public ProcessInfo(Integer number, Integer finished, double finishedRate, Date finishedTime) {
        this.number = number;
        this.finished = finished;
        this.finishedRate = finishedRate;
        this.finishedTime = finishedTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public double getFinishedRate() {
        return finishedRate;
    }

    public void setFinishedRate(double finishedRate) {
        this.finishedRate = finishedRate;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }
}