package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.AddressModel
import versatile.project.lauryl.model.OtpResponse
import versatile.project.lauryl.model.BooleanResponse

class ChangeAddressViewModel: ViewModel() {

    private var laurylRepository:LaurylRepository = LaurylRepository()

    private var citiesLiveData: LiveData<ArrayList<String>>
    private var statesLiveData: LiveData<ArrayList<String>>
    private var addressLiveData: LiveData<ArrayList<AddressModel>>


    init {
        citiesLiveData = laurylRepository.citiesLiveData
        statesLiveData = laurylRepository.statesLiveData
        addressLiveData = laurylRepository.addressLiveData

    }

    fun getCitiesToObserve(): LiveData<ArrayList<String>>{
        return citiesLiveData
    }
    fun getStatesToObserve(): LiveData<ArrayList<String>>{
        return statesLiveData
    }
    fun getAddressesToObserve():LiveData<ArrayList<AddressModel>>{
        return addressLiveData
    }

    fun getCities(){
        laurylRepository.getCities()
    }
    fun getStates(){
        laurylRepository.getStates()
    }
    fun getAddress(){
        laurylRepository.getAddresses()
    }



}