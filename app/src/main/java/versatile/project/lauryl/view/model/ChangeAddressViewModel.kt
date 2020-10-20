package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import timber.log.Timber
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.BooleanResponse
import versatile.project.lauryl.model.address.AddressModel
import versatile.project.lauryl.model.city.CitiResponse
import versatile.project.lauryl.model.city.CityModel

class ChangeAddressViewModel : ViewModel() {

    private var laurylRepository: LaurylRepository = LaurylRepository()

    private var citiesLiveData: LiveData<ArrayList<CityModel>>
    private var statesLiveData: LiveData<ArrayList<String>>
    private var addressLiveData: LiveData<ArrayList<AddressModel>>
     var saveAddressLiveData: LiveData<BooleanResponse>

    init {
        citiesLiveData = laurylRepository.citiesLiveData
        statesLiveData = laurylRepository.statesLiveData
        addressLiveData = laurylRepository.addressLiveData
        saveAddressLiveData=laurylRepository.saveAddressLiveData

    }

    fun getCitiesToObserve(): LiveData<ArrayList<CityModel>> {
        return citiesLiveData
    }

    fun getStatesToObserve(): LiveData<ArrayList<String>> {
        return statesLiveData
    }

    fun getAddressesToObserve(): LiveData<ArrayList<AddressModel>> {
        return addressLiveData
    }

    fun getCities(access: String) {
        laurylRepository.getCities(access)
    }

    fun getStates() {
        laurylRepository.getStates()
    }

    fun getAddress(access: String, number: String) {
        laurylRepository.getAddresses(access, number)
    }

    fun saveAddress(access: String, jsonObject: JsonObject) {
        Timber.e("saving address")

        laurylRepository.saveAddress(accessToken = access, address = jsonObject)
    }


}