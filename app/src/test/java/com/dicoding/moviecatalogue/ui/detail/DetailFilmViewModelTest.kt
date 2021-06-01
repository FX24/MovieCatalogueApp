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

}