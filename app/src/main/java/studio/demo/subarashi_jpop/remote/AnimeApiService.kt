package studio.demo.subarashi_jpop.remote

import android.app.Application
import retrofit2.Retrofit
//import okhttp3.logging.HttpLoggingInterceptor

/* interface AnimeApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): TopAnimeResponse
}*/

class AnimeApiService : Application(){
    companion object{
        lateinit var retrofit: Retrofit
        lateinit var footApiService: RemoteApi
        lateinit var retrofit2: Retrofit
        lateinit var animeApiService: AnimeApiService
        // lateinit var mangaApiService: MangaApiService
    }

    override fun onCreate() {
        super.onCreate()

        /*retrofit = Retrofit.Builder()
            .baseUrl("https://docs.api.jikan.moe/#tag/anime")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient) // Usa l'OkHttpClient con l'interceptor
            .build()

        footApiService = retrofit.create(RemoteApi::class.java)

        retrofit2 = Retrofit.Builder().baseUrl("https://api.jikan.moe/v4/top/anime").client(httpClient).build()*/

    }
}