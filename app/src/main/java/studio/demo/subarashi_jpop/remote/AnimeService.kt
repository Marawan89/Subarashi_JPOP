package studio.demo.subarashi_jpop.remote

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import studio.demo.subarashi_jpop.remote.model.AnimeModel

interface AnimeService {
    @GET("top/anime") // URL aggiornato
    suspend fun getTopAnime(): Response<List<AnimeModel>>
}

class AnimeRepository(private val retrofit: Retrofit) {
    private val animeService = retrofit.create(AnimeService::class.java)

    suspend fun getTopAnime(): List<AnimeModel>? {
        return try {
            val response = animeService.getTopAnime()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
