package fr.leboncoin.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.leboncoin.data.local.model.OfflineAlbumDto
import fr.leboncoin.data.local.`interface`.OfflineAlbumDao

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [OfflineAlbumDto::class], version = 1, exportSchema = false)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albumDao(): OfflineAlbumDao

    companion object {
        @Volatile
        private var Instance: AlbumDatabase? = null

        fun getDatabase(context: Context): AlbumDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AlbumDatabase::class.java, "album_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}