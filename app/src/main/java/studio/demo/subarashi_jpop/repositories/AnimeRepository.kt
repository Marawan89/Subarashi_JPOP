package studio.demo.subarashi_jpop.repositories

import android.util.Log
import retrofit2.Retrofit
import studio.demo.subarashi_jpop.remote.AnimeService
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.remote.model.AnimeListResponse
import studio.demo.subarashi_jpop.remote.model.AnimeModel

class AnimeRepository(remoteApi: RemoteApi) {
    private val animeService = remoteApi.service

    suspend fun getTopAnime(): AnimeListResponse {

        val result = animeService.getTopAnime()

        Log.d("AnimeRepository", result.data.toString())
        return result

    }
}


