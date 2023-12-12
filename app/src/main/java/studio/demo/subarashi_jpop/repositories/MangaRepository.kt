package studio.demo.subarashi_jpop.repositories

import android.util.Log
import retrofit2.HttpException
import studio.demo.subarashi_jpop.remote.RemoteApi.animeService
import studio.demo.subarashi_jpop.remote.manga.MangaService
import studio.demo.subarashi_jpop.remote.manga.model.MangaListResponse

class MangaRepository (private val mangaService: MangaService) {
    private var currentPage = 1
    private var perPage = 10

    suspend fun getTopManga(page: Int = currentPage, perPage: Int = this.perPage): MangaListResponse {
        try {
            val result = mangaService.getTopManga(page, perPage)
            Log.d("MangaRepository", result.data.toString())
            return result
        } catch (e: HttpException) {
            Log.e("MangaRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            Log.e("MangaRepository", "Error: ${e.message}")
            throw e
        }
    }

    suspend fun searchManga(query: String): MangaListResponse {
        try {
            val result = mangaService.searchManga(query)
            Log.d("MangaRepository", result.data.toString())
            return result
        } catch (e: HttpException) {
            Log.e("MangaRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            Log.e("MangaRepository", "Error: ${e.message}")
            throw e
        }
    }

    fun resetPage() {
        currentPage = 1
    }
}