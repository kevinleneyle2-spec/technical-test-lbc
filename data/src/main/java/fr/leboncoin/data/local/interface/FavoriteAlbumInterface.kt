package fr.leboncoin.data.local.`interface`

import fr.leboncoin.data.local.model.FavoriteAlbumDto
import kotlinx.coroutines.flow.Flow

interface FavoriteAlbumInterface {
    /**
     * Insert an item in the data source by id
     */
    suspend fun insertFavoriteId(id: FavoriteAlbumDto)

    /**
     * Remove an item in the data source by id
     */
    suspend fun deleteFavoriteId(id: Int)

    /**
     * Retrieve all the items from the given data source.
     */
    fun getAllAlbums(): Flow<List<FavoriteAlbumDto>>

    /**
     * Search the item from the given data source by id and send back a boolean.
     */
    suspend fun isFavorite(id: Int): Boolean
}