package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import timber.log.Timber
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.AwaitingCompleteModel
import versatile.project.lauryl.model.AwaitingDeliveryModel
import versatile.project.lauryl.model.AwaitingPickUpModel
import versatile.project.lauryl.model.MyOrdersResponse

class MyOrdersViewModel:ViewModel() {

    private var laurylRepository: LaurylRepository = LaurylRepository()
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
        Timber.e("Adding pickup")
        this.awaitingPckUpDtaLst.add(awaitingPickUpModel)
    }

    fun clearAwaitingPckUpDta(){
        this.awaitingPckUpDtaLst.clear()
    }

    fun addAwaitingDlvryDta(awaitingPickUpDlvryModel: AwaitingDeliveryModel){
        Timber.e("Adding delivey")

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

}