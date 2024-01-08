package studio.demo.subarashi_jpop.favouriteLocalService

import androidx.lifecycle.LiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

// interface defining methods for managing favourite anime and manga
interface FavouriteLocalService {
    // adds an anime to the favourite list
    suspend fun addAnimeToFavourites(anime: AnimeEntity)

    // removes an anime from the favourite list
    suspend fun removeAnimeFromFavourites(anime: AnimeEntity)

    // gets the favourite anime list
    fun getFavouriteAnimeList(): LiveData<List<AnimeEntity>>

    // checks if the given anime is in the favourite list
    fun isAnimeFavourite(id: Int): LiveData<Boolean>

    // adds a manga to the favourite list
    suspend fun addMangaToFavourites(manga: MangaEntity)

    // removes a manga from the favourite list
    suspend fun removeMangaFromFavourites(manga: MangaEntity)

    // gets the manga favourite list
    fun getFavouriteMangaList(): LiveData<List<MangaEntity>>

    // checks if the given manga is in the favourite list
    fun isMangaFavourite(id: Int): LiveData<Boolean>
}
