package studio.demo.subarashi_jpop.viewmodel.anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel
import studio.demo.subarashi_jpop.repositories.anime.AnimeRepositoryInterface

class AnimeListViewModel(
    private val animeRepository: AnimeRepositoryInterface,
) : ViewModel() {
    private var _animeListLiveData = MutableLiveData<List<AnimeModel>>()
    private var _animeFavouriteLiveData = MutableLiveData<List<AnimeEntity>>()

    // variables to manage pagination
    private var initialPage = 1
    private var currentPage = initialPage
    private val perPage = 10
    // variable to manage data loading
    private var isLoading = false

    var animeList: LiveData<List<AnimeModel>> = _animeListLiveData

    // initiates the function that fetches top anime from the repository to view anime
    init {
        getTopAnime()
    }

    // function to fetch top anime from the repository
    private fun getTopAnime(page: Int = 1, perPage: Int = 10) {
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

    // function to add anime to the local database
    fun addAnimeToDatabase(anime: AnimeEntity) {
        viewModelScope.launch {
            animeRepository.addAnimeToDB(anime)
        }
    }

    // function to remove anime from the local database
    fun removeAnimeFromDatabase(anime: AnimeEntity) {
        viewModelScope.launch {
            animeRepository.removeAnimeFromDB(anime)
            // Update LiveData after removal
            _animeFavouriteLiveData.value = animeRepository.getFavouriteAnimeList().value
        }
    }

    // function to get the list of favorite anime from the local database
    fun getFavouriteAnimeList(): LiveData<List<AnimeEntity>> {
        return animeRepository.getFavouriteAnimeList()
    }

    // function to load more anime
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

    // function to search an anime
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