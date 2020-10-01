package versatile.project.lauryl.base

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import versatile.project.lauryl.R
import versatile.project.lauryl.utils.ProgressDialogHelper

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
    var progressDialog: Dialog? = null


    fun hideLoading() {
        progressDialog?.dismiss()
    }

    fun shout(message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    fun showLoading() {

        if (progressDialog == null) {
            progressDialog = ProgressDialogHelper.progressDialog(this)
        }
        if (!progressDialog?.isShowing!!)
            progressDialog?.show()
    }
}