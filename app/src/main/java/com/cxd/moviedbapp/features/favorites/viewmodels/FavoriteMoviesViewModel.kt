package com.cxd.moviedbapp.features.favorites.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cxd.moviedbapp.features.favorites.repo.FavoriteMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val repository: FavoriteMoviesRepository
) : ViewModel() {
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun syncFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO){
            _isFavorite.postValue(repository.isFavorite(id))
        }
    }

    fun favorite(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addFavoriteState(id, isFavorite)
            _isFavorite.postValue(isFavorite)
        }
    }
}