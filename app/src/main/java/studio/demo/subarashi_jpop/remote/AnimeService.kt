package studio.demo.subarashi_jpop.remote

import retrofit2.http.GET
import studio.demo.subarashi_jpop.remote.model.AnimeListResponse

interface AnimeService {
    @GET("top/anime") // URL aggiornato
    suspend fun getTopAnime(): AnimeListResponse
}
