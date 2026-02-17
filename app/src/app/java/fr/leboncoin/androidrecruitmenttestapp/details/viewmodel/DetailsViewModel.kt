package fr.leboncoin.androidrecruitmenttestapp.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.data.local.model.FavoriteAlbumDto
import fr.leboncoin.data.network.model.AlbumDto
import fr.leboncoin.data.repository.FavoriteAlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val favoriteAlbumRepository: FavoriteAlbumRepository
) : ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _error = MutableStateFlow<Exception?>(null)
    val error = _error.asStateFlow()

    fun checkFavoriteStatus(album: AlbumDto) {
        viewModelScope.launch {
            _isFavorite.value =
                favoriteAlbumRepository.isFavorite(album.id)
        }
    }

    fun onFavoriteClick(album: AlbumDto) {
        viewModelScope.launch {
            try {
                val newState = !_isFavorite.value
                if (!newState) {
                    favoriteAlbumRepository.deleteFavoriteId(album.id)
                } else {
                    favoriteAlbumRepository.insertFavoriteId(FavoriteAlbumDto(album.id))
                }
                _isFavorite.value = newState
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }

    fun dismissErrorDialog() {
        _error.value = null
    }
}