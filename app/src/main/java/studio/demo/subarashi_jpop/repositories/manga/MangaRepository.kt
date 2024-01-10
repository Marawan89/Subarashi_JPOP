package studio.demo.subarashi_jpop.repositories.manga

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import retrofit2.HttpException
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity
import studio.demo.subarashi_jpop.remote.manga.MangaService
import studio.demo.subarashi_jpop.remote.manga.model.MangaListResponse

class MangaRepository (
    private val mangaService: MangaService,
    private val localService: RoomFavouriteLocalService
) : MangaRepositoryInterface {

    // check if the manga is in favorites or not
    override fun isMangaFavourite(id: Int): LiveData<Boolean> {
        return localService.isMangaFavourite(id)
    }

    // get the manga favourite list from the local database
    override fun getFavouriteMangaList(): LiveData<List<MangaEntity>> {
        return localService.getFavouriteMangaList()
    }
    // add manga to the local database in a background thread
    @WorkerThread
    override suspend fun addMangaToDB(manga: MangaEntity){
        localService.addMangaToFavourites(manga)
    }

    // remove manga from the local database in a background thread
    @WorkerThread
    override suspend fun removeMangaFromDB(manga: MangaEntity){
        localService.removeMangaFromFavourites(manga)
    }

    // get the top manga from the remote API
    override suspend fun getTopManga(page: Int, perPage: Int): MangaListResponse {
        try {
            return mangaService.getTopManga(page, perPage)
        } catch (e: HttpException) {
            Log.e("MangaRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            Log.e("MangaRepository", "Error: ${e.message}")
            throw e
        }
    }

    // search for anime based on the provided query
    override suspend fun searchManga(query: String): MangaListResponse {
        try {
            return mangaService.searchManga(query)
        } catch (e: HttpException) {
            Log.e("MangaRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            Log.e("MangaRepository", "Error: ${e.message}")
            throw e
        }
    }
}