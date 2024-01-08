package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.MangaDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

// database class for handling Anime and Manga entities
@Database(
    entities = [AnimeEntity::class, MangaEntity::class],
    version = 3, // i made a couple of changes on the db that's why i am on the third version
    exportSchema = false
)
abstract class FavouriteDatabase : RoomDatabase() {

    // abstract functions for accessing DAOs
    abstract fun animeDao(): AnimeDao
    abstract fun mangaDao(): MangaDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteDatabase? = null

        // function to get the singleton instance of the database
        fun getDatabase(context: Context): FavouriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDatabase::class.java,
                    "favourite_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}