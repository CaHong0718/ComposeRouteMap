package com.example.composeroutemap.data.remote



import android.content.Context
import com.example.composeroutemap.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NaverSearchNetwork {
    @Volatile
    private var cachedApi: NaverSearchApi? = null

    /** 앱 전역에서 한 번만 호출해도 재사용됩니다. */
    fun api(context: Context): NaverSearchApi =
        cachedApi ?: synchronized(this) {
            cachedApi ?: createApi(context.applicationContext).also { cachedApi = it }
        }

    // ──────────────────────────────────────────────────────────────────────────────
    private fun createApi(ctx: Context): NaverSearchApi {
        val clientId = ctx.getString(R.string.naver_openapi_client_id)
        val clientSec = ctx.getString(R.string.naver_openapi_client_secret)

        val okHttp = OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .addHeader("X-Naver-Client-Id", clientId)
                    .addHeader("X-Naver-Client-Secret", clientSec)
                    .build()
                    .let(chain::proceed)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()

        return Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
            .create(NaverSearchApi::class.java)
    }
}