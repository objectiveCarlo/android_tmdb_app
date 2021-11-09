package com.cxd.moviedbapp.features.upcoming.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UpcomingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatchMovies(list: List<UpcomingMovieEntity>)

    @Query("SELECT * FROM upcoming_movies_table")
    fun getUpcomingMovies(): PagingSource<Int, UpcomingMovieEntity>

    @Query("DELETE FROM upcoming_movies_table")
    suspend fun clear()

    @Query("SELECT COUNT(id) from upcoming_movies_table")
    suspend fun count(): Int
}