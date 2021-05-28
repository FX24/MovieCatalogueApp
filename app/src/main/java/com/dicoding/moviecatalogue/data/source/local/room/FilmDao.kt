package com.dicoding.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity

@Dao
interface FilmDao {

    @Query("SELECT * FROM  movieentity")
    fun getMovie(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM  tvshowentity")
    fun getTvShow(): LiveData<List<TvShowEntity>>

    @Query("SELECT * FROM movieentity WHERE id = :movieId")
    fun getMovieDetailbyId(movieId: String): LiveData<MovieEntity>

    @Query("SELECT * FROM tvshowentity WHERE id = :tvId")
    fun getTvShowDetailbyId(tvId: String): LiveData<TvShowEntity>

    @Query("SELECT * FROM movieentity WHERE favorite = 1")
    fun getFavoriteMovie(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM tvshowentity WHERE favorite = 1")
    fun getFavoriteTvShow(): LiveData<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<TvShowEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)
}