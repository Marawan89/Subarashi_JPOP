package studio.demo.subarashi_jpop

import android.app.Application
import studio.demo.subarashi_jpop.favouriteLocalService.FavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.remote.anime.AnimeService
import studio.demo.subarashi_jpop.remote.manga.MangaService
import studio.demo.subarashi_jpop.repositories.AnimeRepository
import studio.demo.subarashi_jpop.repositories.MangaRepository

class MainApplication : Application() {
    val database by lazy { FavouriteDatabase.getDatabase(this) }
    val localService by lazy { RoomFavouriteLocalService(database.animeDao(), database.mangaDao()) }
    val animeRepository: AnimeRepository by lazy { AnimeRepository(RemoteApi.animeService, localService) }
    val mangaRepository: MangaRepository by lazy { MangaRepository(RemoteApi.mangaService, localService) }

}
