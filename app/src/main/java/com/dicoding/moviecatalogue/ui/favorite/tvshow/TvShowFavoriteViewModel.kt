package com.dicoding.moviecatalogue.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity

class TvShowFavoriteViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getFavoriteTvShow(): LiveData<List<TvShowEntity>> = filmRepository.getFavTvShows()
}
