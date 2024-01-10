package studio.demo.subarashi_jpop.repositories.anime

import androidx.lifecycle.LiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeListResponse

interface AnimeRepositoryInterface {
    // add anime to the local database in a background thread
    suspend fun addAnimeToDB(anime: AnimeEntity)

    // remove anime from the local database in a background thread
    suspend fun removeAnimeFromDB(anime: AnimeEntity)

    // get the top anime from the remote API
    suspend fun getTopAnime(page: Int, perPage: Int): AnimeListResponse

    // search for anime based on the provided query
    suspend fun searchAnime(query: String): AnimeListResponse

    // get the anime favourite list from the local database
    fun getFavouriteAnimeList(): LiveData<List<AnimeEntity>>

    // check if the anime is in favorites or not
    fun isAnimeFavourite(id: Int): LiveData<Boolean>
}
