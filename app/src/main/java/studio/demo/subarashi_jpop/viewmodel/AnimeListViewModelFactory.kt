package studio.demo.subarashi_jpop.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.repositories.AnimeRepository
import studio.demo.subarashi_jpop.viewmodel.AnimeListViewModel

class AnimeListViewModelFactory(private val remoteApi: RemoteApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeListViewModel(remoteApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}