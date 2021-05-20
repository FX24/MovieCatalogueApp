package com.dicoding.moviecatalogue.data.source.remote

import android.util.Log
import com.dicoding.moviecatalogue.data.source.remote.response.*
import com.dicoding.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {

        private const val TAG = "RemoteDataSource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    fun getPopularMovies(callback: LoadPopularMoviesCallback) {
        EspressoIdlingResource.increment()

        val client = ApiConfig.getApiService().getPopularMovies()
        client.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { callback.onPopularMoviesReceived(it) }
                    EspressoIdlingResource.decrement()
                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure listmovie: ${t.message.toString()}")
            }
        })
    }

    fun getPopularTvShows(callback: LoadPopularTvShowsCallback) {
        EspressoIdlingResource.increment()

        val client = ApiConfig.getApiService().getPopularTvShows()
        client.enqueue(object : Callback<TvShowResponse>{
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { callback.onPopularTvShowsReceived(it) }
                    EspressoIdlingResource.decrement()
                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.e(TAG, "onFailure listtvshow: ${t.message.toString()}")
            }

        })
    }

    fun getMovieDetails(movieId: String, callback: LoadMovieDetailsCallback) {
        EspressoIdlingResource.increment()

        val client = ApiConfig.getApiService().getMovieDetail(movieId)
        client.enqueue(object : Callback<DetailMovieResponse>{
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onMovieDetailsReceived(it) }
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure moviedetail: ${t.message.toString()}")
            }
        })
    }

    fun getTvShowDetails(tvId: String, callback: LoadTvShowDetailsCallback){
        EspressoIdlingResource.increment()

        val client = ApiConfig.getApiService().getTvShowDetail(tvId)
        client.enqueue(object : Callback<DetailTVResponse>{
            override fun onResponse(
                call: Call<DetailTVResponse>,
                response: Response<DetailTVResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onTvShowDetailsReceived(it) }
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<DetailTVResponse>, t: Throwable) {
                Log.e(TAG, "onFailure tvshowdetail: ${t.message.toString()}")
            }

        })
    }

    interface LoadPopularMoviesCallback {
        fun onPopularMoviesReceived(movieResponse: List<MovieResultsItem>)
    }

    interface LoadPopularTvShowsCallback {
        fun onPopularTvShowsReceived(tvShowResponse: List<TvShowResultsItem>)
    }

    interface LoadMovieDetailsCallback {
        fun onMovieDetailsReceived(movieResponse: DetailMovieResponse)
    }

    interface LoadTvShowDetailsCallback {
        fun onTvShowDetailsReceived(tvShowResponse: DetailTVResponse)
    }


}