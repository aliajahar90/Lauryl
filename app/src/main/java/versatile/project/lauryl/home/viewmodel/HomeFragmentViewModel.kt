package versatile.project.lauryl.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import versatile.project.lauryl.base.SingleLiveEvent
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.home.HomeRepository
import versatile.project.lauryl.model.MyOrdersDataItem
import versatile.project.lauryl.model.TopServicesResponse

class HomeFragmentViewModel: ViewModel()  {
    private var laurylRepository: HomeRepository = HomeRepository()
    private var topServicesResponse: LiveData<TopServicesResponse> = laurylRepository.getTopServicesLiveDta()

    fun geTopServicesToObserve(): LiveData<TopServicesResponse> {
        return topServicesResponse
    }

    fun getTopServices(accessToken:String,inputJsonObj: JsonObject){
        laurylRepository.getTopServices(accessToken,inputJsonObj)
    }

    fun getTopServicesData():String{

        if(topServicesResponse == null || topServicesResponse.value == null){
            return ""
        }
        return topServicesResponse.value!!.getData().totalCount.toString()

    }
    fun getHomeOrder(accessToken: String,jsonObject: JsonObject){
        laurylRepository.getHomeOrders(accessToken,jsonObject)
    }
    fun getAwaitingPickupList(): LiveData<List<MyOrdersDataItem>>? {
        return laurylRepository.listAwatingPickupSingleLiveEvent
    }
    fun getAwaitingDeliveryList(): LiveData<List<MyOrdersDataItem>>? {
        return laurylRepository.listAwatingDeliverySingleLiveEvent
    }
    fun getCompletedDeliveryList(): LiveData<List<MyOrdersDataItem>>? {
        return laurylRepository.listCompletedDeliverySingleLiveEvent
    }
    fun getMyOrderFetchError(): LiveData<String>? {
        return laurylRepository.orderFetchErrorSingleLiveEvent;
    }

}