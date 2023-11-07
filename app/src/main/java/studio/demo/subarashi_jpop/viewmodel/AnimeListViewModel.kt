package studio.demo.subarashi_jpop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.remote.model.AnimeModel
import studio.demo.subarashi_jpop.repositories.AnimeRepository

class AnimeListViewModel(private val animeRepository: AnimeRepository) : ViewModel() {
    private val animeListLiveData: MutableLiveData<List<AnimeModel>> = MutableLiveData()

    fun getTopAnime(): LiveData<List<AnimeModel>> {
        if (animeListLiveData.value == null) {
            viewModelScope.launch {
                val animeList = animeRepository.getTopAnime()
                animeListLiveData.postValue(animeList)
            }
        }
        return animeListLiveData
    }
}






