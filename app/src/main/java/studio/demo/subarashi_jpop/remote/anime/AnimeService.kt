package studio.demo.subarashi_jpop.remote.anime

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import studio.demo.subarashi_jpop.remote.anime.model.AnimeListResponse


interface AnimeService {
    // retrieves the top anime list with page and per_page parameters to load more anime
    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int = 1, @Query("per_page") perPage: Int = 10): AnimeListResponse

    // searches for anime based on the provided query with optional sfw parameter
    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String, @Query("sfw") sfw: Boolean = true): AnimeListResponse
}
