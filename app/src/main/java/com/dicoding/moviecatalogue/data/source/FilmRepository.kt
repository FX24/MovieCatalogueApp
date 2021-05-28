package com.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.dicoding.moviecatalogue.data.NetworkBoundResource
import com.dicoding.moviecatalogue.data.source.local.LocalDataSource
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.remote.ApiResponse
import com.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.data.source.remote.response.*
import com.dicoding.moviecatalogue.utils.AppExecutors
import com.dicoding.moviecatalogue.vo.Resource

class FilmRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : FilmDataSource {

    companion object {
        @Volatile
        private var instance: FilmRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): FilmRepository =
            instance ?: synchronized(this) {
                instance ?: FilmRepository(remoteData, localData, appExecutors).apply {
                    instance = this
                }
            }
    }

//    override fun getPopularMovies(): LiveData<Resource<List<FilmEntity>>> {
//        val movieResult = MutableLiveData<List<FilmEntity>>()
//
//        remoteDataSource.getPopularMovies(object : RemoteDataSource.LoadPopularMoviesCallback{
//
//            override fun onPopularMoviesReceived(movieResponse: List<MovieResultsItem>) {
//                val movieList = ArrayList<FilmEntity>()
//                for (response in movieResponse) {
//                    val movie = FilmEntity(
//                        response.id,
//                        response.title,
//                        response.posterPath,
//                        response.releaseDate
//                    )
//                    movieList.add(movie)
//                }
//                movieResult.postValue(movieList)
//            }
//        })
//        return movieResult
//    }

    override fun getPopularMovies(): LiveData<Resource<List<MovieEntity>>> {
        return object :
            NetworkBoundResource<List<MovieEntity>, List<MovieResultsItem>>(appExecutors) {

            public override fun loadFromDB(): LiveData<List<MovieEntity>> =
                localDataSource.getAllMovies()

            override fun shouldFetch(data: List<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResultsItem>>> =
                remoteDataSource.getPopularMovies()

            public override fun saveCallResult(movieResponse: List<MovieResultsItem>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in movieResponse) {
                    val movie = MovieEntity(
                        response.id,
                        response.title,
                        response.posterPath,
                        response.releaseDate,
                        (false),
                        "",
                        "",
                        ""
                    )
                    movieList.add(movie)
                }

                localDataSource.insertMovie(movieList)
            }
        }.asLiveData()
    }


    override fun getPopularTvShows(): LiveData<Resource<List<TvShowEntity>>> {
        return object :
            NetworkBoundResource<List<TvShowEntity>, List<TvShowResultsItem>>(appExecutors) {

            public override fun loadFromDB(): LiveData<List<TvShowEntity>> =
                localDataSource.getAllTvShows()

            override fun shouldFetch(data: List<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvShowResultsItem>>> =
                remoteDataSource.getPopularTvShows()

            public override fun saveCallResult(data: List<TvShowResultsItem>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val tvShow = TvShowEntity(
                        response.id,
                        response.name,
                        response.posterPath,
                        response.firstAirDate,
                        (false),
                        "",
                        "",
                        ""
                    )
                    tvShowList.add(tvShow)
                }
                localDataSource.insertTvShow(tvShowList)
            }
        }.asLiveData()
    }

    override fun getMovieDetails(movieId: String): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, DetailMovieResponse>(appExecutors) {

            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovieWithDetailbyId(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data != null && data.overview == ""

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getMovieDetails(movieId)

            override fun saveCallResult(data: DetailMovieResponse) {

                val movieDetail = MovieEntity(
                    data.id,
                    data.originalTitle,
                    data.posterPath,
                    data.releaseDate,
                    (false),
                    getgenre(data.genres),
                    data.overview,
                    data.voteAverage.toString()
                )

                localDataSource.updateMovie(movieDetail)
            }
        }.asLiveData()
    }

    override fun getTvShowDetails(tvId: String): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, DetailTVResponse>(appExecutors) {

            override fun loadFromDB(): LiveData<TvShowEntity> =
                localDataSource.getTvShowWithDetailbyId(tvId)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data != null && data.overview == ""

            override fun createCall(): LiveData<ApiResponse<DetailTVResponse>> =
                remoteDataSource.getTvShowDetails(tvId)

            override fun saveCallResult(data: DetailTVResponse) {

                val tvDetail = TvShowEntity(
                    data.id,
                    data.name,
                    data.posterPath,
                    data.firstAirDate,
                    (false),
                    getgenre(data.genres),
                    data.overview,
                    data.voteAverage.toString()
                )

                localDataSource.updateTvShow(tvDetail)
            }
        }.asLiveData()
    }

    override fun getFavMovies(): LiveData<List<MovieEntity>> =
        localDataSource.getAllFavoriteMovies()


    override fun getFavTvShows(): LiveData<List<TvShowEntity>> =
        localDataSource.getAllFavoriteTvShows()


    override fun setMovieBookmark(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setMovieFavorite(movie, state)
        }
    }

    override fun setTvShowBookmark(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setTvShowFavorite(tvShow, state)
        }
    }

//    override fun getMovieDetails(movieId: String): LiveData<FilmDetailEntity> {
//        val movieDetailResult = MutableLiveData<FilmDetailEntity>()
//
//        remoteDataSource.getMovieDetails(movieId, object : RemoteDataSource.LoadMovieDetailsCallback{
//            override fun onMovieDetailsReceived(movieResponse: DetailMovieResponse) {
//                val movieDetail = FilmDetailEntity(
//                    movieResponse.id,
//                    movieResponse.originalTitle,
//                    movieResponse.posterPath,
//                    movieResponse.overview,
//                    movieResponse.releaseDate,
//                    movieResponse.genres,
//                    movieResponse.voteAverage.toString()
//                )
//                movieDetailResult.postValue(movieDetail)
//            }
//        })
//        return movieDetailResult
//    }
    private fun getgenre(genres: List<GenresItem>?): String {
        var genretxt = ""

        for (i in genres?.indices!!) {
            if (i == 0) {
                genretxt = genres.get(i).name
            } else {
                genretxt = "$genretxt, ${genres.get(i).name}"
            }
        }
        return genretxt
    }
}