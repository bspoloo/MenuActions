package com.example.menuactions.classes

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class REST {
    companion object{
        //val BASE_URL = "https://jsonplaceholder.typicode.com/";
        val BASE_URL = "http://192.168.255.104:3000/";
        fun getRestEngine(): Retrofit{
            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit;
        }
    }
}