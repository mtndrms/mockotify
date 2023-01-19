package com.eyyg.auralsphere.data.service

import com.eyyg.auralsphere.data.model.Track
import com.eyyg.auralsphere.data.model.TrackResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrackService {

    @GET("tracks")
    fun getTracks(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<TrackResponse>

    @GET("tracks/{id}")
    fun getTrackByID(
        @Path("id") ID: String
    ): Call<List<Track>>

    @GET("tracks/genre")
    fun getTracksFilteredByGenre(
        @Query("genre") genre: String
    ): Call<List<Track>>
}