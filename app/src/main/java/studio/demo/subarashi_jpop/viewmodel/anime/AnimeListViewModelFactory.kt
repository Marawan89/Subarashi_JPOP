package studio.demo.subarashi_jpop.viewmodel.anime


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.demo.subarashi_jpop.repositories.AnimeRepository

//versione senza pagination
//class AnimeListViewModelFactory(private val remoteApi: RemoteApi) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AnimeListViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return AnimeListViewModel(remoteApi) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

class AnimeListViewModelFactory(private val animeRepository: AnimeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeListViewModel(animeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


