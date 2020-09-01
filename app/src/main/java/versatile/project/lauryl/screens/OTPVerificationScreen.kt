package versatile.project.lauryl.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_otpverification_screen.*
import versatile.project.lauryl.R
import versatile.project.lauryl.utils.BiggerDotPasswordTransformationMethod
import versatile.project.lauryl.utils.MyEditTextWatcher
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.content.Context
import android.view.inputmethod.InputMethodManager

class OTPVerificationScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification_screen)

        verifyBtn.setOnClickListener {
            startActivity(Intent(this@OTPVerificationScreen, SignUpOrLoginScreen::class.java))
            finish()
        }

        otpOneEdt.transformationMethod = BiggerDotPasswordTransformationMethod()
        otpTwoEdt.transformationMethod = BiggerDotPasswordTransformationMethod()
        otpThreeEdt.transformationMethod = BiggerDotPasswordTransformationMethod()
        otpFourEdt.transformationMethod = BiggerDotPasswordTransformationMethod()
        showKeyboard()
        otpOneEdt.addTextChangedListener(
            MyEditTextWatcher(
                this@OTPVerificationScreen,
                otpOneEdt,
                otpTwoEdt
            )
        )
        otpTwoEdt.addTextChangedListener(
            MyEditTextWatcher(
                this@OTPVerificationScreen,
                otpTwoEdt,
                otpThreeEdt
            )
        )
        otpThreeEdt.addTextChangedListener(
            MyEditTextWatcher(
                this@OTPVerificationScreen,
                otpThreeEdt,
                otpFourEdt
            )
        )
        otpFourEdt.addTextChangedListener(
            MyEditTextWatcher(
                this@OTPVerificationScreen,
                otpFourEdt,
                otpFourEdt
            )
        )
    }

    fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(otpOneEdt, SHOW_IMPLICIT)
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(otpFourEdt.windowToken, 0)
    }

}
