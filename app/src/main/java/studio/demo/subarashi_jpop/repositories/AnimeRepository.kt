package studio.demo.subarashi_jpop.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import studio.demo.subarashi_jpop.remote.AnimeService
import studio.demo.subarashi_jpop.remote.model.AnimeModel

class AnimeRepository(private val retrofit: Retrofit) {
    private val animeService = retrofit.create(AnimeService::class.java)

    suspend fun getTopAnime(): LiveData<List<AnimeModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = animeService.getTopAnime()
                if (response.isSuccessful) {
                    val animeList = response.body()
                    MutableLiveData<List<AnimeModel>>().apply {
                        value = animeList
                    }
                } else {
                    MutableLiveData<List<AnimeModel>>().apply {
                        value = null
                    }
                }
            } catch (e: Exception) {
                MutableLiveData<List<AnimeModel>>().apply {
                    value = null
                }
            }
        }
    }
}
