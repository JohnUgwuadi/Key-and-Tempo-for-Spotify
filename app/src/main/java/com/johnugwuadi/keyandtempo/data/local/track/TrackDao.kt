package com.johnugwuadi.keyandtempo.data.local.track

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrackDao {
    @Query("SELECT * FROM track WHERE id IS :id")
    suspend fun get(id: String): Track?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: Track)
}