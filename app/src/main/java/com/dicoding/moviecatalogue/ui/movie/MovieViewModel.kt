package com.dicoding.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.vo.Resource

class MovieViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = filmRepository.getPopularMovies()
}