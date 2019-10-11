package com.example.openthedoor.data.remote.openthedoor

import android.content.Context
import com.example.openthedoor.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.example.openthedoor.data.sharedPreferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class OpenTheDoorRetroFitClientInstance(ctx: Context) {

    private var retrofit: Retrofit? = null
    private val httpClient = OkHttpClient.Builder()
    var context = ctx

    // TODO : Replace this with app's original base url...
    private val PROD_BASE_URL = "http://travces.com/travces/public/api/"
    private val DEV_BASE_URL = "http://192.168.10.10:8080/travces/public/api/"
    private val BASE_URL = PROD_BASE_URL

    init {
        if (retrofit == null) {
            initRetrofit()
        }
    }

    fun initRetrofit() {
        val retrofitBuilder = retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        val authToken = AppPreferences(context).getAuthenticationToken()
        if (authToken.isNotEmpty()) {
            val interceptor = AuthenticationInterceptor(
                authToken,
                context
            )
            httpClient.addInterceptor(interceptor)

            if (BuildConfig.DEBUG) {
                val loggingIntercepter = getLoggingInterceptor()
                loggingIntercepter.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(loggingIntercepter)
            }

            httpClient.readTimeout(1, TimeUnit.MINUTES)
            httpClient.connectTimeout(1, TimeUnit.MINUTES)

            retrofitBuilder.client(httpClient.build())
        } else {
            val interceptor =
                AuthenticationInterceptor("", context)
            httpClient.addInterceptor(interceptor)

            if (BuildConfig.DEBUG) {
                val loggingIntercepter = getLoggingInterceptor()
                loggingIntercepter.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(loggingIntercepter)
            }

            httpClient.readTimeout(1, TimeUnit.MINUTES)
            httpClient.connectTimeout(1, TimeUnit.MINUTES)

            retrofitBuilder.client(httpClient.build())
        }

        retrofit = retrofitBuilder.build()
    }

    fun getService(): ApiService {
        return retrofit!!.create<ApiService>(ApiService::class.java)
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    fun getRetrofit(): Retrofit? {
        return retrofit
    }

    companion object {
        var singleInstance: OpenTheDoorRetroFitClientInstance? = null

        fun getInstance(context: Context): OpenTheDoorRetroFitClientInstance? {
            if (singleInstance == null)
                singleInstance =
                    OpenTheDoorRetroFitClientInstance(context)
            return singleInstance
        }
    }

    class AuthenticationInterceptor internal constructor(
        private val authToken: String,
        private val context: Context
    ) :
        Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            val builder = original.newBuilder()
                .header("Bearer", authToken)
                .header("x-access-token", authToken)
                .header("Accept", "application/json")

            val request = builder.build()

            //return chain.proceed(request)
            val response = chain.proceed(request)
            if (response.code() == 401) {
                AppPreferences(context).logout()
                AppPreferences(context).redirectToLogin(context, response.message())
            }
            return response
        }
    }
}