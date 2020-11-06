package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import versatile.project.lauryl.base.SingleLiveEvent
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.TopServicesResponse
import versatile.project.lauryl.profile.data.GetProfileResponse
import versatile.project.lauryl.services.ServiceModel
import versatile.project.lauryl.services.ServicesRepository

class SchedulePickUpFragmentViewModel: ViewModel()  {

    private var laurylRepository: ServicesRepository = ServicesRepository()
    private var topServicesResponse: SingleLiveEvent<List<ServiceModel>> = laurylRepository.listServiceModelSingleLiveEvent

    fun geTopServicesToObserve(): SingleLiveEvent<List<ServiceModel>> {
        return topServicesResponse
    }

    fun getTopServices(accessToken:String,inputJsonObj: JsonObject){
        laurylRepository.getAllServices(accessToken,inputJsonObj)
    }

    fun getProfileInformation(accessToken: String){
        return laurylRepository.getProfileInformation(accessToken);
    }
    fun profileFetchSuccessHandler(): SingleLiveEvent<GetProfileResponse> {
        return laurylRepository.getProfileResponseLiveData
    }

    fun profileFetchErrorHandler(): LiveData<Throwable?>? {
        return laurylRepository.throwableMutableLiveData
    }
//    fun getTopServicesData():String{
//
//        if(topServicesResponse == null || topServicesResponse.value == null){
//            return ""
//        }
//        return topServicesResponse.value!!.getData().totalCount.toString()
//
//    }

}