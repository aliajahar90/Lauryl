package versatile.project.lauryl.base

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import versatile.project.lauryl.R
import versatile.project.lauryl.data.source.prefs.redis.RedisProvider
import versatile.project.lauryl.data.source.prefs.redis.base.KRedis
import versatile.project.lauryl.utils.ProgressDialogHelper

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        redis = RedisProvider.getRedis(this)
    }

    var progressDialog: Dialog? = null
    public lateinit var redis: KRedis

    fun hideLoading() {
        progressDialog?.dismiss()
    }

    fun shout(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun scream(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showLoading() {

        if (progressDialog == null) {
            progressDialog = ProgressDialogHelper.progressDialog(this)
        }
        if (!progressDialog?.isShowing!!)
            progressDialog?.show()
    }
}