package studio.demo.subarashi_jpop.repositories

import android.util.Log
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.remote.anime.AnimeService
import studio.demo.subarashi_jpop.remote.anime.model.AnimeListResponse

class AnimeRepository(private val animeService: AnimeService) {
    private var currentPage = 1
    private var perPage = 10

    suspend fun getTopAnime(): AnimeListResponse {
        val result = animeService.getTopAnime(currentPage, perPage)

        Log.d("AnimeRepository", result.data.toString())
        currentPage++
        return result
    }

    fun resetPage() {
        currentPage = 1
    }
}




