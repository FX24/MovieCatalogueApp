package com.dicoding.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.FilmRepository

class MovieViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getMovies(): LiveData<List<TvShowEntity>> = filmRepository.getPopularMovies()
}