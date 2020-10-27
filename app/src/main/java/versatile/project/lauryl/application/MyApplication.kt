package versatile.project.lauryl.application

import android.util.SparseBooleanArray
import androidx.multidex.MultiDexApplication
import timber.log.Timber
import timber.log.Timber.DebugTree
import versatile.project.lauryl.BuildConfig
import versatile.project.lauryl.utils.KevinsTree


open class MyApplication : MultiDexApplication() {

    var accessToken = "135693eb-f0ea-4377-bd3a-88c4196628dd"
    var userAccessToken = "135693eb-f0ea-4377-bd3a-88c4196628dd"
    var mobileNumber = ""
    var selectedOrderTab = 0
    var createOrderSerializedService=""
    var createOrderSerializdedAddressData=""
    var createOrderSerializdedProfile=""
    var activeSessionOrderNumber=""
    var selectedServiceArray: SparseBooleanArray = SparseBooleanArray()
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(KevinsTree())
        }
    }
}