package com.dicoding.moviecatalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.utils.FilmData
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<List<TvShowEntity>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(filmRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = FilmData.generateMovies()
        val movies = MutableLiveData<List<TvShowEntity>>()
        movies.value = dummyMovies

        `when`(filmRepository.getPopularMovies()).thenReturn(movies)

        val movieEntities = viewModel.getMovies().value
        verify(filmRepository).getPopularMovies()

        assertNotNull(movieEntities)
        assertEquals(10, movieEntities?.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}