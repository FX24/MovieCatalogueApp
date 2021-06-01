package com.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.vo.Resource

interface FilmDataSource {
    fun getPopularMovies() : LiveData<Resource<PagedList<MovieEntity>>>

    fun getPopularTvShows() : LiveData<Resource<PagedList<TvShowEntity>>>

    fun getMovieDetails(movieId: String) : LiveData<Resource<MovieEntity>>

    fun getTvShowDetails(tvId : String) : LiveData<Resource<TvShowEntity>>

    fun getFavMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setMovieFavorite(movie: MovieEntity, state: Boolean)

    fun setTvShowFavorite(tvShow: TvShowEntity, state: Boolean)
}