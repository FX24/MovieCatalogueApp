package com.dicoding.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.moviecatalogue.data.source.local.entity.FilmDetailEntity
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.utils.FilmData
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailFilmViewModelTest {

    private lateinit var viewModel : DetailFilmViewModel

    private val dummyMovie = FilmData.generateMovieDetails()
    private val dummyTvShow = FilmData.generateTvShowDetails()
    private val movieId = dummyMovie.id
    private val tvId = dummyTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var movieObserver: Observer<FilmDetailEntity>

    @Mock
    private lateinit var tvShowObserver: Observer<FilmDetailEntity>



    @Before
    fun setUp() {
        viewModel = DetailFilmViewModel(filmRepository)
        viewModel.setSelectedMovie(movieId.toString())
        viewModel.setSelectedTvShow(tvId.toString())
    }

    @Test
    fun getMovieDetails() {
        val movie = MutableLiveData<FilmDetailEntity>()
        movie.value = dummyMovie

        `when`(filmRepository.getMovieDetails(movieId.toString())).thenReturn(movie)

        val movieEntity = viewModel.getMovieDetails().value as FilmDetailEntity
        verify(filmRepository).getMovieDetails(movieId.toString())

        assertNotNull(movieEntity)
        assertEquals(dummyMovie.id, movieEntity.id)
        assertEquals(dummyMovie.poster, movieEntity.poster)
        assertEquals(dummyMovie.title, movieEntity.title)
        assertEquals(dummyMovie.genre, movieEntity.genre)
        assertEquals(dummyMovie.overview, movieEntity.overview)
        assertEquals(dummyMovie.release_date, movieEntity.release_date)

        viewModel.getMovieDetails().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getTvShowDetails() {
        val tvShow = MutableLiveData<FilmDetailEntity>()
        tvShow.value = dummyTvShow

        `when`(filmRepository.getTvShowDetails(tvId.toString())).thenReturn(tvShow)

        val tvShowEntity = viewModel.getTvShowDetails().value as FilmDetailEntity
        verify(filmRepository).getTvShowDetails(tvId.toString())

        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShow.id, tvShowEntity.id)
        assertEquals(dummyTvShow.poster, tvShowEntity.poster)
        assertEquals(dummyTvShow.title, tvShowEntity.title)
        assertEquals(dummyTvShow.genre, tvShowEntity.genre)
        assertEquals(dummyTvShow.overview, tvShowEntity.overview)
        assertEquals(dummyTvShow.release_date, tvShowEntity.release_date)

        viewModel.getTvShowDetails().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShow)
    }

}