package fr.leboncoin.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.leboncoin.data.local.model.FavoriteAlbumDto
import fr.leboncoin.data.local.`interface`.FavoriteAlbumDao

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [FavoriteAlbumDto::class], version = 1, exportSchema = false)
abstract class FavoriteAlbumDatabase : RoomDatabase() {

    abstract fun albumDao(): FavoriteAlbumDao

    companion object {
        @Volatile
        private var Instance: FavoriteAlbumDatabase? = null

        fun getDatabase(context: Context): FavoriteAlbumDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FavoriteAlbumDatabase::class.java,
                    "favorite_album_database"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}