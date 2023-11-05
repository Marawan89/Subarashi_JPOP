package studio.demo.subarashi_jpop.viewmodel

import android.content.Context
//
//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import studio.demo.subarashi_jpop.repositories.FavouriteListRepository
//import studio.demo.subarashi_jpop.daos.AnimeDao
//import studio.demo.subarashi_jpop.daos.MangaDao
//import studio.demo.subarashi_jpop.entities.AnimeEntity
//import studio.demo.subarashi_jpop.entities.MangaEntity
//
class ViewModelFavouriteList (
    context: Context
)//: ViewModel(){
//
//    private val favouriteContainer: FavouriteListRepository
//    private val animeDao: AnimeDao
//    private val mangaDao: MangaDao
//
//    init {
//        favouriteContainer = FavouriteListRepository(animeDao)
//        animeDao = AppDatabase.getInstance(context).favouriteAnimeDao()
//        mangaDao = AppDatabase.getInstance(context).favouriteMangaDao()
//    }
//
//    val favouriteAnime: LiveData<List<AnimeEntity>> = favouriteContainer.getAnime()
//    val favouriteManga: LiveData<List<MangaEntity>> = favouriteContainer.getManga()
//
//    fun onClickInsertFavouriteAnime(favouriteAnime: AnimeEntity){
//        viewModelScope.launch {
//            favouriteContainer.insertAnime(favouriteAnime)
//        }
//    }
//
//    fun onClickDeleteAnime(name: String){
//        viewModelScope.launch(Dispatchers.IO){
//            favouriteContainer.animeDeletionByName(name)
//        }
//    }
//
//    fun onClickInsertFavouriteManga(favouriteManga: MangaEntity){
//        viewModelScope.launch {
//            favouriteContainer.insertManga(favouriteManga)
//        }
//    }
//
//    fun onClickDeleteManga(name: String){
//        viewModelScope.launch(Dispatchers.IO){
//            favouriteContainer.mangaDeletionByName(name)
//        }
//    }
//}