package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeEntity){
        Log.d("AnimeDao", "Inserting anime: ${anime.title}")
    }

    @Query("SELECT * FROM favourite_anime")
    suspend fun getFavouriteAnime(): List<AnimeEntity>

    @Delete
    suspend fun deleteAnime(anime: AnimeEntity)
}