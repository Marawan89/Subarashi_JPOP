package studio.demo.subarashi_jpop.repositories.anime

import androidx.lifecycle.LiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeListResponse

interface AnimeRepositoryInterface {
    suspend fun addAnimeToDB(anime: AnimeEntity)
    suspend fun removeAnimeFromDB(anime: AnimeEntity)
    suspend fun getTopAnime(page: Int, perPage: Int): AnimeListResponse
    suspend fun searchAnime(query: String): AnimeListResponse
    fun getFavouriteAnimeList(): LiveData<List<AnimeEntity>>
    fun isAnimeFavourite(id: Int): LiveData<Boolean>

}