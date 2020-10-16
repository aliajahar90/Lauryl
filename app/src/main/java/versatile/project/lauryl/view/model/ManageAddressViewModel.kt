package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.AddressModel
import versatile.project.lauryl.model.OtpResponse
import versatile.project.lauryl.model.BooleanResponse

class ManageAddressViewModel: ViewModel() {

    private var laurylRepository:LaurylRepository = LaurylRepository()

    private var addressLiveData: LiveData<ArrayList<AddressModel>>


    init {
        addressLiveData = laurylRepository.addressLiveData
    }

    fun getAddressesToObserve():LiveData<ArrayList<AddressModel>>{
        return addressLiveData
    }

    fun getAddress(){
        laurylRepository.getAddresses()
    }



}