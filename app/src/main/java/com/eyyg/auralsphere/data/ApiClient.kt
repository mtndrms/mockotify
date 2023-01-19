package com.eyyg.auralsphere.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient() {
    var retrofit: Retrofit? = null

    constructor(URL: String) : this() {
        retrofit = Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}