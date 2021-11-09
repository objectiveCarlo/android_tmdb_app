package com.cxd.moviedbapp.features.movielist.repo

import androidx.paging.PagingData
import com.cxd.moviedbapp.common.models.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieListRepo {
    fun getPopularMovies(): Flow<PagingData<Movie>>
}