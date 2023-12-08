package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities

import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_anime")
data class AnimeEntity (
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String,
)