package studio.demo.subarashi_jpop.viewmodel.anime


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.demo.subarashi_jpop.repositories.anime.AnimeRepositoryInterface

class AnimeListViewModelFactory(
    private val animeRepository: AnimeRepositoryInterface,
) : ViewModelProvider.Factory {

    // factory implementation to create view models

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // check if requested view model is of type AnimeListViewModel
        if (modelClass.isAssignableFrom(AnimeListViewModel::class.java)) {
            // cast and return instance of AnimeListViewModel with provided repository
            @Suppress("UNCHECKED_CAST")
            return AnimeListViewModel(animeRepository) as T
        }
        // throw exception for unknown view model class
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

