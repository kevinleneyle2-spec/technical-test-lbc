package fr.leboncoin.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class OfflineAlbumDto(
    @PrimaryKey val id: Int,
    @ColumnInfo val albumId: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val url: String,
    @ColumnInfo val thumbnailUrl: String
)