package com.johnugwuadi.keyandtempo.data.remote.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnalysisResponse(
    val key: Int,
    val mode: Int,
    val tempo: Double
)