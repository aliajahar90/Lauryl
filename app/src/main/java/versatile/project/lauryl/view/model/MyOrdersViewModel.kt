package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import timber.log.Timber
import versatile.project.lauryl.base.SingleLiveEvent
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.*
import versatile.project.lauryl.orders.OrderRepository

class MyOrdersViewModel:ViewModel() {

    private var laurylRepository: OrderRepository = OrderRepository()
    private var myOrdersResponse: LiveData<MyOrdersResponse> = laurylRepository.getMyOrdersLiveDta()
    var awaitingPckUpDtaLst:ArrayList<AwaitingPickUpModel> = ArrayList()
    var awaitingPckUpDevryDtaLst:ArrayList<AwaitingDeliveryModel> = ArrayList()
    var awaitingPckUpCompletedDtaLst:ArrayList<AwaitingCompleteModel> = ArrayList()

    fun getMyOrdersResponseToObserver():LiveData<MyOrdersResponse>{
        return myOrdersResponse
    }

    fun getMyOrders(accessToken:String,inputJsonObj: JsonObject){
        laurylRepository.getMyOrders(accessToken,inputJsonObj)
    }

    fun addAwaitingPckUpDta(awaitingPickUpModel: AwaitingPickUpModel){
        this.awaitingPckUpDtaLst.add(awaitingPickUpModel)
    }

    fun clearAwaitingPckUpDta(){
        this.awaitingPckUpDtaLst.clear()
    }

    fun addAwaitingDlvryDta(awaitingPickUpDlvryModel: AwaitingDeliveryModel){
        this.awaitingPckUpDevryDtaLst.add(awaitingPickUpDlvryModel)
    }

    fun clearAwaitingPckUpDlvryDta(){
        this.awaitingPckUpDevryDtaLst.clear()
    }

    fun addAwaitingPckUpCmpltdDta(awaitingPckUpCmpltdModel: AwaitingCompleteModel){
        Timber.e("Adding completed")

        this.awaitingPckUpCompletedDtaLst.add(awaitingPckUpCmpltdModel)
    }

    fun clearAwaitingPckUpCmpltdDta(){
        this.awaitingPckUpCompletedDtaLst.clear()
    }
    fun cancelOrder(accessToken: String,jsonObject: JsonObject){
        laurylRepository.cancelOrder(accessToken,jsonObject)
    }
    fun cancelOrderSuccessObserver():SingleLiveEvent<BooleanResponse>
    {
        return laurylRepository.cancelOrderSuccessEvent
    }
    fun cancelOrderErrorObserver():SingleLiveEvent<String>
    {
        return laurylRepository.cancelOrderErrorEvent
    }

}