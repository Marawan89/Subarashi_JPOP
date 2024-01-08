package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity

// Data Access Object (DAO) for AnimeEntity
@Dao
interface AnimeDao {

    // insert anime selected into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(anime: AnimeEntity)

    // get all anime from the database
    @Query("SELECT * FROM anime_table")
    fun getAllAnime(): LiveData<List<AnimeEntity>>

    // delete anime selected from the database
    @Delete
    suspend fun delete(anime: AnimeEntity)

    // check if anime with given id is in favorites
    @Query("SELECT COUNT(*) > 0 FROM anime_table WHERE id = :id")
    fun isFavourite(id: Int): LiveData<Boolean>
}