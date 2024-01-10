package studio.demo.subarashi_jpop.remote.manga

import retrofit2.http.GET
import retrofit2.http.Query
import studio.demo.subarashi_jpop.remote.anime.model.AnimeListResponse
import studio.demo.subarashi_jpop.remote.manga.model.MangaListResponse

interface MangaService {
    // retrieves the top manga list with page and per_page parameters to load more manga
    @GET("top/manga")
    suspend fun getTopManga(@Query("page") page:Int = 1, @Query("per_page") perPage: Int = 10): MangaListResponse

    // searches for manga based on the provided query with optional sfw parameter
    @GET("manga")
    suspend fun searchManga(@Query("q") query: String, @Query("sfw") sfw: Boolean = true): MangaListResponse
}