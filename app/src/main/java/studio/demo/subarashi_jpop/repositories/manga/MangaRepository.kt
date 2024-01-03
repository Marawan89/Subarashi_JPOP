package studio.demo.subarashi_jpop.repositories.manga

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import retrofit2.HttpException
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity
import studio.demo.subarashi_jpop.remote.manga.MangaService
import studio.demo.subarashi_jpop.remote.manga.model.MangaListResponse

class MangaRepository (
    private val mangaService: MangaService,
    private val localService: RoomFavouriteLocalService
) : MangaRepositoryInterface {

    override fun getFavouriteMangaList(): LiveData<List<MangaEntity>> {
        return localService.getFavouriteMangaList()
    }
    @WorkerThread
    override suspend fun addMangaToDB(manga: MangaEntity){
        localService.addMangaToFavourites(manga)
    }

    override suspend fun getTopManga(page: Int, perPage: Int): MangaListResponse {
        try {
            val result = mangaService.getTopManga(page, perPage)
            Log.d("MangaRepository", result.data.toString())
            return result
        } catch (e: HttpException) {
            Log.e("MangaRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            Log.e("MangaRepository", "Error: ${e.message}")
            throw e
        }
    }

    override suspend fun searchManga(query: String): MangaListResponse {
        try {
            val result = mangaService.searchManga(query)
            Log.d("MangaRepository", result.data.toString())
            return result
        } catch (e: HttpException) {
            Log.e("MangaRepository", "HTTP Exception: ${e.code()}")
            throw e
        } catch (e: Exception) {
            Log.e("MangaRepository", "Error: ${e.message}")
            throw e
        }
    }
}