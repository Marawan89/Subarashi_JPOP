package studio.demo.subarashi_jpop.repositories

import android.util.Log
import retrofit2.HttpException
import studio.demo.subarashi_jpop.remote.anime.AnimeService
import studio.demo.subarashi_jpop.remote.anime.model.AnimeListResponse

class AnimeRepository(private val animeService: AnimeService) {
    private var currentPage = 1
    private val perPage = 10

    suspend fun getTopAnime(): AnimeListResponse {
        try {
            val result = animeService.getTopAnime(currentPage, perPage)
            Log.d("AnimeRepository", result.data.toString())
            currentPage++
            return result
        } catch (e: HttpException) {
            // Gestione degli errori HTTP (ad esempio, 404 Not Found)
            Log.e("AnimeRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            // Gestione di altri tipi di errori
            Log.e("AnimeRepository", "Error: ${e.message}")
            throw e
        }
    }

    fun resetPage() {
        currentPage = 1
    }
}
