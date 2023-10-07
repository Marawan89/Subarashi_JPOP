package studio.demo.subarashi_jpop.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.demo.subarashi_jpop.activities.*

class ViewModelCustomFactory (private val context: Context, private val activityType: Class<out AppCompatActivity>):
        ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return when (activityType){
           AnimeListActivity::class.java, MangaListActivity::class.java-> {
               if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
                   MainActivityViewModel(context) as T
               } else if(modelClass.isAssignableFrom(ViewModelFavouriteList::class.java)){
                   ViewModelFavouriteList(context) as T
               } else{
                   throw IllegalArgumentException("unknown ViewModel class")
               }
           }

           AnimeFavouritesList::class.java, MangaFavouriteList::class.java->{
               if (modelClass.isAssignableFrom(ViewModelFavouriteList::class.java)){ ViewModelFavouriteList(context) as T
               } else {
                   throw IllegalArgumentException("unknown ViewModel class")
               }
           }

           AnimePlotActivity::class.java -> {
               if (modelClass.isAssignableFrom(ViewModelPlotAnime::class.java)){
                   ViewModelPlotAnime() as T
               } else {
                   throw IllegalArgumentException("unknown ViewModel class")
               }
           }
           else -> throw IllegalArgumentException("unknown ViewModel class")

       }
    }
    }