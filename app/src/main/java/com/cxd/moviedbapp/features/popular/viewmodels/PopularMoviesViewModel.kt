package com.cxd.moviedbapp.features.popular.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cxd.moviedbapp.common.models.domain.Movie
import com.cxd.moviedbapp.features.popular.repo.PopularMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val repository: PopularMoviesRepository
) : ViewModel() {
    private var currentResult: Flow<PagingData<Movie>>? = null

    @ExperimentalPagingApi
    fun getMovies(): Flow<PagingData<Movie>> {
        val newResult: Flow<PagingData<Movie>> =
            repository.getMovies().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

}