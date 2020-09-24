package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.BooleanResponse

class SignUpOrLoginViewModel: ViewModel() {

    private var laurylRepository:LaurylRepository = LaurylRepository()
    private var userExistanceResponse: LiveData<BooleanResponse>

    init {
        userExistanceResponse = laurylRepository.getUserExistanceLiveDta()
    }

    fun getUSerExistanceResponseToObserve(): LiveData<BooleanResponse> {
        return userExistanceResponse
    }

    fun checkUserExistance(phNum: String){
        laurylRepository.chkUserExistance(phNum)
    }

}