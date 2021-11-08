package com.cxd.moviedbapp.features.popular.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movies_table")
data class PopularMovieEntity(
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
