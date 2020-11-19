package versatile.project.lauryl.network.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import versatile.project.lauryl.BuildConfig
import versatile.project.lauryl.utils.Constants
import java.util.concurrent.TimeUnit


class RetrofitObj {

    companion object {

        var retrofit: Retrofit? = null
        var versatileRetrofit: Retrofit? = null
        private const val BASE_URL_GENERAL = BuildConfig.BASE_URL_GENERAL
        private const val BASE_URL_VERSATILE_GENERAL = BuildConfig.BASE_URL_VERSATILE_GENERAL

        fun getApiObj(): ApiServices? {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor(Constants.API_BASIC_AUTH_USER_NAME,Constants.API_BASIC_AUTH_PASSWORD))
                .addInterceptor(interceptor)
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
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor(Constants.API_BASIC_AUTH_USER_NAME_VERSATILE,Constants.API_BASIC_AUTH_PASSWORD_VERSATILE))
                .addInterceptor(interceptor)
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