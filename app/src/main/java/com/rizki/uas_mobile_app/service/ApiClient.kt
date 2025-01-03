package com.rizki.crud_berita_user.service

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Arrays

object ApiClient {

    //http://10.208.104.237:8080/beritaDb/getBerita.php
    private const val BASE_URL = "http://10.208.105.241:8080/beritaDb/"

     val retrofit: BeritaService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)//manggil fun client
            .client(interceptor()) // tambah
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BeritaService::class.java)
    }

    private val client = OkHttpClient.Builder()
        .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
        .addInterceptor{ chain ->
            val request  = chain.request().newBuilder()
                .addHeader("Content-type", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()

//    add interceptor
    fun interceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().
            addInterceptor(interceptor).build()
    }

//    val beritaServices : BeritaService by lazy {
//        retrofit.create(BeritaService::class.java)
//    }
}