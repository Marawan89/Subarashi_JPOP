package studio.demo.subarashi_jpop.repositories

import android.util.Log
import retrofit2.HttpException
import studio.demo.subarashi_jpop.remote.anime.AnimeService
import studio.demo.subarashi_jpop.remote.anime.model.AnimeListResponse

class AnimeRepository(private val animeService: AnimeService) {
    private var currentPage = 1
    private val perPage = 10

    suspend fun getTopAnime(page: Int = currentPage, perPage: Int = this.perPage) : AnimeListResponse{
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

    suspend fun searchAnime(query: String): AnimeListResponse {
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