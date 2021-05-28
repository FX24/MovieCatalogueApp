package com.dicoding.moviecatalogue.data.source.local.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.moviecatalogue.data.source.remote.response.GenresItem

@Entity(tableName = "movieentity")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "poster")
    val poster: String,

    @ColumnInfo(name = "release_date")
    val release_date: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,

    @ColumnInfo(name = "genre")
    val genre: List<GenresItem> ?= null,

    @ColumnInfo(name = "overview")
    val overview: String = "",

    @ColumnInfo(name = "rating")
    var rating: String = ""

)