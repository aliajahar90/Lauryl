package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.OtpResponse
import versatile.project.lauryl.model.BooleanResponse

class ResetPasswordViewModel: ViewModel() {

    private var laurylRepository:LaurylRepository = LaurylRepository()
    private var resetPasswordResponse: LiveData<OtpResponse>

    init {
        resetPasswordResponse = laurylRepository.getResetPasswordLiveDta()
    }

    fun getResetPswrdToObserve(): LiveData<OtpResponse> {
        return resetPasswordResponse
    }

    fun resetPassword(inputJsonObj:JsonObject){
        laurylRepository.resetPassword(inputJsonObj)
    }

}