package studio.demo.subarashi_jpop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.remote.model.AnimeModel
import studio.demo.subarashi_jpop.repositories.AnimeRepository

class AnimeListViewModel(private val remoteApi: RemoteApi) : ViewModel() {

    private val animeRepository: AnimeRepository = AnimeRepository(remoteApi)

    private var _animeListLiveData = MutableLiveData<List<AnimeModel>>()


    var animeList: LiveData<List<AnimeModel>> = _animeListLiveData

    val cards: MutableLiveData<List<AnimeModel>> by lazy {
        MutableLiveData<List<AnimeModel>>()
    }

    fun getTopAnime() {

        viewModelScope.launch {
            val animeList = animeRepository.getTopAnime()

            val uiAnime = animeList.data.map {

                it.season?.let { it1 ->
                    it.year?.let { it2 ->
                        AnimeModel(

                            mal_id = it.mal_id,
                            url = it.url,
                            images = it.images,
                            trailer = it.trailer,
                            approved = it.approved,
                            titles = it.titles,
                            title = it.title,
                            title_english = it.title_english,
                            title_japanese = it.title_japanese,
                            title_synonyms = it.title_synonyms,
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
                            season = it1,
                            year = it2,
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
                }

            }

            _animeListLiveData.postValue(uiAnime as List<AnimeModel>?)
        }


    }
}