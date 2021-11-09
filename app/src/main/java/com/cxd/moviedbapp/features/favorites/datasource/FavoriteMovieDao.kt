package com.cxd.moviedbapp.features.favorites.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies_table WHERE id=:id")
    suspend fun getFavorite(id: Int): FavoriteMovieEntity?
}