package versatile.project.lauryl.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sign_up_or_login_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import versatile.project.lauryl.R
import versatile.project.lauryl.model.ErrorResponse
import versatile.project.lauryl.model.LoginResponse
import versatile.project.lauryl.network.api.RetrofitObj
import versatile.project.lauryl.utils.Globals

class SignUpOrLoginScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_or_login_screen)

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
                // Login
                startActivity(Intent(this@SignUpOrLoginScreen, HomeScreen::class.java))
                finish()
            } else {
                // Registration
                startActivity(Intent(this@SignUpOrLoginScreen, OTPVerificationScreen::class.java))
                finish()
            }

        }

        forgotPswrdTxt.setOnClickListener {
            startActivity(Intent(this@SignUpOrLoginScreen, ForgotPasswordScreen::class.java))
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

    }

    override fun onResume() {
        super.onResume()
        selectedTabHdngTxt.text = getString(R.string.plz_login_txt)
        loginTxtUnSelectedLyot.visibility = View.GONE
        registerTxtSelectedLyot.visibility = View.GONE
        registerTxtUnSelectedLyot.visibility = View.VISIBLE
        selectedLogin()
    }

    private fun selectedRegister() {
        forgotPswrdTxt.visibility = View.GONE
        firstNamEdt.visibility = View.VISIBLE
        lastNameEdt.visibility = View.VISIBLE
        mblNumEdt.visibility = View.VISIBLE
        pswrdEdt.visibility = View.VISIBLE
        cnfPswrdEdtLyot.visibility = View.VISIBLE
        firstNamEdt.setText("")
        lastNameEdt.setText("")
        mblNumEdt.setText("")
        pswrdEdt.setText("")
        cnfPswrdEdt.setText("")
    }

    private fun selectedLogin() {
        firstNamEdt.visibility = View.GONE
        lastNameEdt.visibility = View.GONE
        cnfPswrdEdtLyot.visibility = View.GONE
        mblNumEdt.visibility = View.VISIBLE
        pswrdEdt.visibility = View.VISIBLE
        forgotPswrdTxt.visibility = View.VISIBLE
        firstNamEdt.setText("")
        lastNameEdt.setText("")
        mblNumEdt.setText("")
        pswrdEdt.setText("")
        cnfPswrdEdt.setText("")
    }

}
