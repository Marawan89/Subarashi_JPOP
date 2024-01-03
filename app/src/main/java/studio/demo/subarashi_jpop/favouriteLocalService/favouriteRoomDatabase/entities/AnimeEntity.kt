package studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimeEntity> {
        override fun createFromParcel(parcel: Parcel): AnimeEntity {
            return AnimeEntity(parcel)
        }

        override fun newArray(size: Int): Array<AnimeEntity?> {
            return arrayOfNulls(size)
        }
    }
}

