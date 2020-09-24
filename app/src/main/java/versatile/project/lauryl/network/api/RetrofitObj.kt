package versatile.project.lauryl.network.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import versatile.project.lauryl.utils.Constants
import java.util.concurrent.TimeUnit


class RetrofitObj {

    companion object {

        var retrofit: Retrofit? = null
        var versatileRetrofit: Retrofit? = null
        private const val BASE_URL_GENERAL = "https://apistaging.versatilecommerce.co.uk/Lauryl/"
        private const val BASE_URL_VERSATILE_GENERAL = "https://apiqa.versatilecommerce.co.uk/vcApi/"

        fun getApiObj(): ApiServices? {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor(Constants.API_BASIC_AUTH_USER_NAME,Constants.API_BASIC_AUTH_PASSWORD))
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build()

            if (retrofit == null && BASE_URL_GENERAL.isNotEmpty()) {
                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .client(okHttpClient)
                    .baseUrl(BASE_URL_GENERAL).build()
            }
            return retrofit!!.create(ApiServices::class.java)

        }

        fun getVersatileApiObj(): ApiServices? {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor(Constants.API_BASIC_AUTH_USER_NAME_VERSATILE,Constants.API_BASIC_AUTH_PASSWORD_VERSATILE))
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build()

            if (versatileRetrofit == null && BASE_URL_VERSATILE_GENERAL.isNotEmpty()) {
                versatileRetrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .client(okHttpClient)
                    .baseUrl(BASE_URL_VERSATILE_GENERAL).build()
            }
            return versatileRetrofit!!.create(ApiServices::class.java)

        }

    }

}