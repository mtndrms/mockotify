package com.eyyg.auralsphere.data.model

data class TrackResponse(
    val currentPage: String,
    val totalPages: Int,
    val tracks: List<Track>
)