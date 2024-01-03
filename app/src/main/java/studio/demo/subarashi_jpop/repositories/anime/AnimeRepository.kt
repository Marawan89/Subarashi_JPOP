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

    override fun getFavouriteAnimeList(): LiveData<List<AnimeEntity>> {
        return localService.getFavouriteAnimeList()
    }
    @WorkerThread
    override suspend fun addAnimeToDB(anime: AnimeEntity){
        localService.addAnimeToFavourites(anime)
    }

    override suspend fun getTopAnime(page: Int, perPage: Int) : AnimeListResponse{
        try {
            val result = animeService.getTopAnime(page, perPage)
            Log.d("AnimeRepository", result.data.toString())
            return result
        } catch (e: HttpException) {
            Log.e("AnimeRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Error: ${e.message}")
            throw e
        }
    }

    override suspend fun searchAnime(query: String): AnimeListResponse {
        try {
            val result = animeService.searchAnime(query)
            Log.d("AnimeRepository", result.data.toString())
            return result
        } catch (e: HttpException) {
            Log.e("AnimeRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Error: ${e.message}")
            throw e
        }
    }
}