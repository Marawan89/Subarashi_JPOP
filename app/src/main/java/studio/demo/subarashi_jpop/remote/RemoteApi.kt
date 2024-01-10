package studio.demo.subarashi_jpop.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import studio.demo.subarashi_jpop.remote.anime.AnimeService
import studio.demo.subarashi_jpop.remote.manga.MangaService

object RemoteApi {

    // base URL to the Jikan API
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    // moshi instance to JSON serialization/deserialization
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // retrofit instance to make API requests
    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    // animeService instance for anime-related API calls
    val animeService: AnimeService = retrofit.create(AnimeService::class.java)

    // mangaService instance for manga-related API calls
    val mangaService: MangaService = retrofit.create(MangaService::class.java)
}
