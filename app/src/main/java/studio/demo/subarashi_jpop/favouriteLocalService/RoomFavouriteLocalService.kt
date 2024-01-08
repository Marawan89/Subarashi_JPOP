package studio.demo.subarashi_jpop.favouriteLocalService

import androidx.lifecycle.LiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.MangaDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

// implementation of the local service that handles favorites using Room
class RoomFavouriteLocalService(
    private val animeDao: AnimeDao,
    private val mangaDao: MangaDao
) : FavouriteLocalService {

    // adds an anime to the favorites list
    override suspend fun addAnimeToFavourites(anime: AnimeEntity) {
        animeDao.insert(anime)
    }

    // removes an anime from the favorites list
    override suspend fun removeAnimeFromFavourites(anime: AnimeEntity) {
        animeDao.delete(anime)
    }

    // gets the favorite anime list
    override fun getFavouriteAnimeList(): LiveData<List<AnimeEntity>> {
        return animeDao.getAllAnime()
    }

    // checks if the given anime is a favorite
    override fun isAnimeFavourite(id: Int): LiveData<Boolean> {
        return animeDao.isFavourite(id)
    }

    // adds a manga to the favorites list
    override suspend fun addMangaToFavourites(manga: MangaEntity) {
        mangaDao.insert(manga)
    }

    // removes a manga from the favorites list
    override suspend fun removeMangaFromFavourites(manga: MangaEntity) {
        mangaDao.delete(manga)
    }

    // gets the favorite manga list
    override fun getFavouriteMangaList(): LiveData<List<MangaEntity>> {
        return mangaDao.getAllManga()
    }

    // checks if the given manga is a favorite
    override fun isMangaFavourite(id: Int): LiveData<Boolean> {
        return mangaDao.isFavourite(id)
    }
}