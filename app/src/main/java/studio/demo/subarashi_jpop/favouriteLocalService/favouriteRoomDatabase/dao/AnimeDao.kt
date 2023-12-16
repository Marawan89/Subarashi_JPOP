package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(anime: AnimeEntity)

    @Query("SELECT * FROM anime_table")
    fun getAllAnime(): LiveData<List<AnimeEntity>>

    @Delete
    suspend fun delete(anime: AnimeEntity)
}



