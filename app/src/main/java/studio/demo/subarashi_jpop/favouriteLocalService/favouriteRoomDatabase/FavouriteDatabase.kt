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

@Database(entities = [AnimeEntity::class, MangaEntity::class], version = 2)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract fun animeDao() : AnimeDao
    abstract fun mangaDao() : MangaDao

    companion object{
        private const val DATABASE_NAME = "favourite_database"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `favourite_manga` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            "`title` TEXT NOT NULL," +
                            "`imageUrl` TEXT NOT NULL)"
                )
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `favourite_anime` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            "`title` TEXT NOT NULL," +
                            "`imageUrl` TEXT NOT NULL)"
                )
                database.execSQL("ALTER TABLE `favourite_anime` ADD COLUMN id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT 0")
            }
        }
        @Volatile
        private var INSTANCE: FavouriteDatabase? = null

        fun getDatabase(context: Context): FavouriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDatabase::class.java,
                    DATABASE_NAME
                ).addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}