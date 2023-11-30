package studio.demo.subarashi_jpop.remote.anime

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import studio.demo.subarashi_jpop.remote.anime.model.AnimeListResponse


interface AnimeService {
    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int, @Query("per_page") perPage: Int): AnimeListResponse
}

// versione che visualizza fino all'ultimo anime della page 1
//interface AnimeService {
//    @GET("top/anime") // URL aggiornato
//    suspend fun getTopAnime(): AnimeListResponse
//}