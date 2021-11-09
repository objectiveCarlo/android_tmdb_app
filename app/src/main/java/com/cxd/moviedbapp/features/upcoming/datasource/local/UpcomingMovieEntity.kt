package com.cxd.moviedbapp.features.upcoming.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_movies_table")
data class UpcomingMovieEntity(
    @PrimaryKey(autoGenerate = false)
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
)