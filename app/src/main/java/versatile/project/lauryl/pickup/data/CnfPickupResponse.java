package versatile.project.lauryl.pickup.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CnfPickupResponse implements Serializable {
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
        private String totalCount;
        @SerializedName("list")
        @Expose
        private java.util.List<DateTimeList> list = null;

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public java.util.List<DateTimeList> getList() {
            return list;
        }

        public void setList(java.util.List<DateTimeList> list) {
            this.list = list;
        }

    }

    public class DateTimeList {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("pickUpDate")
        @Expose
        private String pickUpDate;
        @SerializedName("pickUpTime")
        @Expose
        private String pickUpTime;
        @SerializedName("noOfSlots")
        @Expose
        private String noOfSlots;
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
        @SerializedName("sellerId")
        @Expose
        private String sellerId;
        @SerializedName("vAccountId")
        @Expose
        private String vAccountId;
        @SerializedName("marketPlaceName")
        @Expose
        private String marketPlaceName;
        @SerializedName("createdByUser")
        @Expose
        private String createdByUser;
        @SerializedName("modifiedByUser")
        @Expose
        private String modifiedByUser;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPickUpDate() {
            return pickUpDate;
        }

        public void setPickUpDate(String pickUpDate) {
            this.pickUpDate = pickUpDate;
        }

        public String getPickUpTime() {
            return pickUpTime;
        }

        public void setPickUpTime(String pickUpTime) {
            this.pickUpTime = pickUpTime;
        }

        public String getNoOfSlots() {
            return noOfSlots;
        }

        public void setNoOfSlots(String noOfSlots) {
            this.noOfSlots = noOfSlots;
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

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
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

    }
}
