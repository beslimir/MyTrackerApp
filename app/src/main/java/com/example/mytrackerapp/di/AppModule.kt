package com.example.mytrackerapp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.mytrackerapp.Constants.KEY_FIRST_TIME_TOGGLE
import com.example.mytrackerapp.Constants.KEY_NAME
import com.example.mytrackerapp.Constants.KEY_WEIGHT
import com.example.mytrackerapp.Constants.SHARED_PREFS_NAME
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

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext app: Context) =
        app.getSharedPreferences(
            SHARED_PREFS_NAME,
            MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideName(sharedPref: SharedPreferences) = sharedPref.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPref: SharedPreferences) = sharedPref.getFloat(KEY_WEIGHT, 80f)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) = sharedPref.getBoolean(
        KEY_FIRST_TIME_TOGGLE, true
    )

}