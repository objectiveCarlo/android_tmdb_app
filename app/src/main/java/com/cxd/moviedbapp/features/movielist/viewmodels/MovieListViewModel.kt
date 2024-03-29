package com.cxd.moviedbapp.features.movielist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cxd.moviedbapp.common.models.domain.Movie
import com.cxd.moviedbapp.features.movielist.repo.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieListRepository
) : ViewModel() {
    private var currentResult: Flow<PagingData<Movie>>? = null

    @ExperimentalPagingApi
    fun getPopularMovies(): Flow<PagingData<Movie>> {
        val newResult: Flow<PagingData<Movie>> =
            repository.getPopularMovies().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

    @ExperimentalPagingApi
    fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        val newResult: Flow<PagingData<Movie>> =
            repository.getUpcomingMovies().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }
}