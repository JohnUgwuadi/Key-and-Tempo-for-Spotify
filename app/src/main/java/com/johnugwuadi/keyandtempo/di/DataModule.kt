package com.johnugwuadi.keyandtempo.di

import android.content.Context
import androidx.room.Room
import com.johnugwuadi.keyandtempo.BuildConfig
import com.johnugwuadi.keyandtempo.data.local.AppDatabase
import com.johnugwuadi.keyandtempo.data.local.track.TrackDao
import com.johnugwuadi.keyandtempo.data.remote.SpotifyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideSpotifyService(): SpotifyService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SpotifyService::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "appdatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTrackDao(database: AppDatabase): TrackDao {
        return database.trackDao()
    }
}