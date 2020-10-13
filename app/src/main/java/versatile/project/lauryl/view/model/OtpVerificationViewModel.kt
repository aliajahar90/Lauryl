package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.OtpResponse
import versatile.project.lauryl.model.BooleanResponse

class OtpVerificationViewModel: ViewModel() {

    private var laurylRepository:LaurylRepository = LaurylRepository()
    private var otpResponse: LiveData<OtpResponse>
    private var chkOtpResponse: LiveData<BooleanResponse>
    private var registerUserResponse: LiveData<BooleanResponse>


    init {
        otpResponse = laurylRepository.getOtpLiveDta()
        chkOtpResponse = laurylRepository.getChkOtpLiveDta()
        registerUserResponse= laurylRepository.getregisterUserLiveDta()
    }

    fun getOtpResponseToObserve(): LiveData<OtpResponse> {
        return otpResponse
    }

    fun getChkOtpResponseToObserve(): LiveData<BooleanResponse> {
        return chkOtpResponse
    }

    fun getRegisterUserResponseToObserve(): LiveData<BooleanResponse> {
        return registerUserResponse
    }

    fun generateOtp(phNum: String){
        laurylRepository.generateOtp(phNum)
    }

    fun checkOtp(inputJsonObj:JsonObject){
        laurylRepository.checkOtp(inputJsonObj)
    }

    fun registerUser(inputJsonObj: JsonObject){
        laurylRepository.registerUser(inputJsonObj)
    }

}