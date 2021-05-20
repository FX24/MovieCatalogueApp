package com.dicoding.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.moviecatalogue.data.FilmEntity
import com.dicoding.moviecatalogue.data.source.FilmRepository

class TvShowViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getTvShow() : LiveData<List<FilmEntity>> = filmRepository.getPopularTvShows()
}