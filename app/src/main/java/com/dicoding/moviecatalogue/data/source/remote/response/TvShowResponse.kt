package com.dicoding.moviecatalogue.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowResponse(

	@field:SerializedName("results")
	val results: List<TvShowResultsItem>,
) : Parcelable

@Parcelize
data class TvShowResultsItem(
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

) : Parcelable
