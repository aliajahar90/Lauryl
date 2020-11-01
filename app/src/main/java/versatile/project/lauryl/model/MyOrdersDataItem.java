
package versatile.project.lauryl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    private String mpShippingService;
    @SerializedName("vShippingService")
    @Expose
    private String vShippingService;
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
    @SerializedName("shippingCountryCode")
    @Expose
    private String shippingCountryCode;
    @SerializedName("shippingPostCode")
    @Expose
    private String shippingPostCode;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("buyerName")
    @Expose
    private String buyerName;
    @SerializedName("invoicePhone")
    @Expose
    private String invoicePhone;
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
    @SerializedName("promiseDispatchDate")
    @Expose
    private String promiseDispatchDate;
    @SerializedName("promiseDeliveryDate")
    @Expose
    private String promiseDeliveryDate;
    @SerializedName("vMergeOrderId")
    @Expose
    private String vMergeOrderId;
    @SerializedName("orderTotal")
    @Expose
    private String orderTotal;
    @SerializedName("latestShipDate")
    @Expose
    private String latestShipDate;
    @SerializedName("earliestDeliveryDate")
    @Expose
    private String earliestDeliveryDate;
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
    private String lastUpdateDate;
    @SerializedName("orderLevelDiscountAmount")
    @Expose
    private String orderLevelDiscountAmount;
    @SerializedName("processedToThirdParty")
    @Expose
    private Boolean processedToThirdParty;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("executedRuleName")
    @Expose
    private String executedRuleName;
    @SerializedName("paymentReceived")
    @Expose
    private Boolean paymentReceived;
    @SerializedName("marketplaceCountryName")
    @Expose
    private String marketplaceCountryName;
    @SerializedName("magentoInvoice")
    @Expose
    private Boolean magentoInvoice;

    public String getvOrderNumber() {
        return vOrderNumber;
    }

    public void setvOrderNumber(String vOrderNumber) {
        this.vOrderNumber = vOrderNumber;
    }

    public String getvAccountId() {
        return vAccountId;
    }

    public void setvAccountId(String vAccountId) {
        this.vAccountId = vAccountId;
    }

    public String getvShippingService() {
        return vShippingService;
    }

    public void setvShippingService(String vShippingService) {
        this.vShippingService = vShippingService;
    }

    public String getvMergeOrderId() {
        return vMergeOrderId;
    }

    public void setvMergeOrderId(String vMergeOrderId) {
        this.vMergeOrderId = vMergeOrderId;
    }

    public Boolean getMagentoInvoice() {
        return magentoInvoice;
    }

    public void setMagentoInvoice(Boolean magentoInvoice) {
        this.magentoInvoice = magentoInvoice;
    }

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getModifiedByUser() {
        return modifiedByUser;
    }

    public void setModifiedByUser(String modifiedByUser) {
        this.modifiedByUser = modifiedByUser;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @SerializedName("serviceList")
    @Expose
    private List<String> serviceList = null;
    @SerializedName("createdByUser")
    @Expose
    private String createdByUser;
    @SerializedName("modifiedByUser")
    @Expose
    private String modifiedByUser;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("modifiedAt")
    @Expose
    private String modifiedAt;
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

    public String getMpShippingService() {
        return mpShippingService;
    }

    public void setMpShippingService(String mpShippingService) {
        this.mpShippingService = mpShippingService;
    }

    public String getVShippingService() {
        return vShippingService;
    }

    public void setVShippingService(String vShippingService) {
        this.vShippingService = vShippingService;
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

    public String getShippingCountryCode() {
        return shippingCountryCode;
    }

    public void setShippingCountryCode(String shippingCountryCode) {
        this.shippingCountryCode = shippingCountryCode;
    }

    public String getShippingPostCode() {
        return shippingPostCode;
    }

    public void setShippingPostCode(String shippingPostCode) {
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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getInvoicePhone() {
        return invoicePhone;
    }

    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone;
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

    public String getPromiseDispatchDate() {
        return promiseDispatchDate;
    }

    public void setPromiseDispatchDate(String promiseDispatchDate) {
        this.promiseDispatchDate = promiseDispatchDate;
    }

    public String getPromiseDeliveryDate() {
        return promiseDeliveryDate;
    }

    public void setPromiseDeliveryDate(String promiseDeliveryDate) {
        this.promiseDeliveryDate = promiseDeliveryDate;
    }

    public String getVMergeOrderId() {
        return vMergeOrderId;
    }

    public void setVMergeOrderId(String vMergeOrderId) {
        this.vMergeOrderId = vMergeOrderId;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getLatestShipDate() {
        return latestShipDate;
    }

    public void setLatestShipDate(String latestShipDate) {
        this.latestShipDate = latestShipDate;
    }

    public String getEarliestDeliveryDate() {
        return earliestDeliveryDate;
    }

    public void setEarliestDeliveryDate(String earliestDeliveryDate) {
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

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getOrderLevelDiscountAmount() {
        return orderLevelDiscountAmount;
    }

    public void setOrderLevelDiscountAmount(String orderLevelDiscountAmount) {
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

    public String getExecutedRuleName() {
        return executedRuleName;
    }

    public void setExecutedRuleName(String executedRuleName) {
        this.executedRuleName = executedRuleName;
    }

    public Boolean getPaymentReceived() {
        return paymentReceived;
    }

    public void setPaymentReceived(Boolean paymentReceived) {
        this.paymentReceived = paymentReceived;
    }

    public String getMarketplaceCountryName() {
        return marketplaceCountryName;
    }

    public void setMarketplaceCountryName(String marketplaceCountryName) {
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
