package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities

import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_manga")
data class MangaEntity (
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String
)