package com.dicoding.moviecatalogue.data.source.remote

import com.dicoding.moviecatalogue.BuildConfig
import com.dicoding.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.dicoding.moviecatalogue.data.source.remote.response.DetailTVResponse
import com.dicoding.moviecatalogue.data.source.remote.response.MovieResponse
import com.dicoding.moviecatalogue.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.MOVIEDB_API_KEY
    }

    @GET("movie/popular?api_key=$API_KEY")
    fun getPopularMovies(
    ) : Call<MovieResponse>

    @GET("tv/popular?api_key=$API_KEY")
    fun getPopularTvShows(
    ) : Call<TvShowResponse>


    @GET("movie/{movie_id}?api_key=$API_KEY")
    fun getMovieDetail(
        @Path("movie_id") movieId: String
    ) : Call<DetailMovieResponse>

    @GET("tv/{tv_id}?api_key=$API_KEY")
    fun getTvShowDetail(
        @Path("tv_id") tvId: String
    ) : Call<DetailTVResponse>
}