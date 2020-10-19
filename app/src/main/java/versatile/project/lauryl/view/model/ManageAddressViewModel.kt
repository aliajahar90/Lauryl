package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.address.AddressModel

class ManageAddressViewModel: ViewModel() {

    private var laurylRepository:LaurylRepository = LaurylRepository()

    private var addressLiveData: LiveData<ArrayList<AddressModel>>


    init {
        addressLiveData = laurylRepository.addressLiveData
    }

    fun getAddressesToObserve():LiveData<ArrayList<AddressModel>>{
        return addressLiveData
    }

    fun getAddress(access:String,number:String){
        laurylRepository.getAddresses(access,number)
    }



}