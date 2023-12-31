package studio.demo.subarashi_jpop.favouriteLocalService

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.MangaDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel

class RoomFavouriteLocalService(
    private val animeDao: AnimeDao,
    private val mangaDao: MangaDao
) : FavouriteLocalService {

    private val _favouriteAnime = MutableLiveData<List<AnimeEntity>>()
    private val _favouriteManga = MutableLiveData<List<MangaEntity>>()

    override suspend fun addAnimeToFavourites(anime: AnimeEntity) {
        animeDao.insert(anime)
        refreshFavouriteAnime()
    }

    override fun getFavouriteAnime(): LiveData<List<AnimeEntity>> {
        return _favouriteAnime
    }

    override suspend fun removeAnimeFromFavourites(anime: AnimeEntity) {
        animeDao.delete(anime)
        refreshFavouriteAnime()
    }

    // aggiorna il LiveData dopo le operazioni di aggiunta/rimozione
    private fun refreshFavouriteAnime() {
        _favouriteAnime.postValue(animeDao.getAllAnime().value)
    }

    override fun getFavouriteAnimeList(): LiveData<List<AnimeEntity>> {
        return animeDao.getAllAnime()
    }

    override suspend fun addMangaToFavourites(manga: MangaEntity) {
        mangaDao.insert(manga)
        refreshFavouriteManga()
    }

    override fun getFavouriteManga(): LiveData<List<MangaEntity>> {
        return _favouriteManga
    }

    override suspend fun removeMangaFromFavourites(manga: MangaEntity) {
        mangaDao.delete(manga)
        refreshFavouriteManga()
    }

    // controllo se l'anime è nei preferiti o meno
    override fun isAnimeFavourite(id: Int): LiveData<Boolean> {
        return animeDao.isFavourite(id)
    }

    // aggiorna il LiveData dopo le operazioni di aggiunta/rimozione
    private fun refreshFavouriteManga() {
        _favouriteManga.postValue(mangaDao.getAllManga().value)
    }

    override fun getFavouriteMangaList(): LiveData<List<MangaEntity>> {
        return mangaDao.getAllManga()
    }

    // controllo se il manga è nei preferiti o meno
    override fun isMangaFavourite(id: Int): LiveData<Boolean> {
        return mangaDao.isFavourite(id)
    }
}