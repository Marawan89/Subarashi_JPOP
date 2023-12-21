package studio.demo.subarashi_jpop.favouriteLocalService

import androidx.lifecycle.LiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

interface FavouriteLocalService {
    suspend fun addAnimeToFavourites(anime: AnimeEntity)
    fun getFavouriteAnime(): LiveData<List<AnimeEntity>>
    suspend fun removeAnimeFromFavourites(anime: AnimeEntity)

    suspend fun addMangaToFavourites(mangaEntity: MangaEntity)
    fun getFavouriteManga(): LiveData<List<MangaEntity>>
    suspend fun removeMangaFromFavourites(mangaEntity: MangaEntity)
}