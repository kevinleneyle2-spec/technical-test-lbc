package fr.leboncoin.data.network.model

import android.os.Parcelable
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize

@Serializable
@Parcelize
data class AlbumDto(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
): Parcelable