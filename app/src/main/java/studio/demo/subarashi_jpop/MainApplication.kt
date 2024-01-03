package studio.demo.subarashi_jpop

import android.app.Application
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.repositories.anime.AnimeRepository
import studio.demo.subarashi_jpop.repositories.manga.MangaRepository

class MainApplication : Application() {
    val database by lazy { FavouriteDatabase.getDatabase(this) }
    val localService by lazy { RoomFavouriteLocalService(database.animeDao(), database.mangaDao()) }
    val animeRepository: AnimeRepository by lazy { AnimeRepository(RemoteApi.animeService, localService) }
    val mangaRepository: MangaRepository by lazy { MangaRepository(RemoteApi.mangaService, localService) }

}
