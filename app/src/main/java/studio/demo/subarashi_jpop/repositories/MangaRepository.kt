package studio.demo.subarashi_jpop.repositories

import android.util.Log
import studio.demo.subarashi_jpop.remote.manga.MangaService
import studio.demo.subarashi_jpop.remote.manga.model.MangaListResponse

class MangaRepository (private val mangaService: MangaService) {
    private var currentPage = 1
    private var perPage = 10

    suspend fun getTopManga(): MangaListResponse{
        val result = mangaService.getTopManga(currentPage, perPage)

        Log.d("MangaRepository", result.data.toString())
        currentPage++
        return result
    }
}