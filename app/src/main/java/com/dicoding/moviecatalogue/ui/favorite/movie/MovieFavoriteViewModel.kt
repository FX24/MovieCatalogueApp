package com.dicoding.moviecatalogue.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity

class MovieFavoriteViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getfavMovies(): LiveData<List<MovieEntity>> = filmRepository.getFavMovies()
}