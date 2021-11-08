package com.cxd.moviedbapp.features.popular.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cxd.moviedbapp.common.models.data.MovieResponse

@Dao
interface PopularMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatchMovies(list: List<PopularMovieEntity>)

    @Query("SELECT * FROM popular_movies_table")
    fun getPopularMovies(): PagingSource<Int, PopularMovieEntity>

    @Query("DELETE FROM popular_movies_table")
    suspend fun clear()

    @Query("SELECT COUNT(id) from popular_movies_table")
    suspend fun count(): Int
}