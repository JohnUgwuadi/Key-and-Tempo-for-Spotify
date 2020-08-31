package com.johnugwuadi.keyandtempo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johnugwuadi.keyandtempo.data.local.track.Track
import com.johnugwuadi.keyandtempo.data.local.track.TrackDao

@Database(entities = [Track::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}