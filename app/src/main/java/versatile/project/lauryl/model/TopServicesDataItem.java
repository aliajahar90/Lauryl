
package versatile.project.lauryl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopServicesDataItem {

    @SerializedName("Product Title")
    @Expose
    private String productTitle;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Priority")
    @Expose
    private Integer priority;
    @SerializedName("skuType")
    @Expose
    private String skuType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vSku")
    @Expose
    private String vSku;
    @SerializedName("marketPlaceName")
    @Expose
    private String marketPlaceName;
    @SerializedName("vAccountId")
    @Expose
    private String vAccountId;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("createdAt")
    @Expose
    private Long createdAt;
    @SerializedName("modifiedAt")
    @Expose
    private Long modifiedAt;
    @SerializedName("createdByUser")
    @Expose
    private String createdByUser;
    @SerializedName("modifiedByUser")
    @Expose
    private String modifiedByUser;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVSku() {
        return vSku;
    }

    public void setVSku(String vSku) {
        this.vSku = vSku;
    }

    public String getMarketPlaceName() {
        return marketPlaceName;
    }

    public void setMarketPlaceName(String marketPlaceName) {
        this.marketPlaceName = marketPlaceName;
    }

    public String getVAccountId() {
        return vAccountId;
    }

    public void setVAccountId(String vAccountId) {
        this.vAccountId = vAccountId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Long modifiedAt) {
        this.modifiedAt = modifiedAt;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
