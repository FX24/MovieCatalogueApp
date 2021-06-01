package com.dicoding.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.moviecatalogue.data.source.FilmRepository
import com.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.vo.Resource

class DetailFilmViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    val movieId = MutableLiveData<String>()
    val tvId = MutableLiveData<String>()

    fun setSelectedMovie(movieId: String) {
        this.movieId.value = movieId
    }

    fun setSelectedTvShow(tvId: String) {
        this.tvId.value = tvId
    }

    var tvShow: LiveData<Resource<TvShowEntity>> = Transformations.switchMap(tvId) { tvId ->
        filmRepository.getTvShowDetails(tvId)
    }

    var movies: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId) { movieId ->
        filmRepository.getMovieDetails(movieId)
    }

    fun setFavMovie() {
        val movieResource = movies.value
        if (movieResource != null) {
            val movies = movieResource.data
            if (movies != null) {
                val newState = !movies.favorite
                filmRepository.setMovieFavorite(movies, newState)
            }
        }
    }

    fun setFavTvShow() {
        val tvShowResource = tvShow.value
        if (tvShowResource != null) {
            val tvShows = tvShowResource.data
            if (tvShows != null) {
                val newState = !tvShows.favorite
                filmRepository.setTvShowFavorite(tvShows, newState)
            }
        }
    }
}