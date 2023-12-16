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
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.remote.RemoteApi.animeService
import studio.demo.subarashi_jpop.repositories.AnimeRepository
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModel
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModelFactory

class AnimeFavouriteList : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var favouriteAnimeRecylerView: RecyclerView
    private lateinit var roomFavouriteLocalService: RoomFavouriteLocalService
    private lateinit var adapter: AnimeFavouriteAdapter
    private lateinit var animeDao: AnimeDao
    private lateinit var viewModel: AnimeListViewModel
    private lateinit var animeRepository: AnimeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_anime_list)

        val favouriteDatabase = FavouriteDatabase.getDatabase(application)
        animeDao = favouriteDatabase.animeDao()
        animeRepository = AnimeRepository(animeService) 

        roomFavouriteLocalService = RoomFavouriteLocalService(animeDao)
        viewModel = ViewModelProvider(this, AnimeListViewModelFactory(animeRepository, roomFavouriteLocalService)).get(AnimeListViewModel::class.java)

        adapter = AnimeFavouriteAdapter(emptyList())

        Log.d("AnimeFavouriteList", "onCreate() executed")

        bottomNavigationView = findViewById(R.id.animeBottomNavigationView)

        favouriteAnimeRecylerView = findViewById(R.id.animeFavouriteRecyclerView)
        favouriteAnimeRecylerView.layoutManager = LinearLayoutManager(this)
        favouriteAnimeRecylerView.adapter = adapter

        viewModel.getFavouriteAnimeFromLocal().observe(this, Observer { anime ->
            adapter.setData(anime)
        })

        favouriteAnimeRecylerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = favouriteAnimeRecylerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

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




    /*private fun loadFavouriteAnime() {
        Log.d("AnimeFavouriteList", "Loading favourite anime...")
        CoroutineScope(Dispatchers.IO).launch {
            val favouriteAnimeList = roomFavouriteLocalService.getFavouriteAnime()
            Log.d("AnimeFavouriteList", "Favourite anime loaded: $favouriteAnimeList")
            withContext(Dispatchers.Main) {
                adapter.setData(favouriteAnimeList)
            }
        }
    }*/