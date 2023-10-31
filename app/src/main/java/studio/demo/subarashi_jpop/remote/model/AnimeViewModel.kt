package studio.demo.subarashi_jpop.remote.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class AnimeViewModel(
    val title: String? = null,
    val imageUrl: String? = null,
    val episodes: Int? = null,
    val status: String? = null,
    val airedFrom: String? = null,
    val airedTo: String? = null,
    val animeLiveData: LiveData<AnimeViewModel> = MutableLiveData()
) : ViewModel()
