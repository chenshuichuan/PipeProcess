package llcweb.domain.entities;

import llcweb.domain.models.Departments;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

public class DepartmentTree {


    private Integer id;

    private String name;

    private Integer pId;//upDepartment

    private String iconSkin;

    private boolean open;

    public DepartmentTree() {

    }
    public DepartmentTree(Departments departments) {
        this.id = departments.getId();
        this.name = departments.getName();
        this.pId = departments.getUpDepartment();
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

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}