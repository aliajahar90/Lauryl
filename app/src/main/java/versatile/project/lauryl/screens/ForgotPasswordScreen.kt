package versatile.project.lauryl.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_forgot_password_screen.*
import versatile.project.lauryl.R

class ForgotPasswordScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_screen)
        sendBtn.setOnClickListener {
            finish()
        }
    }

}
