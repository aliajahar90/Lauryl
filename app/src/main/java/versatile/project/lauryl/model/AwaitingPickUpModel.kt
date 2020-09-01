package versatile.project.lauryl.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AwaitingPickUpModel {

    @SerializedName("Code")
    @Expose
    var orderIdVal: String? = null

    @SerializedName("Code")
    @Expose
    var date: String? = null

    @SerializedName("Code")
    @Expose
    var time: String? = null

    @SerializedName("Code")
    @Expose
    var pickUpAddress: String? = null

}