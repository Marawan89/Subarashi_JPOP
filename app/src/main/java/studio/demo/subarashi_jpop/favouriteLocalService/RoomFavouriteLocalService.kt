package studio.demo.subarashi_jpop.favouriteLocalService

import android.content.Context
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.MangaDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

class RoomFavouriteLocalService (
    private val animeDao: AnimeDao,
    private val mangaDao: MangaDao
) : FavouriteLocalService {
    override suspend fun insertAnime(anime: AnimeEntity){
        animeDao.insertAnime(anime)
    }

    override suspend fun getFavouriteAnime(): List<AnimeEntity> {
        return animeDao.getFavouriteAnime()
    }

    override suspend fun deleteAnime(anime: AnimeEntity){
        animeDao.deleteAnime(anime)
    }

    suspend fun insertManga(manga: MangaEntity){
        mangaDao.insertManga(manga)
    }

    suspend fun getFavouriteManga(): List<MangaEntity>{
        return mangaDao.getFavouriteManga()
    }

    suspend fun deleteManga(manga: MangaEntity){
        mangaDao.deleteManga(manga)
    }

    companion object {
        @Volatile
        private var INSTANCE: RoomFavouriteLocalService? = null

        fun getInstance(context: Context): RoomFavouriteLocalService {
            return INSTANCE ?: synchronized(this) {
                val database = FavouriteDatabase.getDatabase(context)
                val instance = RoomFavouriteLocalService(
                    animeDao = database.animeDao(),
                    mangaDao = database.mangaDao()
                )
                INSTANCE = instance
                instance
            }
        }
    }
}