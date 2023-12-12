package studio.demo.subarashi_jpop.viewmodel.anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel
import studio.demo.subarashi_jpop.repositories.AnimeRepository

class AnimeListViewModel(private val animeRepository: AnimeRepository) : ViewModel() {
    private var _animeListLiveData = MutableLiveData<List<AnimeModel>>()
    private var initialPage = 1
    private var currentPage = initialPage
    private val perPage = 10
    private var isLoading = false

    var animeList: LiveData<List<AnimeModel>> = _animeListLiveData

    init {
        getTopAnime()
    }

    fun getTopAnime(page: Int = 1, perPage: Int = 10) {
        viewModelScope.launch {
            try {
                if (!isLoading) {
                    isLoading = true
                    val animeList = animeRepository.getTopAnime(page, perPage)
                    val uiAnime = animeList.data.map {
                        AnimeModel(
                            mal_id = it.mal_id,
                            url = it.url,
                            images = it.images.jpg.image_url,
                            trailer = it.trailer,
                            approved = it.approved,
                            titles = it.titles,
                            title = it.title,
                            title_japanese = it.title_japanese,
                            title_synonyms = it.title_synonyms,
                            title_english = it.title_english,
                            type = it.type,
                            source = it.source,
                            episodes = it.episodes,
                            status = it.status,
                            airing = it.airing,
                            aired = it.aired,
                            duration = it.duration,
                            rating = it.rating,
                            score = it.score,
                            scored_by = it.scored_by,
                            rank = it.rank,
                            popularity = it.popularity,
                            members = it.members,
                            favorites = it.favorites,
                            synopsis = it.synopsis,
                            background = it.background,
                            broadcast = it.broadcast,
                            producers = it.producers,
                            licensors = it.licensors,
                            studios = it.studios,
                            genres = it.genres,
                            explicit_genres = it.explicit_genres,
                            themes = it.themes,
                            demographics = it.demographics
                        )

                    }
                    _animeListLiveData.postValue(uiAnime)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun loadMoreAnime() {
        viewModelScope.launch {
            try {
                if (!isLoading) {
                    isLoading = true
                    currentPage++
                    val animeList = animeRepository.getTopAnime(currentPage, perPage)
                    val uiAnime = animeList.data.map {
                        AnimeModel(
                            mal_id = it.mal_id,
                            url = it.url,
                            images = it.images.jpg.image_url,
                            trailer = it.trailer,
                            approved = it.approved,
                            titles = it.titles,
                            title = it.title,
                            title_japanese = it.title_japanese,
                            title_synonyms = it.title_synonyms,
                            title_english = it.title_english,
                            type = it.type,
                            source = it.source,
                            episodes = it.episodes,
                            status = it.status,
                            airing = it.airing,
                            aired = it.aired,
                            duration = it.duration,
                            rating = it.rating,
                            score = it.score,
                            scored_by = it.scored_by,
                            rank = it.rank,
                            popularity = it.popularity,
                            members = it.members,
                            favorites = it.favorites,
                            synopsis = it.synopsis,
                            background = it.background,
                            broadcast = it.broadcast,
                            producers = it.producers,
                            licensors = it.licensors,
                            studios = it.studios,
                            genres = it.genres,
                            explicit_genres = it.explicit_genres,
                            themes = it.themes,
                            demographics = it.demographics
                        )
                    }
                    _animeListLiveData.postValue(_animeListLiveData.value.orEmpty() + uiAnime)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun resetPage() {
        currentPage = initialPage
        animeRepository.resetPage()
        _animeListLiveData.value = emptyList()
        getTopAnime()
    }

    fun searchAnime(query: String) {
        viewModelScope.launch {
            try {
                val animeList = animeRepository.searchAnime(query)
                val uiAnime = animeList.data.map {
                    AnimeModel(
                        mal_id = it.mal_id,
                        url = it.url,
                        images = it.images.jpg.image_url,
                        trailer = it.trailer,
                        approved = it.approved,
                        titles = it.titles,
                        title = it.title,
                        title_japanese = it.title_japanese,
                        title_synonyms = it.title_synonyms,
                        title_english = it.title_english,
                        type = it.type,
                        source = it.source,
                        episodes = it.episodes,
                        status = it.status,
                        airing = it.airing,
                        aired = it.aired,
                        duration = it.duration,
                        rating = it.rating,
                        score = it.score,
                        scored_by = it.scored_by,
                        rank = it.rank,
                        popularity = it.popularity,
                        members = it.members,
                        favorites = it.favorites,
                        synopsis = it.synopsis,
                        background = it.background,
                        broadcast = it.broadcast,
                        producers = it.producers,
                        licensors = it.licensors,
                        studios = it.studios,
                        genres = it.genres,
                        explicit_genres = it.explicit_genres,
                        themes = it.themes,
                        demographics = it.demographics
                    )
                }
                _animeListLiveData.postValue(uiAnime)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}