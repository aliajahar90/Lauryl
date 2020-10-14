package versatile.project.lauryl.screens

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_reset_password_screen.*
import versatile.project.lauryl.R
import versatile.project.lauryl.interfaces.OnRegistrationCallback
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.OtpVerificationViewModel
import versatile.project.lauryl.view.model.ResetPasswordViewModel

class ResetPasswordScreen : AppCompatActivity() {

    lateinit var resetPswrdViewModel: ResetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password_screen)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        resetPswrdViewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)

        sendBtn.setOnClickListener {

            val newPswrdTxt = newPswrdEdt.text.toString()
            val newCnfPswrdTxt = newPswrdEdt.text.toString()

            if(newPswrdTxt == null || newPswrdTxt.isEmpty()){
                Globals.showPopoUpDialog(this,getString(R.string.validation),getString(R.string.enter_psrd_num_txt))
                return@setOnClickListener
            }

            if(newCnfPswrdTxt == null || newCnfPswrdTxt.isEmpty()){
                Globals.showPopoUpDialog(this,getString(R.string.validation),getString(R.string.enter_cnf_psrd_num_txt))
                return@setOnClickListener
            }

            if(!newPswrdTxt.contentEquals(newCnfPswrdTxt)){
                Globals.showPopoUpDialog(this,getString(R.string.validation),getString(R.string.pswrd_mismatch_txt))
                return@setOnClickListener
            }

            val inputJson = JsonObject()
            inputJson.addProperty("phoneNumber",intent.getStringExtra(Constants.MOBILE_NUMBER))
            inputJson.addProperty("password",newPswrdTxt)
            resetPswrdViewModel.resetPassword(inputJson)

        }

        observeDataSources()
    }

    private fun observeDataSources() {

        resetPswrdViewModel.getResetPswrdToObserve().observe(this, Observer {
            if(it.data != null){

                Globals.showPopoUpDialog(this,getString(R.string.reset_pswrd_hdng_txt),"${it.data}",true,object :OnRegistrationCallback{

                    override fun userRegisteredSuccessfully() {
                        var navtToRestPswrdIntent = Intent(this@ResetPasswordScreen,SignUpOrLoginScreen::class.java)
                        navtToRestPswrdIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                        navtToRestPswrdIntent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(navtToRestPswrdIntent)
                        finish()
                    }

                })

            }else{
                Globals.showPopoUpDialog(this,getString(R.string.otp_validation),getString(R.string.otp_not_sent))
            }
        })

    }

}