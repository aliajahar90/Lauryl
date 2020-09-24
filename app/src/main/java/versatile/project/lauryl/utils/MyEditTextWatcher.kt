package versatile.project.lauryl.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import versatile.project.lauryl.R
import versatile.project.lauryl.screens.OTPVerificationScreen

class MyEditTextWatcher(var otpVerificationScreen: OTPVerificationScreen, private var sourseEdt:EditText, private var destEdt:EditText):TextWatcher {

    override fun afterTextChanged(p0: Editable?) {

        when(sourseEdt.id){

            R.id.otpOneEdt ->{
                destEdt.requestFocus()
            }
            R.id.otpTwoEdt ->{
                destEdt.requestFocus()
            }
            R.id.otpThreeEdt ->{
                destEdt.requestFocus()
            }
            R.id.otpFourEdt ->{
                destEdt.clearFocus()
                otpVerificationScreen.hideKeyboard()
            }

        }

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}