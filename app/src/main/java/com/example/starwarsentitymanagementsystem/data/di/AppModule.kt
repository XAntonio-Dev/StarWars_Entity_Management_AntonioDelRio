package com.example.starwarsentitymanagementsystem.data.di

import android.content.Context
import com.example.starwarsentitymanagementsystem.data.StarWarsDatabase
import com.example.starwarsentitymanagementsystem.data.dao.SpeciesDao
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
    fun provideDatabase(@ApplicationContext context: Context): StarWarsDatabase {
        return StarWarsDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideSpeciesDao(db: StarWarsDatabase): SpeciesDao {
        return db.getSpeciesDao()
    }
}