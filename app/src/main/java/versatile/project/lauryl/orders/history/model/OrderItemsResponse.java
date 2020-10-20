package versatile.project.lauryl.orders.history.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderItemsResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("totalCount")
        @Expose
        private Integer totalCount;
        @SerializedName("list")
        @Expose
        private java.util.List<ServiceItem> list = null;

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public java.util.List<ServiceItem> getList() {
            return list;
        }

        public void setList(java.util.List<ServiceItem> list) {
            this.list = list;
        }

    }
    public class ServiceItem {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("orderItemId")
        @Expose
        private String orderItemId;
        @SerializedName("orderNumber")
        @Expose
        private String orderNumber;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("vSku")
        @Expose
        private String vSku;
        @SerializedName("ean")
        @Expose
        private String ean;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("productPrice")
        @Expose
        private String productPrice;
        @SerializedName("shippingCost")
        @Expose
        private String shippingCost;
        @SerializedName("itemTax")
        @Expose
        private String itemTax;
        @SerializedName("shippingTax")
        @Expose
        private String shippingTax;
        @SerializedName("discountCode")
        @Expose
        private String discountCode;
        @SerializedName("asin")
        @Expose
        private String asin;
        @SerializedName("qtyPurchased")
        @Expose
        private String qtyPurchased;
        @SerializedName("promotionDiscountAmount")
        @Expose
        private String promotionDiscountAmount;
        @SerializedName("shippingDiscountAmount")
        @Expose
        private String shippingDiscountAmount;
        @SerializedName("vOrderId")
        @Expose
        private String vOrderId;
        @SerializedName("orderStage")
        @Expose
        private String orderStage;
        @SerializedName("vOrderStage")
        @Expose
        private String vOrderStage;
        @SerializedName("trasactionId")
        @Expose
        private String trasactionId;
        @SerializedName("vShippingService")
        @Expose
        private String vShippingService;
        @SerializedName("stockAllocatedStatus")
        @Expose
        private String stockAllocatedStatus;
        @SerializedName("stockReversal")
        @Expose
        private Boolean stockReversal;
        @SerializedName("supplierName")
        @Expose
        private String supplierName;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("modifiedBy")
        @Expose
        private String modifiedBy;
        @SerializedName("modifiedAt")
        @Expose
        private String modifiedAt;
        @SerializedName("scannedBarcodeValue")
        @Expose
        private String scannedBarcodeValue;
        @SerializedName("scannedItemType")
        @Expose
        private String scannedItemType;
        @SerializedName("marketPlaceName")
        @Expose
        private String marketPlaceName;
        @SerializedName("poNumber")
        @Expose
        private String poNumber;
        @SerializedName("vAccountId")
        @Expose
        private String vAccountId;
        @SerializedName("createdByUser")
        @Expose
        private String createdByUser;
        @SerializedName("modifiedByUser")
        @Expose
        private String modifiedByUser;
        @SerializedName("marketplaceCountryName")
        @Expose
        private String marketplaceCountryName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderItemId() {
            return orderItemId;
        }

        public void setOrderItemId(String orderItemId) {
            this.orderItemId = orderItemId;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getVSku() {
            return vSku;
        }

        public void setVSku(String vSku) {
            this.vSku = vSku;
        }

        public String getEan() {
            return ean;
        }

        public void setEan(String ean) {
            this.ean = ean;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getShippingCost() {
            return shippingCost;
        }

        public void setShippingCost(String shippingCost) {
            this.shippingCost = shippingCost;
        }

        public String getItemTax() {
            return itemTax;
        }

        public void setItemTax(String itemTax) {
            this.itemTax = itemTax;
        }

        public String getShippingTax() {
            return shippingTax;
        }

        public void setShippingTax(String shippingTax) {
            this.shippingTax = shippingTax;
        }

        public String getDiscountCode() {
            return discountCode;
        }

        public void setDiscountCode(String discountCode) {
            this.discountCode = discountCode;
        }

        public String getAsin() {
            return asin;
        }

        public void setAsin(String asin) {
            this.asin = asin;
        }

        public String getQtyPurchased() {
            return qtyPurchased;
        }

        public void setQtyPurchased(String qtyPurchased) {
            this.qtyPurchased = qtyPurchased;
        }

        public String getPromotionDiscountAmount() {
            return promotionDiscountAmount;
        }

        public void setPromotionDiscountAmount(String promotionDiscountAmount) {
            this.promotionDiscountAmount = promotionDiscountAmount;
        }

        public String getShippingDiscountAmount() {
            return shippingDiscountAmount;
        }

        public void setShippingDiscountAmount(String shippingDiscountAmount) {
            this.shippingDiscountAmount = shippingDiscountAmount;
        }

        public String getVOrderId() {
            return vOrderId;
        }

        public void setVOrderId(String vOrderId) {
            this.vOrderId = vOrderId;
        }

        public String getOrderStage() {
            return orderStage;
        }

        public void setOrderStage(String orderStage) {
            this.orderStage = orderStage;
        }

        public String getVOrderStage() {
            return vOrderStage;
        }

        public void setVOrderStage(String vOrderStage) {
            this.vOrderStage = vOrderStage;
        }

        public String getTrasactionId() {
            return trasactionId;
        }

        public void setTrasactionId(String trasactionId) {
            this.trasactionId = trasactionId;
        }

        public String getVShippingService() {
            return vShippingService;
        }

        public void setVShippingService(String vShippingService) {
            this.vShippingService = vShippingService;
        }

        public String getStockAllocatedStatus() {
            return stockAllocatedStatus;
        }

        public void setStockAllocatedStatus(String stockAllocatedStatus) {
            this.stockAllocatedStatus = stockAllocatedStatus;
        }

        public Boolean getStockReversal() {
            return stockReversal;
        }

        public void setStockReversal(Boolean stockReversal) {
            this.stockReversal = stockReversal;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedAt() {
            return modifiedAt;
        }

        public void setModifiedAt(String modifiedAt) {
            this.modifiedAt = modifiedAt;
        }

        public String getScannedBarcodeValue() {
            return scannedBarcodeValue;
        }

        public void setScannedBarcodeValue(String scannedBarcodeValue) {
            this.scannedBarcodeValue = scannedBarcodeValue;
        }

        public String getScannedItemType() {
            return scannedItemType;
        }

        public void setScannedItemType(String scannedItemType) {
            this.scannedItemType = scannedItemType;
        }

        public String getMarketPlaceName() {
            return marketPlaceName;
        }

        public void setMarketPlaceName(String marketPlaceName) {
            this.marketPlaceName = marketPlaceName;
        }

        public String getPoNumber() {
            return poNumber;
        }

        public void setPoNumber(String poNumber) {
            this.poNumber = poNumber;
        }

        public String getVAccountId() {
            return vAccountId;
        }

        public void setVAccountId(String vAccountId) {
            this.vAccountId = vAccountId;
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

        public String getMarketplaceCountryName() {
            return marketplaceCountryName;
        }

        public void setMarketplaceCountryName(String marketplaceCountryName) {
            this.marketplaceCountryName = marketplaceCountryName;
        }

    }
}
