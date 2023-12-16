package studio.demo.subarashi_jpop.favouriteLocalService

import androidx.lifecycle.LiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity

interface FavouriteLocalService {
    suspend fun addToFavourites(anime: AnimeEntity)
    fun getFavouriteAnime(): LiveData<List<AnimeEntity>>
    suspend fun removeFromFavourites(anime: AnimeEntity)
}
