package versatile.project.lauryl.orders.history.model;

import java.io.Serializable;

import versatile.project.lauryl.model.MyOrdersDataItem;

public class OrderData implements Serializable {
    private String orderIdVal;
    private String date;
    private String OrderStage;
    private String zohoInvoiceId="";
    private String myOrdersDataItem;

    public String getZohoInvoiceId() {
        return zohoInvoiceId;
    }

    public void setZohoInvoiceId(String zohoInvoiceId) {
        this.zohoInvoiceId = zohoInvoiceId;
    }


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

    public String getMyOrdersDataItem() {
        return myOrdersDataItem;
    }

    public void setMyOrdersDataItem(String myOrdersDataItem) {
        this.myOrdersDataItem = myOrdersDataItem;
    }
}
