package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.MangaDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity

@Database(entities = [AnimeEntity::class], version = 1)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract fun animeDao() : AnimeDao
    abstract fun mangaDao() : MangaDao

    companion object{
        @Volatile
        private var INSTANCE: FavouriteDatabase? = null

        fun getInstance(context: Context): FavouriteDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDatabase::class.java,
                    "favourite_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}