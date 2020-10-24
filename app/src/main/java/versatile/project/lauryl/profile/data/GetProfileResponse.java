package versatile.project.lauryl.profile.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetProfileResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private ProfileData data;

    public ProfileData getProfileData() {
        return data;
    }

    public void setProfileData(ProfileData data) {
        this.data = data;
    }

    public static class ProfileData  implements Serializable {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("password")
        @Expose
        private Object password;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("isVerified")
        @Expose
        private Boolean isVerified;
        @SerializedName("isPrimaryUser")
        @Expose
        private Boolean isPrimaryUser;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;
        @SerializedName("isAdmin")
        @Expose
        private Boolean isAdmin;
        @SerializedName("profilePicUrl")
        @Expose
        private Object profilePicUrl;
        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("expiryDate")
        @Expose
        private Object expiryDate;
        @SerializedName("addressList")
        @Expose
        private List<AddressList> addressList = null;
        @SerializedName("firstTimeLogin")
        @Expose
        private Boolean firstTimeLogin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Boolean getIsVerified() {
            return isVerified;
        }

        public void setIsVerified(Boolean isVerified) {
            this.isVerified = isVerified;
        }

        public Boolean getIsPrimaryUser() {
            return isPrimaryUser;
        }

        public void setIsPrimaryUser(Boolean isPrimaryUser) {
            this.isPrimaryUser = isPrimaryUser;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Boolean getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(Boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        public Object getProfilePicUrl() {
            return profilePicUrl;
        }

        public void setProfilePicUrl(Object profilePicUrl) {
            this.profilePicUrl = profilePicUrl;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Object getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(Object expiryDate) {
            this.expiryDate = expiryDate;
        }

        public List<AddressList> getAddressList() {
            return addressList;
        }

        public void setAddressList(List<AddressList> addressList) {
            this.addressList = addressList;
        }

        public Boolean getFirstTimeLogin() {
            return firstTimeLogin;
        }

        public void setFirstTimeLogin(Boolean firstTimeLogin) {
            this.firstTimeLogin = firstTimeLogin;
        }
    }

    public static class AddressList {

        @SerializedName("createdBy")
        @Expose
        private Object createdBy;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("modifiedBy")
        @Expose
        private Object modifiedBy;
        @SerializedName("modifiedAt")
        @Expose
        private String modifiedAt;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("createdByUser")
        @Expose
        private Object createdByUser;
        @SerializedName("modifiedByUser")
        @Expose
        private Object modifiedByUser;
        @SerializedName("sellerId")
        @Expose
        private Object sellerId;
        @SerializedName("vAccountId")
        @Expose
        private Object vAccountId;
        @SerializedName("marketPlaceName")
        @Expose
        private Object marketPlaceName;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("address1")
        @Expose
        private String address1;
        @SerializedName("address2")
        @Expose
        private String address2;
        @SerializedName("city")
        @Expose
        private Object city;
        @SerializedName("state")
        @Expose
        private Object state;
        @SerializedName("country")
        @Expose
        private Object country;
        @SerializedName("pinCode")
        @Expose
        private Object pinCode;
        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Object modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedAt() {
            return modifiedAt;
        }

        public void setModifiedAt(String modifiedAt) {
            this.modifiedAt = modifiedAt;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Object getCreatedByUser() {
            return createdByUser;
        }

        public void setCreatedByUser(Object createdByUser) {
            this.createdByUser = createdByUser;
        }

        public Object getModifiedByUser() {
            return modifiedByUser;
        }

        public void setModifiedByUser(Object modifiedByUser) {
            this.modifiedByUser = modifiedByUser;
        }

        public Object getSellerId() {
            return sellerId;
        }

        public void setSellerId(Object sellerId) {
            this.sellerId = sellerId;
        }

        public Object getVAccountId() {
            return vAccountId;
        }

        public void setVAccountId(Object vAccountId) {
            this.vAccountId = vAccountId;
        }

        public Object getMarketPlaceName() {
            return marketPlaceName;
        }

        public void setMarketPlaceName(Object marketPlaceName) {
            this.marketPlaceName = marketPlaceName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public Object getPinCode() {
            return pinCode;
        }

        public void setPinCode(Object pinCode) {
            this.pinCode = pinCode;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

    }

}
