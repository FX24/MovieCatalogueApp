package com.dicoding.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.local.room.FilmDao

class LocalDataSource private constructor(private val mFilmDao: FilmDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(filmDao: FilmDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(filmDao)
    }

    fun getAllMovies(): LiveData<List<MovieEntity>> = mFilmDao.getMovie()

    fun getAllTvShows(): LiveData<List<TvShowEntity>> = mFilmDao.getTvShow()

    fun getAllFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = mFilmDao.getFavoriteMovie()

    fun getAllFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> = mFilmDao.getFavoriteTvShow()

    fun getMovieWithDetailbyId(movieId: String): LiveData<MovieEntity> = mFilmDao.getMovieDetailbyId(movieId)

    fun getTvShowWithDetailbyId(tvId: String): LiveData<TvShowEntity> = mFilmDao.getTvShowDetailbyId(tvId)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        mFilmDao.updateMovie(movie)
    }

    fun setTvShowFavorite(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.favorite = newState
        mFilmDao.updateTvShow(tvShow)
    }

    fun insertMovie(movie: List<MovieEntity>) = mFilmDao.insertMovie(movie)

    fun insertTvShow(tvShow: List<TvShowEntity>) = mFilmDao.insertTvShow(tvShow)

    fun updateMovie(movie: MovieEntity) = mFilmDao.updateMovie(movie)

    fun updateTvShow(tvShow: TvShowEntity) = mFilmDao.updateTvShow(tvShow)

 }