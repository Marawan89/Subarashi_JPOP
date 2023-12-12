package studio.demo.subarashi_jpop.remote.manga

import retrofit2.http.GET
import retrofit2.http.Query
import studio.demo.subarashi_jpop.remote.anime.model.AnimeListResponse
import studio.demo.subarashi_jpop.remote.manga.model.MangaListResponse

interface MangaService {
    @GET("top/manga")
    suspend fun getTopManga(@Query("page") page:Int = 1, @Query("per_page") perPage: Int = 10): MangaListResponse

    @GET("manga")
    suspend fun searchManga(@Query("q") query: String, @Query("sfw") sfw: Boolean = true): MangaListResponse
}