package com.cxd.moviedbapp.common.models.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val isAdultOnly: Boolean,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val image: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val originalLanguage: String
): Parcelable

