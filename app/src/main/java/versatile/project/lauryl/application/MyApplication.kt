package versatile.project.lauryl.application

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree
import versatile.project.lauryl.BuildConfig


open class MyApplication: Application() {

    var accessToken = "135693eb-f0ea-4377-bd3a-88c4196628dd"
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}