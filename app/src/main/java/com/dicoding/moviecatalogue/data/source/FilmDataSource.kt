package com.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.vo.Resource

interface FilmDataSource {
    fun getPopularMovies() : LiveData<Resource<List<MovieEntity>>>

    fun getPopularTvShows() : LiveData<Resource<List<TvShowEntity>>>

    fun getMovieDetails(movieId: String) : LiveData<Resource<MovieEntity>>

    fun getTvShowDetails(tvId : String) : LiveData<Resource<TvShowEntity>>

    fun getFavMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setMovieBookmark(movie: MovieEntity, state: Boolean)

    fun setTvShowBookmark(tvShow: TvShowEntity, state: Boolean)
}