package studio.demo.subarashi_jpop

import androidx.lifecycle.LiveData
import studio.demo.subarashi_jpop.daos.AnimeDao
import studio.demo.subarashi_jpop.daos.MangaDao
import studio.demo.subarashi_jpop.entities.AnimeEntity
import studio.demo.subarashi_jpop.entities.MangaEntity

class FavouriteContainer(private val animeDao: AnimeDao, private val mangaDao: MangaDao) {
    fun getAnime(): LiveData<List<AnimeEntity>>{
        return animeDao.getAnime()
    }
    suspend fun insertAnime(anime: AnimeEntity){
        animeDao.insertAnime(anime)
    }
    fun animeDeletionByName(name: String){
        return animeDao.deletionByName(name)
    }
    fun getManga(): LiveData<List<MangaEntity>>{
        return mangaDao.getManga()
    }
    suspend fun insertManga(manga: MangaEntity){
        mangaDao.insertManga(manga)
    }
    fun mangaDeletionByName(name: String){
        return mangaDao.deletionByName(name)
    }

}