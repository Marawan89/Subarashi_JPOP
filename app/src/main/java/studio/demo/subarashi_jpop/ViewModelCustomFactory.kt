package studio.demo.subarashi_jpop

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.DAOs.AnimeDao
import studio.demo.subarashi_jpop.DAOs.MangaDao
import studio.demo.subarashi_jpop.entities.AnimeEntity
import studio.demo.subarashi_jpop.entities.MangaEntity

class ViewModelCustomFactory (
    context: Context
): ViewModel(){

    private val favouriteContainer: FavouriteContainer
    private val animeDao: AnimeDao
    private val mangaDao: MangaDao

    init {
        favouriteContainer = FavouriteContainer(animeDao)
        animeDao = AppDatabase.getInstance(context).favouriteAnimeDao()
        mangaDao = AppDatabase.getInstance(context).favouriteMangaDao()
    }

    val favouriteAnime: LiveData<List<AnimeEntity>> = favouriteContainer.getAnime()
    val favouriteManga: LiveData<List<MangaEntity>> = favouriteContainer.getManga()

    fun onClickInsertFavouriteAnime(favouriteAnime: AnimeEntity){
        viewModelScope.launch {
            favouriteContainer.insertAnime(favouriteAnime)
        }
    }

    fun onClickDeleteAnime(name: String){
        viewModelScope.launch(Dispatchers.IO){
            favouriteContainer.animeDeletionByName(name)
        }
    }

    fun onClickInsertFavouriteManga(favouriteManga: MangaEntity){
        viewModelScope.launch {
            favouriteContainer.insertManga(favouriteManga)
        }
    }

    fun onClickDeleteManga(name: String){
        viewModelScope.launch(Dispatchers.IO){
            favouriteContainer.mangaDeletionByName(name)
        }
    }
}