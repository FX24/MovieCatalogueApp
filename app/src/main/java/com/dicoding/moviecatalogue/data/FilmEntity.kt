package com.dicoding.moviecatalogue.data

import com.dicoding.moviecatalogue.data.source.remote.response.GenresItem

data class FilmEntity(
    val id: Int,
    val title: String,
    val poster: String,
    val release_date: String
)
