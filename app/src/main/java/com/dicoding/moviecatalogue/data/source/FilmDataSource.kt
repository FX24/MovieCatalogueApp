package com.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.vo.Resource

interface FilmDataSource {
    fun getPopularMovies() : LiveData<Resource<List<MovieEntity>>>

    fun getPopularTvShows() : LiveData<Resource<List<TvShowEntity>>>

    fun getMovieDetails(movieId: String) : LiveData<Resource<MovieEntity>>

    fun getTvShowDetails(tvId : String) : LiveData<Resource<TvShowEntity>>

    fun getFavMovies(): LiveData<List<MovieEntity>>

    fun getFavTvShows(): LiveData<List<TvShowEntity>>

    fun setMovieBookmark(movie: MovieEntity, state: Boolean)

    fun setTvShowBookmark(tvShow: TvShowEntity, state: Boolean)
}