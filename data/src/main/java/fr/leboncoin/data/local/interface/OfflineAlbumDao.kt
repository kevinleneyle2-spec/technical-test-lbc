package fr.leboncoin.data.local.`interface`

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.leboncoin.data.local.model.OfflineAlbumDto
import kotlinx.coroutines.flow.Flow

@Dao
interface OfflineAlbumDao {
    @Query("SELECT * from albums ORDER BY albumId ASC")
    fun getAllItems(): Flow<List<OfflineAlbumDto>>

    @Query("SELECT * from albums WHERE id = :id")
    fun getItemById(id: Int): Flow<OfflineAlbumDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<OfflineAlbumDto>)

    @Delete
    suspend fun deleteItem(item: OfflineAlbumDto)

    @Update
    suspend fun updateItem(item: OfflineAlbumDto)

    @Query("DELETE FROM albums")
    suspend fun deleteAll()
}