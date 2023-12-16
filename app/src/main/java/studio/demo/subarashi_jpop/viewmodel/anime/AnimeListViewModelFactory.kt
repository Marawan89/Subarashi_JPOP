package studio.demo.subarashi_jpop.viewmodel.anime


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.demo.subarashi_jpop.favouriteLocalService.FavouriteLocalService
import studio.demo.subarashi_jpop.repositories.AnimeRepository
class AnimeListViewModelFactory(private val animeRepository: AnimeRepository, private val localService: FavouriteLocalService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeListViewModel(animeRepository, localService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
