package com.cxd.moviedbapp.features.favorites.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies_table")
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val isFavorite: Boolean
)