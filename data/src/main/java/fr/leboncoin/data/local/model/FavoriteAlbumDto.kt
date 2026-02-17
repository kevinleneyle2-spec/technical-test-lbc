package fr.leboncoin.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_album")
data class FavoriteAlbumDto(
    @PrimaryKey val id: Int
)