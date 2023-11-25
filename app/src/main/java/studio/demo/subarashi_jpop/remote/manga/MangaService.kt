package studio.demo.subarashi_jpop.remote.manga

import retrofit2.http.GET
import retrofit2.http.Query
import studio.demo.subarashi_jpop.remote.manga.model.MangaListResponse

interface MangaService {
    @GET("top/manga")
    suspend fun getTopManga(@Query("page") page:Int, @Query("per_page") perPage: Int): MangaListResponse
}