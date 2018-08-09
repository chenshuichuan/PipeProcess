package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="factory_distribution")
public class FactoryDistribution {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer projectNum;

    private String shipCode;

    private Integer sessionId;

    private String batchId;

    private String needSection;

    private String componentId;

    private String componentName;

    private String specification;

    private String imageNum;

    private String material;

    private String piece;

    private String supplier;

    private Date confirmTime;

    private Date beginRestrictions;

    private Date arriveRestrictio;

    private String deliveryPlace;

    private Integer needNum;

    private Integer actualLackNum;

    private Date actualArriveTime;

    private String remarks;

    private Integer role;

    private Date updateTime;

    private String extend;

    private Date lastReplyTime;

    private Integer historyReplyTime;
    public FactoryDistribution() {

    }
    public FactoryDistribution(Integer projectNum, String shipCode,
                               Integer sessionId, String batchId, String needSection) {
        this.projectNum = projectNum;
        this.shipCode = shipCode;
        this.sessionId = sessionId;
        this.batchId = batchId;
        this.needSection = needSection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(Integer projectNum) {
        this.projectNum = projectNum;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode == null ? null : shipCode.trim();
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    public String getNeedSection() {
        return needSection;
    }

    public void setNeedSection(String needSection) {
        this.needSection = needSection == null ? null : needSection.trim();
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId == null ? null : componentId.trim();
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName == null ? null : componentName.trim();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public String getImageNum() {
        return imageNum;
    }

    public void setImageNum(String imageNum) {
        this.imageNum = imageNum == null ? null : imageNum.trim();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece == null ? null : piece.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getBeginRestrictions() {
        return beginRestrictions;
    }

    public void setBeginRestrictions(Date beginRestrictions) {
        this.beginRestrictions = beginRestrictions;
    }

    public Date getArriveRestrictio() {
        return arriveRestrictio;
    }

    public void setArriveRestrictio(Date arriveRestrictio) {
        this.arriveRestrictio = arriveRestrictio;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace == null ? null : deliveryPlace.trim();
    }

    public Integer getNeedNum() {
        return needNum;
    }

    public void setNeedNum(Integer needNum) {
        this.needNum = needNum;
    }

    public Integer getActualLackNum() {
        return actualLackNum;
    }

    public void setActualLackNum(Integer actualLackNum) {
        this.actualLackNum = actualLackNum;
    }

    public Date getActualArriveTime() {
        return actualArriveTime;
    }

    public void setActualArriveTime(Date actualArriveTime) {
        this.actualArriveTime = actualArriveTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend == null ? null : extend.trim();
    }

    public Date getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public Integer getHistoryReplyTime() {
        return historyReplyTime;
    }

    public void setHistoryReplyTime(Integer historyReplyTime) {
        this.historyReplyTime = historyReplyTime;
    }
}