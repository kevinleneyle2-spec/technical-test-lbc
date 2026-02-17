package fr.leboncoin.data.repository

import fr.leboncoin.data.local.`interface`.FavoriteAlbumDao
import fr.leboncoin.data.local.`interface`.FavoriteAlbumInterface
import fr.leboncoin.data.local.model.FavoriteAlbumDto
import kotlinx.coroutines.flow.Flow

class FavoriteAlbumRepository(private val favoriteAlbumDao: FavoriteAlbumDao) :
    FavoriteAlbumInterface {

    override suspend fun insertFavoriteId(id: FavoriteAlbumDto) {
        favoriteAlbumDao.insertFavoriteId(id)
    }

    override suspend fun deleteFavoriteId(id: Int) {
        favoriteAlbumDao.deleteFavoriteId(id)
    }

    override fun getAllAlbums(): Flow<List<FavoriteAlbumDto>> =
        favoriteAlbumDao.getAllAlbums()

    override suspend fun isFavorite(id: Int): Boolean =
        favoriteAlbumDao.isFavorite(id)
}