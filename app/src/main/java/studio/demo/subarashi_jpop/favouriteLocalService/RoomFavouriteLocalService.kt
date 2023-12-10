package studio.demo.subarashi_jpop.favouriteLocalService

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

    override suspend fun insertManga(manga: MangaEntity){
        mangaDao.insertManga(manga)
    }

    override suspend fun getFavouriteManga(): List<MangaEntity>{
        return mangaDao.getFavouriteManga()
    }

    override suspend fun deleteManga(manga: MangaEntity){
        mangaDao.deleteManga(manga)
    }
}