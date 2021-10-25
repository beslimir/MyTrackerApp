package com.example.mytrackerapp.di

import android.content.Context
import androidx.room.Room
import com.example.mytrackerapp.db.RunDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RunDatabase::class.java,
        "run_db.db"
    ).build()

    @Singleton
    @Provides
    fun provideRunDAO(db: RunDatabase) = db.getRunDao()

}