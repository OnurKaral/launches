package com.onrkrl.launches.di

import android.content.Context
import androidx.room.Room
import com.onrkrl.launches.data.datasource.LocalCacheDataSource
import com.onrkrl.launches.data.datasource.LocalDataSource
import com.onrkrl.launches.data.local.AppDatabase
import com.onrkrl.launches.data.repository.SatelliteRepositoryImpl
import com.onrkrl.launches.domain.repository.SatelliteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "satellite_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context): LocalDataSource {
        return LocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideLocalCacheDataSource(database: AppDatabase): LocalCacheDataSource {
        return LocalCacheDataSource(database)
    }

    @Provides
    @Singleton
    fun provideSatelliteRepository(
        localDataSource: LocalDataSource,
        localCacheDataSource: LocalCacheDataSource
    ): SatelliteRepository {
        return SatelliteRepositoryImpl(localDataSource, localCacheDataSource)
    }
}
