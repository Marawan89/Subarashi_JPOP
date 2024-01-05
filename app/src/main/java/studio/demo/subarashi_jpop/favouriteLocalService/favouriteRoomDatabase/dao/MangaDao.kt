package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

@Dao
interface MangaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(manga: MangaEntity)
    @Query("SELECT * FROM manga_table")
    fun getAllManga(): LiveData<List<MangaEntity>>
    @Delete
    suspend fun delete(manga: MangaEntity)
    @Query("SELECT COUNT(*) > 0 FROM manga_table WHERE id = :id")
    fun isFavourite(id: Int): LiveData<Boolean>
}