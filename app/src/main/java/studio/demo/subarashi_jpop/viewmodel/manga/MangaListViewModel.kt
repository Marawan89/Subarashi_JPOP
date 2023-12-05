package studio.demo.subarashi_jpop.viewmodel.manga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.remote.manga.model.MangaModel
import studio.demo.subarashi_jpop.repositories.MangaRepository

class MangaListViewModel (private val mangaRepository: MangaRepository) : ViewModel() {
    private var _mangaListLiveData = MutableLiveData<List<MangaModel>>()
    private var initialPage = 1
    private var currentPage = initialPage
    private val perPage = 10
    private var isLoading = false

    var mangaList: LiveData<List<MangaModel>> = _mangaListLiveData

    init {
        getTopManga()
    }

    fun getTopManga(page: Int = 1, perPage: Int = 10) {
        viewModelScope.launch {
            try {
                if (!isLoading){
                    isLoading = true
                    val mangaList = mangaRepository.getTopManga(page, perPage)
                    val uiManga = mangaList.data.map {
                        MangaModel(
                            mal_id = it.mal_id,
                            url = it.url,
                            images = it.images.jpg.image_url,
                            approved = it.approved,
                            titles = it.titles,
                            title = it.title,
                            title_english = it.title_english,
                            title_japanese = it.title_japanese,
                            type = it.type,
                            chapters = it.chapters,
                            volumes = it.volumes,
                            status = it.status,
                            publishing = it.publishing,
                            published = it.published,
                            score = it.score,
                            scored_by = it.scored_by,
                            rank = it.rank,
                            popularity = it.popularity,
                            members = it.members,
                            favorites = it.favorites,
                            synopsis = it.synopsis,
                            background = it.background,
                            authors = it.authors,
                            serializations = it.serializations,
                            genres = it.genres,
                            explicit_genres = it.explicit_genres,
                            themes = it.themes,
                            demographics = it.demographics
                        )
                    }
                    _mangaListLiveData.postValue(uiManga)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun loadMoreManga() {
        viewModelScope.launch {
            try {
                if (!isLoading){
                    isLoading = true
                    currentPage++
                    val mangaList = mangaRepository.getTopManga(currentPage, perPage)
                    val uiManga = mangaList.data.map {
                        MangaModel(
                            mal_id = it.mal_id,
                            url = it.url,
                            images = it.images.jpg.image_url,
                            approved = it.approved,
                            titles = it.titles,
                            title = it.title,
                            title_english = it.title_english,
                            title_japanese = it.title_japanese,
                            type = it.type,
                            chapters = it.chapters,
                            volumes = it.volumes,
                            status = it.status,
                            publishing = it.publishing,
                            published = it.published,
                            score = it.score,
                            scored_by = it.scored_by,
                            rank = it.rank,
                            popularity = it.popularity,
                            members = it.members,
                            favorites = it.favorites,
                            synopsis = it.synopsis,
                            background = it.background,
                            authors = it.authors,
                            serializations = it.serializations,
                            genres = it.genres,
                            explicit_genres = it.explicit_genres,
                            themes = it.themes,
                            demographics = it.demographics
                        )
                    }
                    _mangaListLiveData.postValue(_mangaListLiveData.value.orEmpty() + uiManga)
                }
            } catch (e: Exception){
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun resetPage() {
        currentPage = initialPage
        mangaRepository.resetPage()
        _mangaListLiveData.value = emptyList()
        getTopManga()
    }
}