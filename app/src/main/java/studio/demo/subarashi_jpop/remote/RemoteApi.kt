package studio.demo.subarashi_jpop.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import studio.demo.subarashi_jpop.remote.anime.AnimeService

object RemoteApi {
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val service: AnimeService = retrofit.create(AnimeService::class.java)
}
