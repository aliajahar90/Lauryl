package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.BooleanResponse
import versatile.project.lauryl.model.address.AddressModel

class ManageAddressViewModel : ViewModel() {

    private var laurylRepository: LaurylRepository = LaurylRepository()

    private var addressLiveData: LiveData<ArrayList<AddressModel>>
     var deleteObserver: LiveData<BooleanResponse>


    init {
        addressLiveData = laurylRepository.addressLiveData
        deleteObserver = laurylRepository.deleteAddressLiveData
    }

    fun getAddressesToObserve(): LiveData<ArrayList<AddressModel>> {
        return addressLiveData
    }

    fun getAddress(access: String, number: String) {
        laurylRepository.getAddresses(access, number)
    }

    fun deleteAddress(accessToken: String, id: String) {
        laurylRepository.deleteAddress(accessToken, id)
    }

}