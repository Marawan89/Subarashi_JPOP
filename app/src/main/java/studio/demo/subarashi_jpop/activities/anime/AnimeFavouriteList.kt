package studio.demo.subarashi_jpop.activities.anime

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import studio.demo.subarashi_jpop.MainApplication
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.activities.MainActivity
import studio.demo.subarashi_jpop.adapters.anime.AnimeFavouriteAdapter
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.MangaDao
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.remote.RemoteApi.animeService
import studio.demo.subarashi_jpop.repositories.AnimeRepository
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModel
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModelFactory

class AnimeFavouriteList : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var favouriteAnimeRecylerView: RecyclerView
    private lateinit var adapter: AnimeFavouriteAdapter
    private lateinit var animeListViewModel: AnimeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_anime_list)

        val favouriteDatabase = FavouriteDatabase.getDatabase(application)
        val animeDao = FavouriteDatabase.getDatabase(applicationContext).animeDao()
        val mangaDao = FavouriteDatabase.getDatabase(applicationContext).mangaDao()
        val localService = RoomFavouriteLocalService(animeDao, mangaDao)
        val animeRepository = AnimeRepository(RemoteApi.animeService, localService)

        animeListViewModel = ViewModelProvider(this, AnimeListViewModelFactory(animeRepository, localService))[AnimeListViewModel::class.java]

        Log.d("AnimeFavouriteList", "onCreate() executed")

        bottomNavigationView = findViewById(R.id.animeBottomNavigationView)
        val favouriteAnimeRecylerView = findViewById<RecyclerView>(R.id.animeFavouriteRecyclerView)
        val adapter = AnimeFavouriteAdapter()
        favouriteAnimeRecylerView.adapter = adapter
        favouriteAnimeRecylerView.layoutManager = LinearLayoutManager(this)

        animeListViewModel.getFavouriteAnimeList().observe(this, Observer { animeList ->
            // Aggiorna la UI con la nuova lista di anime preferiti
            // Utilizza l'adapter per visualizzare gli anime nella RecyclerView, ad esempio.
            adapter.submitList(animeList)
        })

        favouriteAnimeRecylerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = favouriteAnimeRecylerView.layoutManager as LinearLayoutManager
            }
        })

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_animeList -> {
                    Intent(this, AnimeListActivity::class.java).also {
                        startActivity(it)
                    }
                    true
                }

                R.id.menu_animeFavouriteList -> {
                    true
                }

                else -> {
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                    }
                    true
                }
            }
        }
        bottomNavigationView.selectedItemId = R.id.menu_animeFavouriteList
    }
}