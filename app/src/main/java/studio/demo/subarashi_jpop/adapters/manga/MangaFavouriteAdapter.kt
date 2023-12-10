package studio.demo.subarashi_jpop.adapters.manga

import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

class MangaFavouriteAdapter(
    private var favouriteMangaList: List<MangaEntity>
    private val roomFavouriteLocalService: RoomFavouriteLocalService
) {
}