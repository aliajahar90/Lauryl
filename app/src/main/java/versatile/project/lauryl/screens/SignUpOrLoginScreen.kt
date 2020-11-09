package versatile.project.lauryl.screens

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sign_up_or_login_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import versatile.project.lauryl.BuildConfig
import versatile.project.lauryl.R
import versatile.project.lauryl.application.MyApplication
import versatile.project.lauryl.base.BaseActivity
import versatile.project.lauryl.model.ErrorResponse
import versatile.project.lauryl.model.LoginResponse
import versatile.project.lauryl.model.VersatileLoginResponse
import versatile.project.lauryl.network.api.RetrofitObj
import versatile.project.lauryl.utils.Constants
import versatile.project.lauryl.utils.EnumOTPSource
import versatile.project.lauryl.utils.Globals
import versatile.project.lauryl.view.model.SignUpOrLoginViewModel


class SignUpOrLoginScreen : BaseActivity() {

    lateinit var signUpOrLoginViewModel: SignUpOrLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_or_login_screen)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        signUpOrLoginViewModel = ViewModelProvider(this).get(SignUpOrLoginViewModel::class.java)

        loginTxtSelectedLyot.setOnClickListener {
            selectedTabHdngTxt.text = getString(R.string.plz_login_txt)
            loginTxtUnSelectedLyot.visibility = View.GONE
            registerTxtSelectedLyot.visibility = View.GONE
            registerTxtUnSelectedLyot.visibility = View.VISIBLE
            selectedLogin()
        }

        loginTxtUnSelectedLyot.setOnClickListener {
            selectedTabHdngTxt.text = getString(R.string.plz_login_txt)
            loginTxtUnSelectedLyot.visibility = View.GONE
            loginTxtSelectedLyot.visibility = View.VISIBLE
            registerTxtSelectedLyot.visibility = View.GONE
            registerTxtUnSelectedLyot.visibility = View.VISIBLE
            selectedLogin()
        }

        registerTxtSelectedLyot.setOnClickListener {
            selectedTabHdngTxt.text = getString(R.string.plz_reg_txt)
            registerTxtUnSelectedLyot.visibility = View.GONE
            loginTxtSelectedLyot.visibility = View.GONE
            loginTxtUnSelectedLyot.visibility = View.VISIBLE
            selectedRegister()
        }

        registerTxtUnSelectedLyot.setOnClickListener {
            selectedTabHdngTxt.text = getString(R.string.plz_reg_txt)
            registerTxtUnSelectedLyot.visibility = View.GONE
            registerTxtSelectedLyot.visibility = View.VISIBLE
            loginTxtSelectedLyot.visibility = View.GONE
            loginTxtUnSelectedLyot.visibility = View.VISIBLE
            selectedRegister()
        }

        loginOrRegisterBtn.setOnClickListener {

            if (firstNamEdt.visibility == View.GONE) {

                // Login selected
                val mblNumEdtVal = mblNumEdt.text.toString()
                val pswrdEdtVal = pswrdEdt.text.toString()

                if (mblNumEdtVal == null || mblNumEdtVal.isEmpty()) {
                    //Globals.showPopoUpDialog(this,getString(R.string.mbl_valid_num_txt))
                    Globals.showPopoUpDialog(
                        this@SignUpOrLoginScreen, getString(R.string.validation), getString(
                            R.string.mbl_valid_num_txt
                        )
                    )
                    return@setOnClickListener
                }

                if (pswrdEdtVal == null || pswrdEdtVal.isEmpty()) {
                    //Globals.showToastMsg(this,getString(R.string.mbl_valid_pswrd_txt))
                    Globals.showPopoUpDialog(
                        this@SignUpOrLoginScreen, getString(R.string.validation), getString(
                            R.string.mbl_valid_pswrd_txt
                        )
                    )
                    return@setOnClickListener
                }

                loginOrRegisterBtn.visibility = View.GONE
                progressLyot.visibility = View.VISIBLE

                RetrofitObj.getApiObj()!!.executeLogin(
                    getString(R.string.grant_type_txt),
                    mblNumEdtVal,
                    pswrdEdtVal,
                    BuildConfig.VERSION_CODE.toString()
                ).enqueue(object : Callback<LoginResponse> {

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        if (response?.body() != null) {
                            response.body()

                            Globals.saveStringToPreferences(
                                applicationContext,
                                Constants.USER_AUTH_TOKEN,
                                response.body()!!.accessToken
                            )
                            (application as MyApplication).userAccessToken =
                                response.body()!!.accessToken
                        }
                        progressLyot.visibility = View.GONE
                        loginOrRegisterBtn.visibility = View.VISIBLE

                        if (response != null) {

                            if (response.isSuccessful) {
                                loginOrRegisterBtn.visibility = View.GONE
                                progressLyot.visibility = View.VISIBLE

                                RetrofitObj.getVersatileApiObj()!!.executeVersatileLogin(
                                    getString(R.string.grant_type_txt), getString(
                                        R.string.versatile_user_name_txt
                                    ), getString(R.string.versatile_pswrd_txt)
                                ).enqueue(object : Callback<VersatileLoginResponse> {
                                    override fun onResponse(
                                        call: Call<VersatileLoginResponse>,
                                        response: Response<VersatileLoginResponse>
                                    ) {

                                        progressLyot.visibility = View.GONE
                                        loginOrRegisterBtn.visibility = View.VISIBLE

                                        if (response != null) {

                                            if (response.isSuccessful) {
                                                Globals.saveStringToPreferences(
                                                    applicationContext,
                                                    Constants.MOBILE_NUMBER,
                                                    mblNumEdtVal
                                                )
                                                Globals.saveStringToPreferences(
                                                    applicationContext,
                                                    Constants.AUTH_TOKEN,
                                                    response.body()!!.accessToken
                                                )
                                                (application as MyApplication).accessToken =
                                                    response.body()!!.accessToken
                                                (application as MyApplication).mobileNumber =
                                                    mblNumEdtVal
                                                startActivity(
                                                    Intent(
                                                        this@SignUpOrLoginScreen,
                                                        HomeScreen::class.java
                                                    )
                                                )
                                                finish()
//                                                Globals.showPopoUpDialog(this@SignUpOrLoginScreen,
//                                                    getString(R.string.lauryl),
//                                                    getString(
//                                                        R.string.user_login_successful
//                                                    ),
//                                                    true,
//                                                    object :
//                                                        OnRegistrationCallback {
//                                                        override fun userRegisteredSuccessfully() {
//
//                                                        }
//                                                    })

                                            } else {
//                                                val errorResponse = Gson().fromJson(
//                                                    response.errorBody()!!.charStream(),
//                                                    ErrorResponse::class.java
//                                                )
                                                Globals.showPopoUpDialog(
                                                    this@SignUpOrLoginScreen,
                                                    getString(R.string.login_fail_hdng_txt),
                                                    getString(
                                                        R.string.login_error
                                                    )
                                                )
                                            }

                                        } else {
                                            Globals.showPopoUpDialog(
                                                this@SignUpOrLoginScreen,
                                                getString(R.string.login_fail_hdng_txt),
                                                getString(
                                                    R.string.server_error_txt
                                                )
                                            )
                                        }

                                    }

                                    override fun onFailure(
                                        call: Call<VersatileLoginResponse>,
                                        t: Throwable
                                    ) {
                                        progressLyot.visibility = View.GONE
                                        loginOrRegisterBtn.visibility = View.VISIBLE
                                        Globals.showPopoUpDialog(
                                            this@SignUpOrLoginScreen,
                                            getString(R.string.login_fail_hdng_txt),
                                            getString(
                                                R.string.internet_error_txt
                                            )
                                        )
                                    }

                                })

                            } else {
                                val errorResponse = Gson().fromJson(
                                    response.errorBody()!!.charStream(), ErrorResponse::class.java
                                )
                                Globals.showPopoUpDialog(
                                    this@SignUpOrLoginScreen,
                                    getString(R.string.login_fail_hdng_txt),
                                    getString(
                                        R.string.login_error
                                    )
                                )
                            }

                        } else {
                            Globals.showPopoUpDialog(
                                this@SignUpOrLoginScreen,
                                getString(R.string.login_fail_hdng_txt),
                                getString(
                                    R.string.server_error_txt
                                )
                            )
                        }

                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        progressLyot.visibility = View.GONE
                        loginOrRegisterBtn.visibility = View.VISIBLE
                        Globals.showPopoUpDialog(
                            this@SignUpOrLoginScreen,
                            getString(R.string.login_fail_hdng_txt),
                            getString(
                                R.string.internet_error_txt
                            )
                        )
                    }

                })

            } else {

                // Registration
                val firstNamTxt = firstNamEdt.text.toString()
                val lastNameTxt = lastNameEdt.text.toString()
                val mblNumberTxt = mblNumEdt.text.toString()
                val pswrdTxt = pswrdEdt.text.toString()
                val cnfPswrdTxt = cnfPswrdEdt.text.toString()

                if (firstNamTxt != null && firstNamTxt.isNotEmpty()) {
                    if (!firstNamTxt.contains("0123456789!@#$%^&*()_+")) {
                        if (lastNameTxt != null && lastNameTxt.isNotEmpty()) {
                            if (!lastNameTxt.contains("0123456789!@#$%^&*()_+")) {
                                if (mblNumberTxt != null && mblNumberTxt.isNotEmpty()) {
                                    if (mblNumberTxt.length == 10) {
                                        if (pswrdTxt != null && pswrdTxt.isNotEmpty()) {
                                            if (cnfPswrdTxt != null && cnfPswrdTxt.isNotEmpty()) {
                                                if (pswrdTxt.contentEquals(cnfPswrdTxt)) {
                                                    loginOrRegisterBtn.isEnabled = false
                                                    signUpOrLoginViewModel.checkUserExistance(
                                                        mblNumberTxt
                                                    )
                                                } else {
                                                    Globals.showPopoUpDialog(
                                                        this,
                                                        getString(R.string.validation),
                                                        getString(
                                                            R.string.pswrd_mismatch_txt
                                                        )
                                                    )
                                                }
                                            } else {
                                                Globals.showPopoUpDialog(
                                                    this, getString(R.string.validation), getString(
                                                        R.string.cnf_pswrd_valid_txt
                                                    )
                                                )
                                            }
                                        } else {
                                            Globals.showPopoUpDialog(
                                                this, getString(R.string.validation), getString(
                                                    R.string.pswrd_valid_txt
                                                )
                                            )
                                        }
                                    } else {
                                        Globals.showPopoUpDialog(
                                            this, getString(R.string.validation), getString(
                                                R.string.mbl_digit_valid_txt
                                            )
                                        )
                                    }
                                } else {
                                    Globals.showPopoUpDialog(
                                        this,
                                        getString(R.string.validation),
                                        getString(R.string.mbl_valid_num_txt)
                                    )
                                }

                            } else {
                                Globals.showPopoUpDialog(
                                    this,
                                    getString(R.string.validation),
                                    getString(R.string.ln_alpha_valid_txt)
                                )
                            }

                        } else {
                            Globals.showPopoUpDialog(
                                this,
                                getString(R.string.validation),
                                getString(R.string.ln_valid_txt)
                            )
                        }

                    } else {
                        Globals.showPopoUpDialog(
                            this,
                            getString(R.string.validation),
                            getString(R.string.fn_alpha_valid_txt)
                        )
                    }
                } else {
                    Globals.showPopoUpDialog(
                        this,
                        getString(R.string.validation),
                        getString(R.string.fn_valid_txt)
                    )
                }

            }

        }

        forgotPswrdTxt.setOnClickListener {
            var navToForgotPswrdIntent = Intent(
                this@SignUpOrLoginScreen,
                ForgotPasswordScreen::class.java
            )
            startActivity(navToForgotPswrdIntent)
        }

        /*loginOrRegisterBtn.setOnClickListener {

            if(firstNamEdt.visibility == View.GONE){
                // Login selected
                val mblNumEdtVal = mblNumEdt.text.toString()
                val pswrdEdtVal = pswrdEdt.text.toString()

                if(mblNumEdtVal == null || mblNumEdtVal.isEmpty()){
                    Globals.showToastMsg(this,getString(R.string.mbl_valid_num_txt))
                    return@setOnClickListener
                }

                if(pswrdEdtVal == null || pswrdEdtVal.isEmpty()){
                    Globals.showToastMsg(this,getString(R.string.mbl_valid_pswrd_txt))
                    return@setOnClickListener
                }

                loginOrRegisterBtn.visibility = View.GONE
                progressLyot.visibility = View.VISIBLE

                RetrofitObj.getApiObj()!!.executeLogin(getString(R.string.grant_type_txt),mblNumEdtVal,pswrdEdtVal).enqueue(object: Callback<LoginResponse> {

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                        progressLyot.visibility = View.GONE
                        loginOrRegisterBtn.visibility = View.VISIBLE

                        if(response != null){

                            if(response.isSuccessful){
                                Globals.showPopoUpDialog(this@SignUpOrLoginScreen,getString(R.string.login_success_hdng_txt),getString(R.string.login_success_txt))
                            }else{
                                val errorResponse = Gson().fromJson(response.errorBody()!!.charStream(),ErrorResponse::class.java)
                                Globals.showPopoUpDialog(this@SignUpOrLoginScreen,getString(R.string.login_fail_hdng_txt),errorResponse.errorDescription!!)
                            }

                        }else{
                            Globals.showPopoUpDialog(this@SignUpOrLoginScreen,getString(R.string.login_fail_hdng_txt),getString(R.string.server_error_txt))
                        }

                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        progressLyot.visibility = View.GONE
                        loginOrRegisterBtn.visibility = View.VISIBLE
                        Globals.showPopoUpDialog(this@SignUpOrLoginScreen,getString(R.string.login_fail_hdng_txt),getString(R.string.internet_error_txt))
                    }

                })

            }else{
                // Register selected

            }
        }*/
        selectedLogin()
    }

    private fun observeDataSources() {

        signUpOrLoginViewModel.getUSerExistanceResponseToObserve().observe(this, Observer {
            loginOrRegisterBtn.isEnabled = true
            if (it != null) {

                if (!it.status) {

                    var navToOtpVerfyIntent = Intent(
                        this@SignUpOrLoginScreen,
                        OTPVerificationScreen::class.java
                    )
                    navToOtpVerfyIntent.putExtra(Constants.FIRST_NAME, firstNamEdt.text.toString())
                    navToOtpVerfyIntent.putExtra(Constants.LAST_NAME, lastNameEdt.text.toString())
                    navToOtpVerfyIntent.putExtra(Constants.MOBILE_NUMBER, mblNumEdt.text.toString())
                    navToOtpVerfyIntent.putExtra(Constants.PASSWORD, pswrdEdt.text.toString())
                    navToOtpVerfyIntent.putExtra(
                        Constants.FORGOT_PASSWORD_SOURCE_TYPE,
                        EnumOTPSource.ENUM_TYPE_REGISTRATION
                    )
                    startActivity(navToOtpVerfyIntent)
                    finish()

                } else {
                    Globals.showPopoUpDialog(
                        this,
                        getString(R.string.user_existence),
                        getString(R.string.user_exist_disclaimer)
                    )
                }

            } else {
                Globals.showPopoUpDialog(
                    this,
                    getString(R.string.user_existence),
                    getString(R.string.server_txt)
                )
            }
        })

    }

    override fun onResume() {
        super.onResume()
        // getCurrentVersion()
        val isPreferenceExisted = Globals.checkBoolFromPreferences(
            applicationContext,
            Constants.IS_PREFS_EXISTED
        )
        if (isPreferenceExisted) {
            val savedLoginSts = Globals.getBoolFromPreferences(
                applicationContext,
                Constants.IS_LOGIN
            )
            if (savedLoginSts) {
                populateLogin()
            } else {
                populateRegister()
            }
        } else {
            selectedTabHdngTxt.text = getString(R.string.plz_login_txt)
            loginTxtUnSelectedLyot.visibility = View.GONE
            registerTxtSelectedLyot.visibility = View.GONE
            registerTxtUnSelectedLyot.visibility = View.VISIBLE
            selectedLogin()
        }
        Globals.saveBoolToPreferences(this, Constants.IS_PREFS_EXISTED, true)
        observeDataSources()
    }

    private fun selectedRegister() {
        forgotPswrdTxt.visibility = View.GONE
        firstNamEdtLyout.visibility = View.VISIBLE
        firstNamEdt.visibility = View.VISIBLE
        lastNameEdtLyout.visibility = View.VISIBLE
        lastNameEdt.visibility = View.VISIBLE
        mblNumEdt.visibility = View.VISIBLE
        pswrdEdt.visibility = View.VISIBLE
        cnfPswrdEdtLyot.visibility = View.VISIBLE
        loginOrRegisterBtn.text = getString(R.string.register_txt)
        firstNamEdt.setText("")
        lastNameEdt.setText("")
        mblNumEdt.setText("")
        pswrdEdt.setText("")
        cnfPswrdEdt.setText("")
        mblNumEdt.clearFocus()
        pswrdEdt.clearFocus()
        Globals.saveBoolToPreferences(applicationContext, Constants.IS_LOGIN, false)
    }

    private fun populateRegister() {
        forgotPswrdTxt.visibility = View.GONE
        firstNamEdtLyout.visibility = View.VISIBLE
        firstNamEdt.visibility = View.VISIBLE
        lastNameEdtLyout.visibility = View.VISIBLE
        lastNameEdt.visibility = View.VISIBLE
        mblNumEdt.visibility = View.VISIBLE
        pswrdEdt.visibility = View.VISIBLE
        cnfPswrdEdtLyot.visibility = View.VISIBLE
        val firstNameTxt = Globals.getStringFromPreferences(
            applicationContext,
            Constants.FIRST_NAME
        )
        val lastNameTxt = Globals.getStringFromPreferences(applicationContext, Constants.LAST_NAME)
        val mblNumTxt = Globals.getStringFromPreferences(
            applicationContext,
            Constants.MOBILE_NUMBER
        )
        val pswrdTxt = Globals.getStringFromPreferences(applicationContext, Constants.PASSWORD)
        val cnfPswrdTxt = Globals.getStringFromPreferences(
            applicationContext,
            Constants.CNF_PASSWORD
        )
        firstNamEdt.setText(firstNameTxt)
        lastNameEdt.setText(lastNameTxt)
        mblNumEdt.setText(mblNumTxt)
        pswrdEdt.setText(pswrdTxt)
        cnfPswrdEdt.setText(cnfPswrdTxt)
    }

    private fun selectedLogin() {
        firstNamEdtLyout.visibility = View.GONE
        firstNamEdt.visibility = View.GONE
        lastNameEdtLyout.visibility = View.GONE
        lastNameEdt.visibility = View.GONE
        cnfPswrdEdtLyot.visibility = View.GONE
        mblNumEdt.visibility = View.VISIBLE
        pswrdEdt.visibility = View.VISIBLE
        forgotPswrdTxt.visibility = View.VISIBLE
        loginOrRegisterBtn.text = getString(R.string.login)
        selectedTabHdngTxt.text = getString(R.string.plz_login_txt)
        firstNamEdt.setText("")
        lastNameEdt.setText("")
        mblNumEdt.setText("")
        pswrdEdt.setText("")
        cnfPswrdEdt.setText("")
        mblNumEdt.clearFocus()
        pswrdEdt.clearFocus()
        Globals.saveBoolToPreferences(applicationContext, Constants.IS_LOGIN, true)
    }

    private fun populateLogin() {
        firstNamEdtLyout.visibility = View.GONE
        firstNamEdt.visibility = View.GONE
        lastNameEdtLyout.visibility = View.GONE
        lastNameEdt.visibility = View.GONE
        cnfPswrdEdtLyot.visibility = View.GONE
        mblNumEdt.visibility = View.VISIBLE
        pswrdEdt.visibility = View.VISIBLE
        forgotPswrdTxt.visibility = View.VISIBLE
        val mblNumTxt = Globals.getStringFromPreferences(
            applicationContext,
            Constants.MOBILE_NUMBER
        )
        val pswrdTxt = Globals.getStringFromPreferences(applicationContext, Constants.PASSWORD)
        mblNumEdt.setText(mblNumTxt)
        pswrdEdt.setText(pswrdTxt)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(Constants.MOBILE_NUMBER, mblNumEdt.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val mblNum = savedInstanceState.getString(Constants.MOBILE_NUMBER)
        mblNumEdt.setText(mblNum)
    }

    override fun onStart() {
        super.onStart()
        Log.d("sign_up_", "on_start_fired")
    }

    override fun onPause() {
        super.onPause()
        Log.d("sign_up_", "on_pause_fired")
        val firstNameTxt = firstNamEdt.text.toString()
        val lastNameTxt = lastNameEdt.text.toString()
        val mblNumTxt = mblNumEdt.text.toString()
        val pswrdTxt = pswrdEdt.text.toString()
        val newPswrdTxt = cnfPswrdEdt.text.toString()
        Globals.saveStringToPreferences(applicationContext, Constants.FIRST_NAME, firstNameTxt)
        Globals.saveStringToPreferences(applicationContext, Constants.LAST_NAME, lastNameTxt)
        Globals.saveStringToPreferences(applicationContext, Constants.MOBILE_NUMBER, mblNumTxt)
        Globals.saveStringToPreferences(applicationContext, Constants.PASSWORD, pswrdTxt)
        Globals.saveStringToPreferences(applicationContext, Constants.CNF_PASSWORD, newPswrdTxt)
    }

    override fun onStop() {
        super.onStop()
        Log.d("sign_up_", "on_stop_fired")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("sign_up_", "on_re_start_fired")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("sign_up_", "on_destroy_fired")
        Globals.clearLaurylPrefs(applicationContext)
    }


}
