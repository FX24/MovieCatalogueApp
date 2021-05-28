package com.dicoding.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.moviecatalogue.data.source.remote.api.ApiConfig
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

    fun getPopularMovies(): LiveData<ApiResponse<List<MovieResultsItem>>> {
        EspressoIdlingResource.increment()

        val resultMovie = MutableLiveData<ApiResponse<List<MovieResultsItem>>>()

        val client = ApiConfig.getApiService().getPopularMovies()
        client.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    resultMovie.value = ApiResponse.success(response.body()?.results!!)
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

        return resultMovie
    }
//
//    if (response.isSuccessful) {
//        response.body()?.results?.let {
//            resultTvShow.value = ApiResponse.success(it)
//        }
//        EspressoIdlingResource.decrement()
//    }

    fun getPopularTvShows(): LiveData<ApiResponse<List<TvShowResultsItem>>> {
        EspressoIdlingResource.increment()

        val resultTvShow = MutableLiveData<ApiResponse<List<TvShowResultsItem>>>()

        val client = ApiConfig.getApiService().getPopularTvShows()
        client.enqueue(object : Callback<TvShowResponse>{
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                if (response.isSuccessful) {
                    resultTvShow.value = ApiResponse.success(response.body()?.results!!)
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

        return resultTvShow
    }

//    if (response.isSuccessful) {
//        response.body()?.let {
//            resultMovieDetail.value = ApiResponse.success(it)
//        }
//        EspressoIdlingResource.decrement()
//    }

    fun getMovieDetails(movieId: String): LiveData<ApiResponse<DetailMovieResponse>> {
        EspressoIdlingResource.increment()

        val resultMovieDetail= MutableLiveData<ApiResponse<DetailMovieResponse>>()

        val client = ApiConfig.getApiService().getMovieDetail(movieId)
        client.enqueue(object : Callback<DetailMovieResponse>{
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                if (response.isSuccessful) {
                    resultMovieDetail.value = ApiResponse.success(response.body()!!)
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure moviedetail: ${t.message.toString()}")
            }
        })

        return resultMovieDetail
    }

    fun getTvShowDetails(tvId: String): LiveData<ApiResponse<DetailTVResponse>>{
        EspressoIdlingResource.increment()

        val resultTvShowDetail= MutableLiveData<ApiResponse<DetailTVResponse>>()

        val client = ApiConfig.getApiService().getTvShowDetail(tvId)
        client.enqueue(object : Callback<DetailTVResponse>{
            override fun onResponse(
                call: Call<DetailTVResponse>,
                response: Response<DetailTVResponse>
            ) {
                if (response.isSuccessful) {
                    resultTvShowDetail.value = ApiResponse.success(response.body()!!)
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<DetailTVResponse>, t: Throwable) {
                Log.e(TAG, "onFailure tvshowdetail: ${t.message.toString()}")
            }

        })

        return resultTvShowDetail
    }
}