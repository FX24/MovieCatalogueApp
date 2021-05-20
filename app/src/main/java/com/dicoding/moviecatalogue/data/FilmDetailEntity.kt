package com.dicoding.moviecatalogue.data

import com.dicoding.moviecatalogue.data.source.remote.response.GenresItem

data class FilmDetailEntity(
    val id: Int,
    val title: String,
    val poster: String,
    val overview: String,
    val release_date: String,
    val genre: List<GenresItem>?,
    val rating: String
)

