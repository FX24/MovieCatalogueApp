package com.dicoding.moviecatalogue.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity

class MovieFavoriteViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getfavMovies(): LiveData<PagedList<MovieEntity>> = filmRepository.getFavMovies()
}