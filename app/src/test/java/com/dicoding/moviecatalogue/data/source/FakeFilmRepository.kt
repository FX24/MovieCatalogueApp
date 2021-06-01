package com.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.data.NetworkBoundResource
import com.dicoding.moviecatalogue.data.source.local.LocalDataSource
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.remote.ApiResponse
import com.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.data.source.remote.response.*
import com.dicoding.moviecatalogue.utils.AppExecutors
import com.dicoding.moviecatalogue.vo.Resource

class FakeFilmRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : FilmDataSource {

    override fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieResultsItem>>(appExecutors) {

            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }


            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
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

    override fun getPopularTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResultsItem>>(appExecutors) {

            public override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
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

    override fun getFavMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getAllFavoriteMovies(), config).build()
    }


    override fun getFavTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getAllFavoriteTvShows(), config).build()
    }

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setMovieFavorite(movie, state)
        }
    }

    override fun setTvShowFavorite(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setTvShowFavorite(tvShow, state)
        }
    }

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