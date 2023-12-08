package studio.demo.subarashi_jpop.favouriteLocalService

import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity

class RoomFavouriteLocalService (private val animeDao: AnimeDao) : FavouriteLocalService {
    override suspend fun insertAnime(anime: AnimeEntity){
        animeDao.insertAnime(anime)
    }

    override suspend fun getFavouriteAnime(): List<AnimeEntity> {
        return animeDao.getFavouriteAnime()
    }

    override suspend fun deleteAnime(anime: AnimeEntity){
        animeDao.deleteAnime(anime)
    }
}