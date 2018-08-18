package llcweb.domain.entities;

import llcweb.domain.models.Departments;
import llcweb.tools.DateUtil;

/**
 *@Author: Ricardo
 *@Description: 部门展示信息类
 *@Date: 23:33 2018/8/17
 *@param:
 **/

public class DepartmentInfo {

    private Integer id;
    private String name;
    private String level;
    private String description;
    private String updateTime;

    private String upDepartment;//upDepartment
    private String bangdingWorker;
    private String scanner;//工位的话，绑定的扫码枪
    private int isLock;//工位的话，绑定的扫码枪,当天是否锁定

    public DepartmentInfo() {

    }

    public String getScanner() {
        return scanner;
    }

    public void setScanner(String scanner) {
        this.scanner = scanner;
    }

    public int getIsLock() {
        return isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }

    public DepartmentInfo(Departments departments) {
        this.id = departments.getId();
        this.name = departments.getName();
        this.description = departments.getDescription();
        this.updateTime = DateUtil.formatDateTimeString(departments.getUpdatatTime());
        this.level = departments.getLever()==0?"工段":(departments.getLever()==1?"工序":"工位");
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpDepartment() {
        return upDepartment;
    }

    public void setUpDepartment(String upDepartment) {
        this.upDepartment = upDepartment;
    }

    public String getBangdingWorker() {
        return bangdingWorker;
    }

    public void setBangdingWorker(String bangdingWorker) {
        this.bangdingWorker = bangdingWorker;
    }
}