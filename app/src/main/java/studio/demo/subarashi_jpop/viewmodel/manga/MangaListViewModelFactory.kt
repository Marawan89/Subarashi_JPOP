package studio.demo.subarashi_jpop.viewmodel.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.demo.subarashi_jpop.repositories.manga.MangaRepository

class MangaListViewModelFactory(
    private val mangaRepository: MangaRepository,
    ): ViewModelProvider.Factory {

    // factory implementation to create view models

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // check if requested view model is of type MangaListViewModel
        if (modelClass.isAssignableFrom(MangaListViewModel::class.java)){
            // cast and return instance of MangaListViewModel with provided repository
            @Suppress("UNCHECKED_CAST")
            return MangaListViewModel(mangaRepository) as T
        }
        // throw exception for unknown view model class
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}