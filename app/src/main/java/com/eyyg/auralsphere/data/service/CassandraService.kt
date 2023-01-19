package com.eyyg.auralsphere.data.service

import com.eyyg.auralsphere.data.model.Suggestion
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CassandraService {
    @GET("/recommend")
    fun recommend(@Query("refer_id") trackID: String): Call<Void>

    @GET("/suggests")
    fun suggests(): Call<List<Suggestion>>
}