package com.dicoding.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.utils.FilmData
import com.dicoding.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailFilmViewModelTest {

    private lateinit var viewModel: DetailFilmViewModel

    private val dummyMovie = FilmData.generateMovieDetails()
    private val dummyTvShow = FilmData.generateTvShowDetails()
    private val movieId = dummyMovie.id
    private val tvId = dummyTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<TvShowEntity>>


    @Before
    fun setUp() {
        viewModel = DetailFilmViewModel(filmRepository)
        viewModel.setSelectedMovie(movieId.toString())
        viewModel.setSelectedTvShow(tvId.toString())
        viewModel.setFavMovie()
        viewModel.setFavTvShow()
    }

    @Test
    fun getMovieDetails() {
        val dummyMovieDetails = Resource.success(dummyMovie)
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyMovieDetails

        `when`(filmRepository.getMovieDetails(movieId.toString())).thenReturn(movie)

//        val movieEntity = viewModel.getMovieDetails().value as FilmDetailEntity
//        verify(filmRepository).getMovieDetails(movieId.toString())
//
//        assertNotNull(movieEntity)
//        assertEquals(dummyMovie.id, movieEntity.id)
//        assertEquals(dummyMovie.poster, movieEntity.poster)
//        assertEquals(dummyMovie.title, movieEntity.title)
//        assertEquals(dummyMovie.genre, movieEntity.genre)
//        assertEquals(dummyMovie.overview, movieEntity.overview)
//        assertEquals(dummyMovie.release_date, movieEntity.release_date)

        viewModel.movies.observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovieDetails)
    }

    @Test
    fun getTvShowDetails() {
        val dummyTvShowDetails = Resource.success(dummyTvShow)
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dummyTvShowDetails

        `when`(filmRepository.getTvShowDetails(tvId.toString())).thenReturn(tvShow)

        viewModel.tvShow.observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShowDetails)
    }

    @Test
    fun setFavMovie() {
        val dummyMovieDetails = Resource.success(dummyMovie)
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyMovieDetails

        val state = !dummyMovieDetails.data?.favorite!!

        `when`(filmRepository.getMovieDetails(movieId.toString())).thenReturn(movie)

        viewModel.movies = filmRepository.getMovieDetails(movieId.toString())
        viewModel.setFavMovie()
        verify(filmRepository).setMovieFavorite(movie.value!!.data as MovieEntity, true)
    }

    @Test
    fun setFavTvShow() {
        val dummyTvShowDetails = Resource.success(dummyTvShow)
        val movie = MutableLiveData<Resource<TvShowEntity>>()
        movie.value = dummyTvShowDetails

        val state = !dummyTvShowDetails.data?.favorite!!

        `when`(filmRepository.getTvShowDetails(movieId.toString())).thenReturn(movie)

        viewModel.tvShow = filmRepository.getTvShowDetails(movieId.toString())
        viewModel.setFavTvShow()
        verify(filmRepository).setTvShowFavorite(movie.value!!.data as TvShowEntity, true)
    }

}