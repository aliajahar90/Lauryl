package versatile.project.lauryl.screens

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.splash_screen.*
import versatile.project.lauryl.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) { instanceIdResult ->
            val newToken = instanceIdResult.token
            Log.d("newToken_", newToken)
            //updateFirebaseInstanceIdInAppServer(newToken)
        }

        Handler().postDelayed({
            startActivity(Intent(this@SplashScreen, IntroScreen::class.java))
            finish()
        },6000)
        startGifView()
        
    }

    private fun startGifView() {
        Glide.with(this).load(R.drawable.splash).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                if (resource is GifDrawable) {
                    (resource).setLoopCount(1)
                }
                return false
            }

        }).into(splashGif)
    }

}