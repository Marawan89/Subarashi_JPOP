package studio.demo.subarashi_jpop.favouriteLocalService

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel

class RoomFavouriteLocalService(private val animeDao: AnimeDao) : FavouriteLocalService {

    private val _favouriteAnime = MutableLiveData<List<AnimeEntity>>()

    override suspend fun addToFavourites(anime: AnimeEntity) {
        animeDao.insert(anime)
        refreshFavouriteAnime()
    }

    override fun getFavouriteAnime(): LiveData<List<AnimeEntity>> {
        return _favouriteAnime
    }

    override suspend fun removeFromFavourites(anime: AnimeEntity) {
        animeDao.delete(anime)
        refreshFavouriteAnime()
    }

    // Aggiorna il LiveData dopo le operazioni di aggiunta/rimozione
    private fun refreshFavouriteAnime() {
        _favouriteAnime.postValue(animeDao.getAllAnime().value)
    }
}




