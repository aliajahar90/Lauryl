package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.OtpResponse
import versatile.project.lauryl.model.BooleanResponse

class ForgotPasswordViewModel: ViewModel() {

    private var laurylRepository:LaurylRepository = LaurylRepository()
    private var requestOtpForResetPasswordResponse: LiveData<OtpResponse>

    init {
        requestOtpForResetPasswordResponse = laurylRepository.getrequestOtpForResetPasswordLiveDta()
    }

    fun getOtpResponseToObserve(): LiveData<OtpResponse> {
        return requestOtpForResetPasswordResponse
    }

    fun generateOtp(phNum: String){
        laurylRepository.generateOtpToResetPswrd(phNum)
    }

}