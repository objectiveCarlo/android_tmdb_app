package com.cxd.moviedbapp.common.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cxd.moviedbapp.common.dao.RemoteKeysDao
import com.cxd.moviedbapp.common.models.paging.RemoteKeysEntity
import com.cxd.moviedbapp.features.favorites.datasource.FavoriteMovieDao
import com.cxd.moviedbapp.features.favorites.datasource.FavoriteMovieEntity
import com.cxd.moviedbapp.features.upcoming.datasource.local.UpcomingMovieEntity
import com.cxd.moviedbapp.features.upcoming.datasource.local.UpcomingMoviesDao
import com.cxd.moviedbapp.features.popular.datasource.local.PopularMovieEntity
import com.cxd.moviedbapp.features.popular.datasource.local.PopularMoviesDao

@Database(
    entities = [PopularMovieEntity::class, RemoteKeysEntity::class, FavoriteMovieEntity::class, UpcomingMovieEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract val popularMovieDao: PopularMoviesDao
    abstract val remoteKeysEntityDao: RemoteKeysDao
    abstract val favoriteMovieDao: FavoriteMovieDao
    abstract val upcomingMovieDao: UpcomingMoviesDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(
                    context
                ).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "app_db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}