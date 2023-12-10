package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

@Dao
interface MangaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManga(manga: MangaEntity){
        Log.d("MangaDao", "Inserting manga: ${manga.title}")
    }

    @Query("SELECT * FROM favourite_manga")
    suspend fun getFavouriteManga(): List<MangaEntity>

    @Delete
    suspend fun deleteManga(manga: MangaEntity)


}