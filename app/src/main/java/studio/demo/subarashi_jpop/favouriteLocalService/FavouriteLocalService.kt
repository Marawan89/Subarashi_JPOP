package studio.demo.subarashi_jpop.favouriteLocalService

import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity

interface FavouriteLocalService {
    suspend fun insertAnime(anime: AnimeEntity)
    suspend fun getFavouriteAnime(): List<AnimeEntity>
    suspend fun deleteAnime(anime: AnimeEntity)
}