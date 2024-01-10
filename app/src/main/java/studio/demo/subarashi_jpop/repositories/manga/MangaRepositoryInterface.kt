package studio.demo.subarashi_jpop.repositories.manga

import androidx.lifecycle.LiveData
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity
import studio.demo.subarashi_jpop.remote.manga.model.MangaListResponse

interface MangaRepositoryInterface {
    // add manga to the local database in a background thread
    suspend fun addMangaToDB(manga: MangaEntity)

    // remove manga from the local database in a background thread
    suspend fun removeMangaFromDB(manga: MangaEntity)

    // get the top manga from the remote API
    suspend fun getTopManga(page: Int, perPage: Int): MangaListResponse

    // search for manga based on the provided query
    suspend fun searchManga(query: String): MangaListResponse

    // get the manga favourite list from the local database
    fun getFavouriteMangaList(): LiveData<List<MangaEntity>>

    // check if the manga is in favorites or not
    fun isMangaFavourite(id: Int): LiveData<Boolean>
}