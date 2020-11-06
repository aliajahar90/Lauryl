package versatile.project.lauryl.orders.create.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CreateOrderData implements Serializable {
    @SerializedName("details")
    @Expose
    private Details details;

    public Details getDetails() {
        return details;
    }
    public void setDetails(Details details) {
        this.details = details;
    }

    public static class Details  implements Serializable{

        @SerializedName("orderNumber")
        @Expose
        private String orderNumber;
        @SerializedName("orderTotal")
        @Expose
        private String orderTotal;
        @SerializedName("vAccountId")
        @Expose
        private String vAccountId;
        @SerializedName("marketPlaceName")
        @Expose
        private String marketPlaceName;
        @SerializedName("orderDateTime")
        @Expose
        private String orderDateTime;
        @SerializedName("paymentDateTime")
        @Expose
        private String paymentDateTime;
        @SerializedName("emailId")
        @Expose
        private String emailId;
        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("paymentReceived")
        @Expose
        private Boolean paymentReceived;
        @SerializedName("shippingName")
        @Expose
        private String shippingName;
        @SerializedName("shippingAddress1")
        @Expose
        private String shippingAddress1;
        @SerializedName("shippingAddress2")
        @Expose
        private String shippingAddress2;
        @SerializedName("shippingAddress3")
        @Expose
        private String shippingAddress3;
        @SerializedName("shippingCity")
        @Expose
        private String shippingCity;
        @SerializedName("shippingState")
        @Expose
        private String shippingState;
        @SerializedName("shippingCountry")
        @Expose
        private String shippingCountry;
        @SerializedName("shippingPostCode")
        @Expose
        private String shippingPostCode;
        @SerializedName("invoiceAddress1")
        @Expose
        private String invoiceAddress1;
        @SerializedName("invoiceAddress2")
        @Expose
        private String invoiceAddress2;
        @SerializedName("invoiceAddress3")
        @Expose
        private String invoiceAddress3;
        @SerializedName("invoiceCity")
        @Expose
        private String invoiceCity;
        @SerializedName("invoiceState")
        @Expose
        private String invoiceState;
        @SerializedName("invoiceCountry")
        @Expose
        private String invoiceCountry;
        @SerializedName("invoicePostCode")
        @Expose
        private String invoicePostCode;
        @SerializedName("pickupAddress1")
        @Expose
        private String pickupAddress1;
        @SerializedName("pickupAddress2")
        @Expose
        private String pickupAddress2;
        @SerializedName("pickupAddress3")
        @Expose
        private String pickupAddress3;
        @SerializedName("pickupCity")
        @Expose
        private String pickupCity;
        @SerializedName("pickupState")
        @Expose
        private String pickupState;
        @SerializedName("pickupCountry")
        @Expose
        private String pickupCountry;
        @SerializedName("pickupCountryCode")
        @Expose
        private String pickupCountryCode;
        @SerializedName("orderStage")
        @Expose
        private String orderStage;
        @SerializedName("transactionId")
        @Expose
        private String transactionId;
        @SerializedName("serviceList")
        @Expose
        private List<String> serviceList = null;
        @SerializedName("razorPayOrderId")
        @Expose
        private String razorPayOrderId;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("pickupDate")
        @Expose
        private String pickupDate;
        @SerializedName("pickupSlot")
        @Expose
        private String pickupSlot;
        @SerializedName("specialInstructions")
        @Expose
        private String specialInstructions;
        @SerializedName("buyerName")
        @Expose
        private String buyerName;
        @SerializedName("reSchedule")
        @Expose
        private boolean reSchedule;

        public boolean isReSchedule() {
            return reSchedule;
        }

        public void setReSchedule(boolean reSchedule) {
            this.reSchedule = reSchedule;
        }

        public String getSpecialInstructions() {
            return specialInstructions;
        }

        public void setSpecialInstructions(String specialInstructions) {
            this.specialInstructions = specialInstructions;
        }

        public String getBuyerName() {
            return buyerName;
        }

        public void setBuyerName(String buyerName) {
            this.buyerName = buyerName;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getPickupDate() {
            return pickupDate;
        }

        public void setPickupDate(String pickupDate) {
            this.pickupDate = pickupDate;
        }

        public String getPickupSlot() {
            return pickupSlot;
        }

        public void setPickupSlot(String pickupSlot) {
            this.pickupSlot = pickupSlot;
        }

        public String getRazorPayOrderId() {
            return razorPayOrderId;
        }

        public void setRazorPayOrderId(String razorPayOrderId) {
            this.razorPayOrderId = razorPayOrderId;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderTotal() {
            return orderTotal;
        }

        public void setOrderTotal(String orderTotal) {
            this.orderTotal = orderTotal;
        }

        public String getVAccountId() {
            return vAccountId;
        }

        public void setVAccountId(String vAccountId) {
            this.vAccountId = vAccountId;
        }

        public String getMarketPlaceName() {
            return marketPlaceName;
        }

        public void setMarketPlaceName(String marketPlaceName) {
            this.marketPlaceName = marketPlaceName;
        }

        public String getOrderDateTime() {
            return orderDateTime;
        }

        public void setOrderDateTime(String orderDateTime) {
            this.orderDateTime = orderDateTime;
        }

        public String getPaymentDateTime() {
            return paymentDateTime;
        }

        public void setPaymentDateTime(String paymentDateTime) {
            this.paymentDateTime = paymentDateTime;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Boolean getPaymentReceived() {
            return paymentReceived;
        }

        public void setPaymentReceived(Boolean paymentReceived) {
            this.paymentReceived = paymentReceived;
        }

        public String getShippingName() {
            return shippingName;
        }

        public void setShippingName(String shippingName) {
            this.shippingName = shippingName;
        }

        public String getShippingAddress1() {
            return shippingAddress1;
        }

        public void setShippingAddress1(String shippingAddress1) {
            this.shippingAddress1 = shippingAddress1;
        }

        public String getShippingAddress2() {
            return shippingAddress2;
        }

        public void setShippingAddress2(String shippingAddress2) {
            this.shippingAddress2 = shippingAddress2;
        }

        public String getShippingAddress3() {
            return shippingAddress3;
        }

        public void setShippingAddress3(String shippingAddress3) {
            this.shippingAddress3 = shippingAddress3;
        }

        public String getShippingCity() {
            return shippingCity;
        }

        public void setShippingCity(String shippingCity) {
            this.shippingCity = shippingCity;
        }

        public String getShippingState() {
            return shippingState;
        }

        public void setShippingState(String shippingState) {
            this.shippingState = shippingState;
        }

        public String getShippingCountry() {
            return shippingCountry;
        }

        public void setShippingCountry(String shippingCountry) {
            this.shippingCountry = shippingCountry;
        }

        public String getShippingPostCode() {
            return shippingPostCode;
        }

        public void setShippingPostCode(String shippingPostCode) {
            this.shippingPostCode = shippingPostCode;
        }

        public String getInvoiceAddress1() {
            return invoiceAddress1;
        }

        public void setInvoiceAddress1(String invoiceAddress1) {
            this.invoiceAddress1 = invoiceAddress1;
        }

        public String getInvoiceAddress2() {
            return invoiceAddress2;
        }

        public void setInvoiceAddress2(String invoiceAddress2) {
            this.invoiceAddress2 = invoiceAddress2;
        }

        public String getInvoiceAddress3() {
            return invoiceAddress3;
        }

        public void setInvoiceAddress3(String invoiceAddress3) {
            this.invoiceAddress3 = invoiceAddress3;
        }

        public String getInvoiceCity() {
            return invoiceCity;
        }

        public void setInvoiceCity(String invoiceCity) {
            this.invoiceCity = invoiceCity;
        }

        public String getInvoiceState() {
            return invoiceState;
        }

        public void setInvoiceState(String invoiceState) {
            this.invoiceState = invoiceState;
        }

        public String getInvoiceCountry() {
            return invoiceCountry;
        }

        public void setInvoiceCountry(String invoiceCountry) {
            this.invoiceCountry = invoiceCountry;
        }

        public String getInvoicePostCode() {
            return invoicePostCode;
        }

        public void setInvoicePostCode(String invoicePostCode) {
            this.invoicePostCode = invoicePostCode;
        }

        public String getPickupAddress1() {
            return pickupAddress1;
        }

        public void setPickupAddress1(String pickupAddress1) {
            this.pickupAddress1 = pickupAddress1;
        }

        public String getPickupAddress2() {
            return pickupAddress2;
        }

        public void setPickupAddress2(String pickupAddress2) {
            this.pickupAddress2 = pickupAddress2;
        }

        public String getPickupAddress3() {
            return pickupAddress3;
        }

        public void setPickupAddress3(String pickupAddress3) {
            this.pickupAddress3 = pickupAddress3;
        }

        public String getPickupCity() {
            return pickupCity;
        }

        public void setPickupCity(String pickupCity) {
            this.pickupCity = pickupCity;
        }

        public String getPickupState() {
            return pickupState;
        }

        public void setPickupState(String pickupState) {
            this.pickupState = pickupState;
        }

        public String getPickupCountry() {
            return pickupCountry;
        }

        public void setPickupCountry(String pickupCountry) {
            this.pickupCountry = pickupCountry;
        }

        public String getPickupCountryCode() {
            return pickupCountryCode;
        }

        public void setPickupCountryCode(String pickupCountryCode) {
            this.pickupCountryCode = pickupCountryCode;
        }

        public String getOrderStage() {
            return orderStage;
        }

        public void setOrderStage(String orderStage) {
            this.orderStage = orderStage;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public List<String> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<String> serviceList) {
            this.serviceList = serviceList;
        }

    }
}
