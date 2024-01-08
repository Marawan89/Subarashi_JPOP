package studio.demo.subarashi_jpop.activities.anime

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.activities.MainActivity
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.adapters.anime.AnimeFavListAdapter
import studio.demo.subarashi_jpop.adapters.anime.AnimeFavListAdapterListener
import studio.demo.subarashi_jpop.repositories.anime.AnimeRepository
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModel
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModelFactory

class AnimeFavListActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var animeListViewModel: AnimeListViewModel
    private lateinit var adapter: AnimeFavListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anime_fav_list_activity)

        // setup ViewModel and Adapter
        setupViewModelAndAdapter()

        // setup BottomNavigationView
        setupBottomNavigationView()

        // setup RecyclerView
        setupRecyclerView()
    }

    private fun setupViewModelAndAdapter() {

        // viewModel arranges data to the activity: it knows the viewModelFactory which knows the repository which knows both the service and the localService and finally the localService knows the dao
        animeListViewModel = ViewModelProvider(this, AnimeListViewModelFactory(AnimeRepository(
            RemoteApi.animeService, RoomFavouriteLocalService(FavouriteDatabase.getDatabase(applicationContext).animeDao(), FavouriteDatabase.getDatabase(applicationContext).mangaDao()))))[AnimeListViewModel::class.java]

        adapter = AnimeFavListAdapter().apply {
            // setting a listener to handle removing an anime from favorites
            setListener(object : AnimeFavListAdapterListener {
                override fun removeAnimeFromFavourite(anime: AnimeEntity) {
                    // calling the ViewModel to remove the anime from the favorites database
                    animeListViewModel.removeAnimeFromDatabase(anime)
                }
            })
        }
    }

    private fun setupBottomNavigationView() {
        // manages translation between one activity and another
        bottomNavigationView = findViewById(R.id.animeBottomNavigationView)
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

    // setting up the RecyclerView for displaying the list of favorite anime
    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.animeFavouriteRecyclerView)
        recyclerView.adapter = adapter
        // displays data in a linear pattern
        recyclerView.layoutManager = LinearLayoutManager(this)

        // observing changes in the favorite anime list and updating the adapter
        animeListViewModel.getFavouriteAnimeList().observe(this, Observer { animeList ->
            adapter.submitList(animeList)
        })
    }
}
