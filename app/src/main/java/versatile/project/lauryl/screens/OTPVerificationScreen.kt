package versatile.project.lauryl.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_otpverification_screen.*
import versatile.project.lauryl.R
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import versatile.project.lauryl.interfaces.OnRegistrationCallback
import versatile.project.lauryl.utils.*
import versatile.project.lauryl.view.model.OtpVerificationViewModel

class OTPVerificationScreen : AppCompatActivity() {

    lateinit var otpVerificationViewModel:OtpVerificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification_screen)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        otpVerificationViewModel = ViewModelProvider(this).get(OtpVerificationViewModel::class.java)
        val mblNum = intent.getStringExtra(Constants.MOBILE_NUMBER) as String
        val first2Chars = "${mblNum.substring(0,2)}"
        val last2Chars = "${mblNum.substring(8,10)}"
        mblNumTxt.text = "${getString(R.string.otp_txt)} ${first2Chars}xxxxxx${last2Chars}"
        verifyBtn.setOnClickListener{

            val otpOneTxt = otpOneEdt.text.toString()
            val otpTwoTxt = otpTwoEdt.text.toString()
            val otpThreeTxt = otpThreeEdt.text.toString()
            val otpFourTxt = otpFourEdt.text.toString()

            if(otpOneTxt.isEmpty() || otpTwoTxt.isEmpty() || otpThreeTxt.isEmpty() || otpFourTxt.isEmpty()){
                Globals.showPopoUpDialog(this,getString(R.string.otp_validation),getString(R.string.otp_empty_disclaimer))
            } else{
                val phnNum = intent.getStringExtra(Constants.MOBILE_NUMBER)
                val otpString = "${otpOneTxt}${otpTwoTxt}${otpThreeTxt}${otpFourTxt}"
                val inputJson = JsonObject()
                inputJson.addProperty("phoneNumber",phnNum)
                inputJson.addProperty("otp",otpString)
                otpVerificationViewModel.checkOtp(inputJson)
            }

        }

        /*otpOneEdt.transformationMethod = BiggerDotPasswordTransformationMethod()
        otpTwoEdt.transformationMethod = BiggerDotPasswordTransformationMethod()
        otpThreeEdt.transformationMethod = BiggerDotPasswordTransformationMethod()
        otpFourEdt.transformationMethod = BiggerDotPasswordTransformationMethod()*/
        showKeyboard()
        otpOneEdt.addTextChangedListener(MyEditTextWatcher(this@OTPVerificationScreen,otpOneEdt,otpTwoEdt))
        otpTwoEdt.addTextChangedListener(MyEditTextWatcher(this@OTPVerificationScreen,otpTwoEdt,otpThreeEdt))
        otpThreeEdt.addTextChangedListener(MyEditTextWatcher(this@OTPVerificationScreen,otpThreeEdt,otpFourEdt))
        otpFourEdt.addTextChangedListener(MyEditTextWatcher(this@OTPVerificationScreen,otpFourEdt,otpFourEdt))
        observeDataSources()
        val otpSourceType = intent.getSerializableExtra(Constants.FORGOT_PASSWORD_SOURCE_TYPE) as EnumOTPSource
        if(otpSourceType == EnumOTPSource.ENUM_TYPE_REGISTRATION){
            generateOtp()
        }

    }

    private fun generateOtp() {
        val phnNum = intent.getStringExtra(Constants.MOBILE_NUMBER)
        otpVerificationViewModel.generateOtp(phnNum)
    }

    private fun observeDataSources() {

        otpVerificationViewModel.getOtpResponseToObserve().observe(this, Observer {
            if(it.data != null){

                Log.d("otp_","${it.data}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Globals.showNotifInOreoAll(this,"${it.data}")
                } else {
                    Globals.sendNotification(this,"${it.data}")
                }

            }else{
                Globals.showPopoUpDialog(this,getString(R.string.otp_validation),getString(R.string.otp_not_sent))
            }
        })

        otpVerificationViewModel.getChkOtpResponseToObserve().observe(this, Observer {

            if(it.status){

                val otpSourceType = intent.getSerializableExtra(Constants.FORGOT_PASSWORD_SOURCE_TYPE) as EnumOTPSource
                if(otpSourceType == EnumOTPSource.ENUM_TYPE_REGISTRATION){

                    val usertNameTxt = intent.getStringExtra(Constants.MOBILE_NUMBER)
                    val pswrdTxt = intent.getStringExtra(Constants.PASSWORD)
                    val phoneNumTxt = intent.getStringExtra(Constants.MOBILE_NUMBER).toLong()
                    val firstNameTxt = intent.getStringExtra(Constants.FIRST_NAME)
                    val lastNameTxt = intent.getStringExtra(Constants.LAST_NAME)
                    val inputJson = JsonObject()
                    inputJson.addProperty("userName",usertNameTxt)
                    inputJson.addProperty("password",pswrdTxt)
                    inputJson.addProperty("phoneNumber",phoneNumTxt)
                    inputJson.addProperty("firstName",firstNameTxt)
                    inputJson.addProperty("lastName",lastNameTxt)
                    otpVerificationViewModel.registerUser(inputJson)

                }else if(otpSourceType == EnumOTPSource.ENUM_TYPE_FORGOT_PASSWORD){
                    var navtToRestPswrdIntent = Intent(this@OTPVerificationScreen,ResetPasswordScreen::class.java)
                    navtToRestPswrdIntent.putExtra(Constants.MOBILE_NUMBER,intent.getStringExtra(Constants.MOBILE_NUMBER))
                    startActivity(navtToRestPswrdIntent)
                    finish()
                }

            }else{
                Globals.showPopoUpDialog(this,getString(R.string.otp_validation),getString(R.string.otp_mismatch_disclaimer))
                otpOneEdt.setText("")
                otpTwoEdt.setText("")
                otpThreeEdt.setText("")
                otpFourEdt.setText("")
            }
        })

        otpVerificationViewModel.getRegisterUserResponseToObserve().observe(this, Observer {
            if(it.status){
                Globals.showPopoUpDialog(this,getString(R.string.registration),getString(R.string.user_register_successful),true,object : OnRegistrationCallback{
                    override fun userRegisteredSuccessfully() {
                        startActivity(Intent(this@OTPVerificationScreen,SignUpOrLoginScreen::class.java))
                        finish()
                    }
                })
            }else{
                Globals.showPopoUpDialog(this,getString(R.string.otp_validation),getString(R.string.user_register_unsuccessful))
            }
        })

    }

    fun showKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(otpOneEdt, SHOW_IMPLICIT)
    }

    fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(otpFourEdt.windowToken, 0)
    }

}
