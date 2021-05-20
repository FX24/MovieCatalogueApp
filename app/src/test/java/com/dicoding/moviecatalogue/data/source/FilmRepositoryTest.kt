package com.dicoding.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.moviecatalogue.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.utils.FilmData
import com.dicoding.moviecatalogue.utils.LiveDataTestUtil
import org.junit.Test
import org.mockito.Mockito
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.doAnswer
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule

class FilmRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val filmRepository = FakeFilmRepository(remote)

    private val movieResponse = FilmData.generateRemoteMovies()
    private val movieId = movieResponse[0].id
    private val movieDetailResponse = FilmData.generateRemoteMovieDetails(movieId)

    private val tvShowResponse = FilmData.generateRemoteTvShows()
    private val tvShowId = tvShowResponse[0].id
    private val tvShowDetailResponse = FilmData.generateRemoteTvShowDetails(tvShowId)

    @Test
    fun getPopularMovies() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadPopularMoviesCallback)
                .onPopularMoviesReceived(movieResponse)
            null
        }.`when`(remote).getPopularMovies(any())

        val movieEntities = LiveDataTestUtil.getValue(filmRepository.getPopularMovies())
        verify(remote).getPopularMovies(any())

        assertNotNull(movieEntities)
        assertEquals(movieResponse.size.toLong(), movieEntities.size.toLong())
    }

    @Test
    fun getPopularTvShows() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadPopularTvShowsCallback)
                .onPopularTvShowsReceived(tvShowResponse)
            null
        }.`when`(remote).getPopularTvShows(any())

        val tvShowEntities = LiveDataTestUtil.getValue(filmRepository.getPopularTvShows())
        verify(remote).getPopularTvShows(any())

        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntities.size.toLong())
    }

    @Test
    fun getMovieDetails() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMovieDetailsCallback)
                .onMovieDetailsReceived(movieDetailResponse)
            null
        }.`when`(remote).getMovieDetails(eq(movieId.toString()), any())

        val movie = LiveDataTestUtil.getValue(filmRepository.getMovieDetails(movieId.toString()))
        verify(remote).getMovieDetails(eq(movieId.toString()), any())

        assertNotNull(movie)
        assertEquals(movieDetailResponse.id, movie.id)
        assertEquals(movieDetailResponse.originalTitle, movie.title)
        assertEquals(movieDetailResponse.genres, movie.genre)
        assertEquals(movieDetailResponse.overview, movie.overview)
        assertEquals(movieDetailResponse.releaseDate, movie.release_date)
        assertEquals(movieDetailResponse.posterPath, movie.poster)
    }

    @Test
    fun getTvShowDetails() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadTvShowDetailsCallback)
                .onTvShowDetailsReceived(tvShowDetailResponse)
            null
        }.`when`(remote).getTvShowDetails(eq(tvShowId.toString()), any())

        val tvShow = LiveDataTestUtil.getValue(filmRepository.getTvShowDetails(tvShowId.toString()))
        verify(remote).getTvShowDetails(eq(tvShowId.toString()), any())

        assertNotNull(tvShow)
        assertEquals(tvShowDetailResponse.id, tvShow.id)
        assertEquals(tvShowDetailResponse.name, tvShow.title)
        assertEquals(tvShowDetailResponse.genres, tvShow.genre)
        assertEquals(tvShowDetailResponse.overview, tvShow.overview)
        assertEquals(tvShowDetailResponse.firstAirDate, tvShow.release_date)
        assertEquals(tvShowDetailResponse.posterPath, tvShow.poster)
    }
}