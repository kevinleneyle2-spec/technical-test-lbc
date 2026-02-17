package fr.leboncoin.data.local.`interface`

import fr.leboncoin.data.local.model.OfflineAlbumDto
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface OfflineAlbumInterface {
    /**
     * Retrieve all the items from the given data source.
     */
    suspend fun getAllAlbums(): Flow<List<OfflineAlbumDto>>

    /**
     * Insert all items in the data source
     */
    suspend fun insertAll(albums: List<OfflineAlbumDto>)

    /**
     * Update item in the data source
     */
    suspend fun updateAlbum(item: OfflineAlbumDto)

    /**
     * Remove all items in the data source
     */
    suspend fun deleteAll()

    /**
     * Retrieve an item in the data source by id
     */
    suspend fun getAlbumById(id: Int): Flow<OfflineAlbumDto>
}