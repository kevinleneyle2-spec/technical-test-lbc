package fr.leboncoin.data.repository

import fr.leboncoin.data.local.`interface`.OfflineAlbumInterface
import fr.leboncoin.data.local.`interface`.OfflineAlbumDao
import fr.leboncoin.data.local.model.OfflineAlbumDto
import kotlinx.coroutines.flow.Flow

class OfflineAlbumRepository(private val albumDao: OfflineAlbumDao) : OfflineAlbumInterface {

    override suspend fun insertAll(albums: List<OfflineAlbumDto>) {
        albumDao.insertAll(albums)
    }

    override suspend fun updateAlbum(item: OfflineAlbumDto) {
        albumDao.updateItem(item)
    }

    override suspend fun deleteAll() {
        albumDao.deleteAll()
    }

    override suspend fun getAlbumById(id: Int): Flow<OfflineAlbumDto> = albumDao.getItemById(id)


    override suspend fun getAllAlbums(): Flow<List<OfflineAlbumDto>> = albumDao.getAllItems()
}