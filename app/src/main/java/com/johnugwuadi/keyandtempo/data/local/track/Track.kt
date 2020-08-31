package com.johnugwuadi.keyandtempo.data.local.track

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Track(
    @PrimaryKey
    val id: String,
    val key: Int,
    val mode: Int,
    val tempo: Double
)