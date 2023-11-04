package studio.demo.subarashi_jpop.repositories

import retrofit2.Retrofit
import studio.demo.subarashi_jpop.remote.AnimeService
import studio.demo.subarashi_jpop.remote.model.AnimeModel

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





