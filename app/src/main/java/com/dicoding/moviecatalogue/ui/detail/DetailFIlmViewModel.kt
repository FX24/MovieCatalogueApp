package com.dicoding.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.moviecatalogue.data.FilmDetailEntity
import com.dicoding.moviecatalogue.data.source.FilmRepository

class DetailFilmViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    private lateinit var movieId: String
    private lateinit var tvId: String

    fun setSelectedMovie(movieId: String) {
        this.movieId = movieId
    }

    fun setSelectedTvShow(tvId: String) {
        this.tvId = tvId
    }

    fun getMovieDetails(): LiveData<FilmDetailEntity> = filmRepository.getMovieDetails(movieId)

    fun getTvShowDetails(): LiveData<FilmDetailEntity> = filmRepository.getTvShowDetails(tvId)
}