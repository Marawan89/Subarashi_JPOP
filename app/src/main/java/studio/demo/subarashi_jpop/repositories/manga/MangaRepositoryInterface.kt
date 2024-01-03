package studio.demo.subarashi_jpop.repositories.manga

import androidx.lifecycle.LiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity
import studio.demo.subarashi_jpop.remote.manga.model.MangaListResponse

interface MangaRepositoryInterface {
    suspend fun addMangaToDB(manga: MangaEntity)
    suspend fun getTopManga(page: Int, perPage: Int): MangaListResponse
    suspend fun searchManga(query: String): MangaListResponse
    fun getFavouriteMangaList(): LiveData<List<MangaEntity>>
}