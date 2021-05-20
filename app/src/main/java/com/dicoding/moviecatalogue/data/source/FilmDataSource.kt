package com.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.dicoding.moviecatalogue.data.FilmDetailEntity
import com.dicoding.moviecatalogue.data.FilmEntity

interface FilmDataSource {
    fun getPopularMovies() : LiveData<List<FilmEntity>>

    fun getPopularTvShows() : LiveData<List<FilmEntity>>

    fun getMovieDetails(movieId: String) : LiveData<FilmDetailEntity>

    fun getTvShowDetails(tvId : String) : LiveData<FilmDetailEntity>
}