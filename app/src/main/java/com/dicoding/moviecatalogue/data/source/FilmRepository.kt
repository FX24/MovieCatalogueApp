package com.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.moviecatalogue.data.FilmDetailEntity
import com.dicoding.moviecatalogue.data.FilmEntity
import com.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.dicoding.moviecatalogue.data.source.remote.response.DetailTVResponse
import com.dicoding.moviecatalogue.data.source.remote.response.MovieResultsItem
import com.dicoding.moviecatalogue.data.source.remote.response.TvShowResultsItem

class FilmRepository private constructor(private val remoteDataSource: RemoteDataSource) : FilmDataSource {

    companion object {
        @Volatile
        private var instance: FilmRepository? = null

        fun getInstance(remoteData: RemoteDataSource): FilmRepository =
            instance ?: synchronized(this) {
                instance ?: FilmRepository(remoteData).apply { instance = this }
            }
    }

    override fun getPopularMovies(): LiveData<List<FilmEntity>> {
        val movieResult = MutableLiveData<List<FilmEntity>>()

        remoteDataSource.getPopularMovies(object : RemoteDataSource.LoadPopularMoviesCallback{

            override fun onPopularMoviesReceived(movieResponse: List<MovieResultsItem>) {
                val movieList = ArrayList<FilmEntity>()
                for (response in movieResponse) {
                    val movie = FilmEntity(
                        response.id,
                        response.title,
                        response.posterPath,
                        response.releaseDate
                    )
                    movieList.add(movie)
                }
                movieResult.postValue(movieList)
            }
        })
        return movieResult
    }

    override fun getPopularTvShows(): LiveData<List<FilmEntity>> {
        val tvResult = MutableLiveData<List<FilmEntity>>()

        remoteDataSource.getPopularTvShows(object : RemoteDataSource.LoadPopularTvShowsCallback{
            override fun onPopularTvShowsReceived(tvShowResponse: List<TvShowResultsItem>) {
                val tvList = ArrayList<FilmEntity>()
                for (response in tvShowResponse) {
                    val tv = FilmEntity(
                        response.id,
                        response.name,
                        response.posterPath,
                        response.firstAirDate
                    )
                    tvList.add(tv)
                }
                tvResult.postValue(tvList)
            }
        })
        return tvResult
    }


    override fun getMovieDetails(movieId: String): LiveData<FilmDetailEntity> {
        val movieDetailResult = MutableLiveData<FilmDetailEntity>()

        remoteDataSource.getMovieDetails(movieId, object : RemoteDataSource.LoadMovieDetailsCallback{
            override fun onMovieDetailsReceived(movieResponse: DetailMovieResponse) {
                val movieDetail = FilmDetailEntity(
                    movieResponse.id,
                    movieResponse.originalTitle,
                    movieResponse.posterPath,
                    movieResponse.overview,
                    movieResponse.releaseDate,
                    movieResponse.genres,
                    movieResponse.voteAverage.toString()
                )
                movieDetailResult.postValue(movieDetail)
            }
        })
        return movieDetailResult
    }

    override fun getTvShowDetails(tvId: String): LiveData<FilmDetailEntity> {
        val tvDetailResult = MutableLiveData<FilmDetailEntity>()

        remoteDataSource.getTvShowDetails(tvId, object: RemoteDataSource.LoadTvShowDetailsCallback{
            override fun onTvShowDetailsReceived(tvShowResponse: DetailTVResponse) {
                val tvDetail = FilmDetailEntity(
                    tvShowResponse.id,
                    tvShowResponse.name,
                    tvShowResponse.posterPath,
                    tvShowResponse.overview,
                    tvShowResponse.firstAirDate,
                    tvShowResponse.genres,
                    tvShowResponse.voteAverage.toString()
                )
                tvDetailResult.postValue(tvDetail)
            }
        })
        return tvDetailResult
    }
}