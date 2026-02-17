package fr.leboncoin.androidrecruitmenttestapp.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.data.local.model.FavoriteAlbumDto
import fr.leboncoin.data.local.model.OfflineAlbumDto
import fr.leboncoin.data.network.model.AlbumDto
import fr.leboncoin.data.repository.AlbumRepository
import fr.leboncoin.data.repository.FavoriteAlbumRepository
import fr.leboncoin.data.repository.OfflineAlbumRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val offlineAlbumRepository: OfflineAlbumRepository,
    private val favoriteAlbumRepository: FavoriteAlbumRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _albums = MutableStateFlow<List<AlbumDto>>(listOf())
    val albums: StateFlow<List<AlbumDto>> = _albums

    private val _favoriteAlbums = MutableStateFlow<List<FavoriteAlbumDto>>(listOf())
    val favoriteAlbums: StateFlow<List<FavoriteAlbumDto>> = _favoriteAlbums

    init {
        loadAlbums()
        findAllFavoriteAlbums()
    }

    fun findAllFavoriteAlbums() {
        viewModelScope.launch {
            favoriteAlbumRepository.getAllAlbums().collect { favorites ->
                _favoriteAlbums.value = favorites
            }
        }
    }

    fun loadAlbums() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val albums = albumRepository.getAllAlbums()
                _albums.emit(albums)
                offlineAlbumRepository.deleteAll()
                val offlineAlbums = albums.map { album ->
                    OfflineAlbumDto(
                        id = album.id,
                        albumId = album.albumId,
                        title = album.title,
                        url = album.url,
                        thumbnailUrl = album.thumbnailUrl
                    )
                }
                offlineAlbumRepository.insertAll(offlineAlbums)
            } catch (ex: IOException) {
                val offlineAlbums = offlineAlbumRepository.getAllAlbums().first()
                val albumDtos = offlineAlbums.map {
                    AlbumDto(
                        id = it.id,
                        albumId = it.albumId,
                        title = it.title,
                        url = it.url,
                        thumbnailUrl = it.thumbnailUrl
                    )
                }
                _albums.emit(albumDtos)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
