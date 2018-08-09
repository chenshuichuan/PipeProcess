package llcweb.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="process_order")
public class ProcessOrder {
    @Id
    @GeneratedValue
    private Integer id;
    public ProcessOrder( ) {

    }
    public ProcessOrder(String name, String orderList) {
    }
    public ProcessOrder(Integer id, String name, String orderList) {
        this.id = id;
        this.name = name;
        this.orderList = orderList;
    }

    private String name;

    private String orderList;

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
        this.name = name == null ? null : name.trim();
    }

    public String getOrderList() {
        return orderList;
    }

    public void setOrderList(String orderList) {
        this.orderList = orderList == null ? null : orderList.trim();
    }
}