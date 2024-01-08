package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

// Data Access Object (DAO) for MangaEntity
@Dao
interface MangaDao {

    // insert manga selected into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(manga: MangaEntity)

    // get all manga from the database
    @Query("SELECT * FROM manga_table")
    fun getAllManga(): LiveData<List<MangaEntity>>

    // delete manga selected from the database
    @Delete
    suspend fun delete(manga: MangaEntity)

    // check if manga with given id is in favorites
    @Query("SELECT COUNT(*) > 0 FROM manga_table WHERE id = :id")
    fun isFavourite(id: Int): LiveData<Boolean>
}