package studio.demo.subarashi_jpop.viewmodel.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.demo.subarashi_jpop.favouriteLocalService.FavouriteLocalService
import studio.demo.subarashi_jpop.repositories.manga.MangaRepository

class MangaListViewModelFactory(
    private val mangaRepository: MangaRepository,
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MangaListViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MangaListViewModel(mangaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}