package versatile.project.lauryl.screens

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_forgot_password_screen.*
import versatile.project.lauryl.R
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.utils.EnumOTPSource
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.ForgotPasswordViewModel

class ForgotPasswordScreen : AppCompatActivity() {

    lateinit var forgotPswrdViewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_screen)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        forgotPswrdViewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        sendBtn.setOnClickListener {

            val mblNumberTxt = mblNumberEdt.text.toString()
            if(mblNumberTxt == null || mblNumberTxt.isEmpty()){
                Globals.showPopoUpDialog(this,getString(R.string.validation),getString(R.string.mbl_valid_num_txt))
                return@setOnClickListener
            }

            generateOtpToResetPswrd(mblNumberTxt)

        }
        observeDataSources()

    }

    private fun generateOtpToResetPswrd(mblNumberTxt: String) {
        forgotPswrdViewModel.generateOtp(mblNumberTxt)
    }

    private fun observeDataSources() {

        forgotPswrdViewModel.getOtpResponseToObserve().observe(this, Observer {
            if(it.data != null){

                Log.d("otp_","${it.data}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Globals.showNotifInOreoAll(this,"${it.data}")
                } else {
                    Globals.sendNotification(this,"${it.data}")
                }

                var navToOtpVerfyIntent = Intent(this@ForgotPasswordScreen,OTPVerificationScreen::class.java)
                navToOtpVerfyIntent.putExtra(Constants.FORGOT_PASSWORD_SOURCE_TYPE,EnumOTPSource.ENUM_TYPE_FORGOT_PASSWORD)
                navToOtpVerfyIntent.putExtra(Constants.MOBILE_NUMBER,mblNumberEdt.text.toString())
                startActivity(navToOtpVerfyIntent)
                finish()

            }else{
                Globals.showPopoUpDialog(this,getString(R.string.otp_validation),getString(R.string.otp_not_sent))
            }

        })
    }

}
