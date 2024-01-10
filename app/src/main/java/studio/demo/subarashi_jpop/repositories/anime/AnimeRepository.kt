package studio.demo.subarashi_jpop.repositories.anime

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import retrofit2.HttpException
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.AnimeService
import studio.demo.subarashi_jpop.remote.anime.model.AnimeListResponse

class AnimeRepository(
    private val animeService: AnimeService,
    private val localService: RoomFavouriteLocalService
) : AnimeRepositoryInterface {

    // check if the anime is in favorites or not
    override fun isAnimeFavourite(id: Int): LiveData<Boolean> {
        return localService.isAnimeFavourite(id)
    }

    // get the anime favourite list from the local database
    override fun getFavouriteAnimeList(): LiveData<List<AnimeEntity>> {
        return localService.getFavouriteAnimeList()
    }

    // add anime to the local database in a background thread
    @WorkerThread
    override suspend fun addAnimeToDB(anime: AnimeEntity) {
        localService.addAnimeToFavourites(anime)
    }

    // remove anime from the local database in a background thread
    @WorkerThread
    override suspend fun removeAnimeFromDB(anime: AnimeEntity) {
        localService.removeAnimeFromFavourites(anime)
    }

    // get the top anime from the remote API
    override suspend fun getTopAnime(page: Int, perPage: Int): AnimeListResponse {
        try {
            return animeService.getTopAnime(page, perPage)
        } catch (e: HttpException) {
            Log.e("AnimeRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Error: ${e.message}")
            throw e
        }
    }

    // search for anime based on the provided query
    override suspend fun searchAnime(query: String): AnimeListResponse {
        try {
            return animeService.searchAnime(query)
        } catch (e: HttpException) {
            Log.e("AnimeRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Error: ${e.message}")
            throw e
        }
    }
}
