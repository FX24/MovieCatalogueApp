package com.dicoding.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.vo.Resource

class TvShowViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getTvShow() : LiveData<Resource<PagedList<TvShowEntity>>> = filmRepository.getPopularTvShows()
}