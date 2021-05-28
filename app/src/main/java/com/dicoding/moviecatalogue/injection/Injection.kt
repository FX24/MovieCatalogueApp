package com.dicoding.moviecatalogue.injection

import android.content.Context
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.data.source.local.LocalDataSource
import com.dicoding.moviecatalogue.data.source.local.room.FilmDatabase
import com.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FilmRepository {

        val database = FilmDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.filmDao())
        val appExecutors = AppExecutors()

        return FilmRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}