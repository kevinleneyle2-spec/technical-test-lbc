package fr.leboncoin.data.local.`interface`

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.leboncoin.data.local.model.FavoriteAlbumDto
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAlbumDao {
    @Query("SELECT * from favorite_album")
    fun getAllAlbums(): Flow<List<FavoriteAlbumDto>>

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertFavoriteId(id: FavoriteAlbumDto)

    @Query("DELETE FROM favorite_album WHERE id = :id")
    suspend fun deleteFavoriteId(id: Int)

    @Query("SELECT COUNT(*) FROM favorite_album WHERE id = :id")
    suspend fun isFavorite(id: Int): Boolean
}