package versatile.project.lauryl.model.address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddressModel {
    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Int? = null

    @SerializedName("modifiedBy")
    @Expose
    var modifiedBy: Any? = null

    @SerializedName("modifiedAt")
    @Expose
    var modifiedAt: Int? = null

    @SerializedName("isDeleted")
    @Expose
    var isDeleted: Boolean? = null

    @SerializedName("createdByUser")
    @Expose
    var createdByUser: Any? = null

    @SerializedName("modifiedByUser")
    @Expose
    var modifiedByUser: Any? = null

    @SerializedName("sellerId")
    @Expose
    var sellerId: Any? = null

    @SerializedName("vAccountId")
    @Expose
    var vAccountId: Any? = null

    @SerializedName("marketPlaceName")
    @Expose
    var marketPlaceName: Any? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("addresType")
    @Expose
    var addresType: String? = null

    @SerializedName("address1")
    @Expose
    var address1: String? = null

    @SerializedName("streetName")
    @Expose
    var streetName: String? = null

    @SerializedName("landmark")
    @Expose
    var landmark: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("pinCode")
    @Expose
    var pinCode: String? = null

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: Int? = null

}