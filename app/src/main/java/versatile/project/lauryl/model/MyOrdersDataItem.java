
package versatile.project.lauryl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrdersDataItem {

    @SerializedName("vOrderNumber")
    @Expose
    private String vOrderNumber;
    @SerializedName("orderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("vAccountId")
    @Expose
    private String vAccountId;
    @SerializedName("sellerId")
    @Expose
    private String sellerId;
    @SerializedName("marketPlaceName")
    @Expose
    private String marketPlaceName;
    @SerializedName("orderDateTime")
    @Expose
    private Long orderDateTime;
    @SerializedName("paymentDateTime")
    @Expose
    private Long paymentDateTime;
    @SerializedName("orderStage")
    @Expose
    private String orderStage;
    @SerializedName("mpShippingService")
    @Expose
    private Object mpShippingService;
    @SerializedName("vShippingService")
    @Expose
    private Object vShippingService;
    @SerializedName("shippingName")
    @Expose
    private Object shippingName;
    @SerializedName("shippingAddress1")
    @Expose
    private Object shippingAddress1;
    @SerializedName("shippingAddress2")
    @Expose
    private Object shippingAddress2;
    @SerializedName("shippingAddress3")
    @Expose
    private Object shippingAddress3;
    @SerializedName("shippingCity")
    @Expose
    private Object shippingCity;
    @SerializedName("shippingState")
    @Expose
    private Object shippingState;
    @SerializedName("shippingCountry")
    @Expose
    private Object shippingCountry;
    @SerializedName("shippingCountryCode")
    @Expose
    private Object shippingCountryCode;
    @SerializedName("shippingPostCode")
    @Expose
    private Object shippingPostCode;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("buyerName")
    @Expose
    private Object buyerName;
    @SerializedName("invoicePhone")
    @Expose
    private Object invoicePhone;
    @SerializedName("invoiceAddress1")
    @Expose
    private Object invoiceAddress1;
    @SerializedName("invoiceAddress2")
    @Expose
    private Object invoiceAddress2;
    @SerializedName("invoiceAddress3")
    @Expose
    private Object invoiceAddress3;
    @SerializedName("invoiceCity")
    @Expose
    private Object invoiceCity;
    @SerializedName("invoiceState")
    @Expose
    private Object invoiceState;
    @SerializedName("invoiceCountry")
    @Expose
    private Object invoiceCountry;
    @SerializedName("invoicePostCode")
    @Expose
    private Object invoicePostCode;
    @SerializedName("promiseDispatchDate")
    @Expose
    private Object promiseDispatchDate;
    @SerializedName("promiseDeliveryDate")
    @Expose
    private Object promiseDeliveryDate;
    @SerializedName("vMergeOrderId")
    @Expose
    private Object vMergeOrderId;
    @SerializedName("orderTotal")
    @Expose
    private Object orderTotal;
    @SerializedName("latestShipDate")
    @Expose
    private Object latestShipDate;
    @SerializedName("earliestDeliveryDate")
    @Expose
    private Object earliestDeliveryDate;
    @SerializedName("isBusinessOrder")
    @Expose
    private Boolean isBusinessOrder;
    @SerializedName("isPrime")
    @Expose
    private Boolean isPrime;
    @SerializedName("orderItemDownload")
    @Expose
    private Boolean orderItemDownload;
    @SerializedName("lastUpdateDate")
    @Expose
    private Object lastUpdateDate;
    @SerializedName("orderLevelDiscountAmount")
    @Expose
    private Float orderLevelDiscountAmount;
    @SerializedName("processedToThirdParty")
    @Expose
    private Boolean processedToThirdParty;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("executedRuleName")
    @Expose
    private Object executedRuleName;
    @SerializedName("paymentReceived")
    @Expose
    private Boolean paymentReceived;
    @SerializedName("marketplaceCountryName")
    @Expose
    private Object marketplaceCountryName;
    @SerializedName("businessOrder")
    @Expose
    private Boolean businessOrder;
    @SerializedName("prime")
    @Expose
    private Boolean prime;
    @SerializedName("isOrderItemDownload")
    @Expose
    private Boolean isOrderItemDownload;

    public String getVOrderNumber() {
        return vOrderNumber;
    }

    public void setVOrderNumber(String vOrderNumber) {
        this.vOrderNumber = vOrderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getVAccountId() {
        return vAccountId;
    }

    public void setVAccountId(String vAccountId) {
        this.vAccountId = vAccountId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getMarketPlaceName() {
        return marketPlaceName;
    }

    public void setMarketPlaceName(String marketPlaceName) {
        this.marketPlaceName = marketPlaceName;
    }

    public Long getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Long orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public Long getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(Long paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public String getOrderStage() {
        return orderStage;
    }

    public void setOrderStage(String orderStage) {
        this.orderStage = orderStage;
    }

    public Object getMpShippingService() {
        return mpShippingService;
    }

    public void setMpShippingService(Object mpShippingService) {
        this.mpShippingService = mpShippingService;
    }

    public Object getVShippingService() {
        return vShippingService;
    }

    public void setVShippingService(Object vShippingService) {
        this.vShippingService = vShippingService;
    }

    public Object getShippingName() {
        return shippingName;
    }

    public void setShippingName(Object shippingName) {
        this.shippingName = shippingName;
    }

    public Object getShippingAddress1() {
        return shippingAddress1;
    }

    public void setShippingAddress1(Object shippingAddress1) {
        this.shippingAddress1 = shippingAddress1;
    }

    public Object getShippingAddress2() {
        return shippingAddress2;
    }

    public void setShippingAddress2(Object shippingAddress2) {
        this.shippingAddress2 = shippingAddress2;
    }

    public Object getShippingAddress3() {
        return shippingAddress3;
    }

    public void setShippingAddress3(Object shippingAddress3) {
        this.shippingAddress3 = shippingAddress3;
    }

    public Object getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(Object shippingCity) {
        this.shippingCity = shippingCity;
    }

    public Object getShippingState() {
        return shippingState;
    }

    public void setShippingState(Object shippingState) {
        this.shippingState = shippingState;
    }

    public Object getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(Object shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public Object getShippingCountryCode() {
        return shippingCountryCode;
    }

    public void setShippingCountryCode(Object shippingCountryCode) {
        this.shippingCountryCode = shippingCountryCode;
    }

    public Object getShippingPostCode() {
        return shippingPostCode;
    }

    public void setShippingPostCode(Object shippingPostCode) {
        this.shippingPostCode = shippingPostCode;
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

    public Object getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(Object buyerName) {
        this.buyerName = buyerName;
    }

    public Object getInvoicePhone() {
        return invoicePhone;
    }

    public void setInvoicePhone(Object invoicePhone) {
        this.invoicePhone = invoicePhone;
    }

    public Object getInvoiceAddress1() {
        return invoiceAddress1;
    }

    public void setInvoiceAddress1(Object invoiceAddress1) {
        this.invoiceAddress1 = invoiceAddress1;
    }

    public Object getInvoiceAddress2() {
        return invoiceAddress2;
    }

    public void setInvoiceAddress2(Object invoiceAddress2) {
        this.invoiceAddress2 = invoiceAddress2;
    }

    public Object getInvoiceAddress3() {
        return invoiceAddress3;
    }

    public void setInvoiceAddress3(Object invoiceAddress3) {
        this.invoiceAddress3 = invoiceAddress3;
    }

    public Object getInvoiceCity() {
        return invoiceCity;
    }

    public void setInvoiceCity(Object invoiceCity) {
        this.invoiceCity = invoiceCity;
    }

    public Object getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(Object invoiceState) {
        this.invoiceState = invoiceState;
    }

    public Object getInvoiceCountry() {
        return invoiceCountry;
    }

    public void setInvoiceCountry(Object invoiceCountry) {
        this.invoiceCountry = invoiceCountry;
    }

    public Object getInvoicePostCode() {
        return invoicePostCode;
    }

    public void setInvoicePostCode(Object invoicePostCode) {
        this.invoicePostCode = invoicePostCode;
    }

    public Object getPromiseDispatchDate() {
        return promiseDispatchDate;
    }

    public void setPromiseDispatchDate(Object promiseDispatchDate) {
        this.promiseDispatchDate = promiseDispatchDate;
    }

    public Object getPromiseDeliveryDate() {
        return promiseDeliveryDate;
    }

    public void setPromiseDeliveryDate(Object promiseDeliveryDate) {
        this.promiseDeliveryDate = promiseDeliveryDate;
    }

    public Object getVMergeOrderId() {
        return vMergeOrderId;
    }

    public void setVMergeOrderId(Object vMergeOrderId) {
        this.vMergeOrderId = vMergeOrderId;
    }

    public Object getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Object orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Object getLatestShipDate() {
        return latestShipDate;
    }

    public void setLatestShipDate(Object latestShipDate) {
        this.latestShipDate = latestShipDate;
    }

    public Object getEarliestDeliveryDate() {
        return earliestDeliveryDate;
    }

    public void setEarliestDeliveryDate(Object earliestDeliveryDate) {
        this.earliestDeliveryDate = earliestDeliveryDate;
    }

    public Boolean getIsBusinessOrder() {
        return isBusinessOrder;
    }

    public void setIsBusinessOrder(Boolean isBusinessOrder) {
        this.isBusinessOrder = isBusinessOrder;
    }

    public Boolean getIsPrime() {
        return isPrime;
    }

    public void setIsPrime(Boolean isPrime) {
        this.isPrime = isPrime;
    }

    public Boolean getOrderItemDownload() {
        return orderItemDownload;
    }

    public void setOrderItemDownload(Boolean orderItemDownload) {
        this.orderItemDownload = orderItemDownload;
    }

    public Object getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Object lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Float getOrderLevelDiscountAmount() {
        return orderLevelDiscountAmount;
    }

    public void setOrderLevelDiscountAmount(Float orderLevelDiscountAmount) {
        this.orderLevelDiscountAmount = orderLevelDiscountAmount;
    }

    public Boolean getProcessedToThirdParty() {
        return processedToThirdParty;
    }

    public void setProcessedToThirdParty(Boolean processedToThirdParty) {
        this.processedToThirdParty = processedToThirdParty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getExecutedRuleName() {
        return executedRuleName;
    }

    public void setExecutedRuleName(Object executedRuleName) {
        this.executedRuleName = executedRuleName;
    }

    public Boolean getPaymentReceived() {
        return paymentReceived;
    }

    public void setPaymentReceived(Boolean paymentReceived) {
        this.paymentReceived = paymentReceived;
    }

    public Object getMarketplaceCountryName() {
        return marketplaceCountryName;
    }

    public void setMarketplaceCountryName(Object marketplaceCountryName) {
        this.marketplaceCountryName = marketplaceCountryName;
    }

    public Boolean getBusinessOrder() {
        return businessOrder;
    }

    public void setBusinessOrder(Boolean businessOrder) {
        this.businessOrder = businessOrder;
    }

    public Boolean getPrime() {
        return prime;
    }

    public void setPrime(Boolean prime) {
        this.prime = prime;
    }

    public Boolean getIsOrderItemDownload() {
        return isOrderItemDownload;
    }

    public void setIsOrderItemDownload(Boolean isOrderItemDownload) {
        this.isOrderItemDownload = isOrderItemDownload;
    }

}
