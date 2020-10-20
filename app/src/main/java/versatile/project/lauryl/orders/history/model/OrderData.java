package versatile.project.lauryl.orders.history.model;

import java.io.Serializable;

public class OrderData implements Serializable {
    private String orderIdVal;
    private String date;
    private String OrderStage;

    public String getOrderIdVal() {
        return orderIdVal;
    }

    public void setOrderIdVal(String orderIdVal) {
        this.orderIdVal = orderIdVal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderStage() {
        return OrderStage;
    }

    public void setOrderStage(String orderStage) {
        OrderStage = orderStage;
    }
}
