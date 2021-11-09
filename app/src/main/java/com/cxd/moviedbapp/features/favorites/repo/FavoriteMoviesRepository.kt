package com.cxd.moviedbapp.features.favorites.repo

import com.cxd.moviedbapp.common.db.AppDataBase
import com.cxd.moviedbapp.features.favorites.datasource.FavoriteMovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMoviesRepository @Inject constructor(
    private val db: AppDataBase
){
    suspend fun addFavoriteState(id: Int, isFavorite: Boolean) {
        db.favoriteMovieDao.insertFavoriteMovie(FavoriteMovieEntity(id, isFavorite))
    }

    suspend fun isFavorite(id: Int): Boolean {
       val favoriteMovieEntity = db.favoriteMovieDao.getFavorite(id)
        return favoriteMovieEntity?.isFavorite ?: false
    }
}
