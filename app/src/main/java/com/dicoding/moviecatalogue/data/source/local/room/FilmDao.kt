package com.dicoding.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity

@Dao
interface FilmDao {

    @Query("SELECT * FROM  movieentity")
    fun getMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM  tvshowentity")
    fun getTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM movieentity WHERE id = :movieId")
    fun getMovieDetailbyId(movieId: String): LiveData<MovieEntity>

    @Query("SELECT * FROM tvshowentity WHERE id = :tvId")
    fun getTvShowDetailbyId(tvId: String): LiveData<TvShowEntity>

    @Query("SELECT * FROM movieentity WHERE favorite = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvshowentity WHERE favorite = 1")
    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<TvShowEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)
}