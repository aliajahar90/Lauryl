package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.TopServicesResponse

class SchedulePickUpFragmentViewModel: ViewModel()  {

    private var laurylRepository: LaurylRepository = LaurylRepository()
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

}