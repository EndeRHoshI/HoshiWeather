package com.hoshi.weather.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit 要用到的的 Creator
 */
object ServiceCreator {

    private const val BASE_URL = "https://md6apw957r.re.qweatherapi.com"
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 10L

    private fun create(url: String = BASE_URL): Retrofit {

        // 日志拦截器
        val httpLogInterceptor = HttpLoggingInterceptor { msg -> println(msg) }
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // okHttpClientBuilder
        val okHttpClientBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(httpLogInterceptor)
        }

        return RetrofitBuild(
            url = url,
            client = okHttpClientBuilder.build(),
            gsonFactory = GsonConverterFactory.create()
        ).retrofit
    }

    /**
     * get ServiceApi
     */
    fun <T> create(service: Class<T>): T = create().create(service)

}

class RetrofitBuild(
    url: String, client: OkHttpClient,
    gsonFactory: GsonConverterFactory
) {
    val retrofit: Retrofit = Retrofit.Builder().apply {
        baseUrl(url)
        client(client)
        addConverterFactory(gsonFactory)

    }.build()
}