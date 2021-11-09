package com.cxd.moviedbapp.features.movielist.datasource.remote

import com.cxd.moviedbapp.common.models.data.MovieResponse
import com.cxd.moviedbapp.common.models.network.ResponseItems
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListService {
    /**
     * @return List of [MovieResponse]
     *
     * @param language the language to obtain the data.
     * @param page the current page of items.
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): ResponseItems<MovieResponse>

    /**
     * @return List of [MovieResponse]
     *
     * @param language the language to obtain the data.
     * @param page the current page of items.
     */
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): ResponseItems<MovieResponse>
}