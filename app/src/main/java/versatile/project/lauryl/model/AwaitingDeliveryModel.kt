package versatile.project.lauryl.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AwaitingDeliveryModel(var orderIdVal: String,var date: String,var time: String,var pickUpAddress: String,var dataItem: MyOrdersDataItem) : Serializable{

}