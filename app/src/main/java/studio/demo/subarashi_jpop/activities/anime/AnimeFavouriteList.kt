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
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.activities.MainActivity
import studio.demo.subarashi_jpop.adapters.anime.AnimeFavouriteAdapter
import studio.demo.subarashi_jpop.adapters.anime.AnimeFavouriteAdapterListener
import studio.demo.subarashi_jpop.adapters.anime.AnimeListAdapter
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.RemoteApi.animeService
import studio.demo.subarashi_jpop.repositories.anime.AnimeRepository
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModel
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModelFactory

class AnimeFavouriteList : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var animeListViewModel: AnimeListViewModel
    private lateinit var adapter: AnimeFavouriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_anime_list)

        val animeDao = FavouriteDatabase.getDatabase(applicationContext).animeDao()
        val mangaDao = FavouriteDatabase.getDatabase(applicationContext).mangaDao()
        val localService = RoomFavouriteLocalService(animeDao, mangaDao)
        val animeRepository = AnimeRepository(animeService, localService)

        animeListViewModel = ViewModelProvider(this, AnimeListViewModelFactory(animeRepository))[AnimeListViewModel::class.java]

        bottomNavigationView = findViewById(R.id.animeBottomNavigationView)
        val favouriteAnimeRecyclerView = findViewById<RecyclerView>(R.id.animeFavouriteRecyclerView)

        adapter = AnimeFavouriteAdapter()
        adapter.setListener(object : AnimeFavouriteAdapterListener {
            override fun removeAnimeFromFavourite(anime: AnimeEntity) {
                animeListViewModel.removeAnimeFromDatabase(anime)
            }
        })
        adapter.submitList(animeListViewModel.getFavouriteAnimeList().value ?: emptyList())

        favouriteAnimeRecyclerView.adapter = adapter
        favouriteAnimeRecyclerView.layoutManager = LinearLayoutManager(this)

        animeListViewModel.getFavouriteAnimeList().observe(this, Observer { animeList ->
            adapter.submitList(animeList)
        })

        favouriteAnimeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = favouriteAnimeRecyclerView.layoutManager as LinearLayoutManager
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