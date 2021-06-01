package com.dicoding.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.moviecatalogue.data.source.local.LocalDataSource
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.data.source.remote.response.GenresItem
import com.dicoding.moviecatalogue.utils.AppExecutors
import com.dicoding.moviecatalogue.utils.FilmData
import com.dicoding.moviecatalogue.utils.LiveDataTestUtil
import com.dicoding.moviecatalogue.utils.PagedListUtil
import com.dicoding.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class FilmRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val filmRepository = FakeFilmRepository(remote, local, appExecutors)

    private val movieResponse = FilmData.generateRemoteMovies()
    private val movieId = movieResponse[0].id
    private val movieDetailResponse = FilmData.generateRemoteMovieDetails(movieId)

    private val tvShowResponse = FilmData.generateRemoteTvShows()
    private val tvShowId = tvShowResponse[0].id
    private val tvShowDetailResponse = FilmData.generateRemoteTvShowDetails(tvShowId)

    @Test
    fun getPopularMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)

        filmRepository.getPopularMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(FilmData.generateMovies()))
        verify(local).getAllMovies()

        assertNotNull(movieEntities.data)
        assertEquals(movieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getPopularTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShows()).thenReturn(dataSourceFactory)

        filmRepository.getPopularTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(FilmData.generateTvShows()))
        verify(local).getAllTvShows()

        assertNotNull(tvShowEntities.data)
        assertEquals(movieResponse.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieDetails() {
        val dummyMovieDetails = MutableLiveData<MovieEntity>()
        dummyMovieDetails.value = FilmData.generateMovieDetails()

        `when`(local.getMovieWithDetailbyId(movieId.toString())).thenReturn(dummyMovieDetails)

        val movie = LiveDataTestUtil.getValue(filmRepository.getMovieDetails(movieId.toString()))
        verify(local).getMovieWithDetailbyId(movieId.toString())

        assertNotNull(movie)
        assertEquals(movieDetailResponse.id, movie.data?.id)
        assertEquals(movieDetailResponse.originalTitle, movie.data?.title)
        assertEquals(getgenre(movieDetailResponse.genres), movie.data?.genre)
        assertEquals(movieDetailResponse.overview, movie.data?.overview)
        assertEquals(movieDetailResponse.releaseDate, movie.data?.release_date)
        assertEquals(movieDetailResponse.posterPath, movie.data?.poster)
    }

    @Test
    fun getTvShowDetails() {
        val dummyTvShowDetails = MutableLiveData<TvShowEntity>()
        dummyTvShowDetails.value = FilmData.generateTvShowDetails()

        `when`(local.getTvShowWithDetailbyId(tvShowId.toString())).thenReturn(dummyTvShowDetails)

        val tvShow = LiveDataTestUtil.getValue(filmRepository.getTvShowDetails(tvShowId.toString()))
        verify(local).getTvShowWithDetailbyId(tvShowId.toString())

        assertNotNull(tvShow)
        assertEquals(tvShowDetailResponse.id, tvShow.data?.id)
        assertEquals(tvShowDetailResponse.name, tvShow.data?.title)
        assertEquals(getgenre(tvShowDetailResponse.genres), tvShow.data?.genre)
        assertEquals(tvShowDetailResponse.overview, tvShow.data?.overview)
        assertEquals(tvShowDetailResponse.firstAirDate, tvShow.data?.release_date)
        assertEquals(tvShowDetailResponse.posterPath, tvShow.data?.poster)
    }

    @Test
    fun getFavMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllFavoriteMovies()).thenReturn(dataSourceFactory)

        filmRepository.getFavMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(FilmData.generateMovies()))
        verify(local).getAllFavoriteMovies()

        assertNotNull(movieEntities.data)
        assertEquals(movieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllFavoriteTvShows()).thenReturn(dataSourceFactory)

        filmRepository.getFavTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(FilmData.generateTvShows()))
        verify(local).getAllFavoriteTvShows()

        assertNotNull(tvShowEntities.data)
        assertEquals(movieResponse.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    private fun getgenre(genres: List<GenresItem>?): String {
        var genretxt = ""

        for (i in genres?.indices!!) {
            genretxt = if (i == 0) {
                genres[i].name
            } else {
                "$genretxt, ${genres[i].name}"
            }
        }
        return genretxt
    }

}