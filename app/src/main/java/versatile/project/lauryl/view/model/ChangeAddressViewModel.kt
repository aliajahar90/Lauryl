package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.address.AddressModel

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
    fun getAddress(access:String,number:String){
        Timber.e("number $number")
        laurylRepository.getAddresses(access,"7382129781")
    }



}