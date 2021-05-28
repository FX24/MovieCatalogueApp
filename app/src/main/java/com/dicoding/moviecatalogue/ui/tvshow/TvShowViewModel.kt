package com.dicoding.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.FilmRepository

class TvShowViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getTvShow() : LiveData<List<TvShowEntity>> = filmRepository.getPopularTvShows()
}