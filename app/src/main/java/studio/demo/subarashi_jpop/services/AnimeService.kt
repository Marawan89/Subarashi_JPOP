package studio.demo.subarashi_jpop.services

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import studio.demo.subarashi_jpop.searchedanime.SearchedAnime
import studio.demo.subarashi_jpop.topanime.TopAnime

interface AnimeService {

    @GET("top/anime")
    fun getTopAnime(): Call<TopAnime>

    @GET("search/anime")
    fun getSerachedAnime(@Query("q")queryString: String): Call<SearchedAnime>    //the q is query string

    companion object{
        val BASE_URL = "https://api.jikan.moe/v4/"
        fun create(): AnimeService{
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build()
            return retrofit.create(AnimeService::class.java)
        }
    }
}

