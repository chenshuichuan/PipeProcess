package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="attachment_hub_header")
public class AttachmentHubHeader {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer projectNum;

    private String shipCode;

    private String batchId;

    private String hubNum;

    private Integer sectionId;

    private String warehouseName;

    private String warehouseManager;

    private String contact;

    private String place;

    private String receiver;

    private String receiveDepartment;

    private Date receiveTime;

    private String resourceNum;

    private String name;

    private String specification;

    private String imageNum;

    private String piece;

    private String material;

    private Integer number;

    private Integer lackNumber;

    private Date lastReplyTime;

    private Date historyReplyTime;

    private String remarks;

    private Date updateTime;

    public AttachmentHubHeader() {

    }
    public AttachmentHubHeader(Integer projectNum, String shipCode,
                               String batchId, String hubNum, Integer sectionId) {
        this.projectNum = projectNum;
        this.shipCode = shipCode;
        this.batchId = batchId;
        this.hubNum = hubNum;
        this.sectionId = sectionId;
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

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    public String getHubNum() {
        return hubNum;
    }

    public void setHubNum(String hubNum) {
        this.hubNum = hubNum == null ? null : hubNum.trim();
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getWarehouseManager() {
        return warehouseManager;
    }

    public void setWarehouseManager(String warehouseManager) {
        this.warehouseManager = warehouseManager == null ? null : warehouseManager.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getReceiveDepartment() {
        return receiveDepartment;
    }

    public void setReceiveDepartment(String receiveDepartment) {
        this.receiveDepartment = receiveDepartment == null ? null : receiveDepartment.trim();
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getResourceNum() {
        return resourceNum;
    }

    public void setResourceNum(String resourceNum) {
        this.resourceNum = resourceNum == null ? null : resourceNum.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece == null ? null : piece.trim();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getLackNumber() {
        return lackNumber;
    }

    public void setLackNumber(Integer lackNumber) {
        this.lackNumber = lackNumber;
    }

    public Date getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public Date getHistoryReplyTime() {
        return historyReplyTime;
    }

    public void setHistoryReplyTime(Date historyReplyTime) {
        this.historyReplyTime = historyReplyTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}