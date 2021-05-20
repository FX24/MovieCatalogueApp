package com.dicoding.moviecatalogue.injection

import android.content.Context
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): FilmRepository {

        val remoteDataSource = RemoteDataSource.getInstance()

        return FilmRepository.getInstance(remoteDataSource)
    }
}