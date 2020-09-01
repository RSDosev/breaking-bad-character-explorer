package com.gan.breakingbadcharacters.data.source.remote.client

import com.gan.breakingbadcharacters.data.source.remote.BreakingBadCharactersAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(private val baseUrl: String) {

    val retrofitClient: Retrofit
        get() = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    val breakingBadCharactersAPIService
        get() = retrofitClient.create(BreakingBadCharactersAPIService::class.java)

    val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .apply {
//                if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor(PrettyJsonHttpLogger()))
//                }
            }
            .build()
}