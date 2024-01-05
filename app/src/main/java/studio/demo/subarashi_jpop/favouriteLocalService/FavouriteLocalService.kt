package studio.demo.subarashi_jpop.favouriteLocalService

import androidx.lifecycle.LiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

interface FavouriteLocalService {
    suspend fun addAnimeToFavourites(anime: AnimeEntity)
    fun getFavouriteAnime(): LiveData<List<AnimeEntity>>
    suspend fun removeAnimeFromFavourites(anime: AnimeEntity)
    fun getFavouriteAnimeList(): LiveData<List<AnimeEntity>>
    fun isAnimeFavourite(id: Int): LiveData<Boolean>

    suspend fun addMangaToFavourites(manga: MangaEntity)
    fun getFavouriteManga(): LiveData<List<MangaEntity>>
    suspend fun removeMangaFromFavourites(manga: MangaEntity)
    fun getFavouriteMangaList(): LiveData<List<MangaEntity>>
    fun isMangaFavourite(id: Int): LiveData<Boolean>


}