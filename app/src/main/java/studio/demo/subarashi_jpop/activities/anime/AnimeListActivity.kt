package studio.demo.subarashi_jpop.activities.anime

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.activities.MainActivity
import studio.demo.subarashi_jpop.adapters.anime.AnimeListAdapter
import studio.demo.subarashi_jpop.adapters.anime.AnimeListAdapterListener
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.repositories.anime.AnimeRepository
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModel
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModelFactory

class AnimeListActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeListAdapter
    private lateinit var animeListViewModel: AnimeListViewModel
    private lateinit var searchInputLayout: TextInputLayout
    private lateinit var searchInputEditText: TextInputEditText
    private lateinit var buttonSearch: Button
    private lateinit var animeLifecycleOwner: LifecycleOwner // is for displaying the right icon based on whether the anime is within the db or not

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anime_list_activity)

        // setup ViewModel and Adapter
        setupViewModelAndAdapter()

        // setup BottomNavigationView
        setupBottomNavigationView()

        // setup RecyclerView
        setupRecyclerView()

        // setup Search functionality
        setupSearchFunctionality()
    }

    private fun setupViewModelAndAdapter() {
        // viewModel arranges data to the activity: it knows the viewModelFactory which knows the repository which knows both the service and the localService and finally the localService knows the dao
        animeListViewModel = ViewModelProvider(this, AnimeListViewModelFactory(AnimeRepository(RemoteApi.animeService, RoomFavouriteLocalService(FavouriteDatabase.getDatabase(applicationContext).animeDao(), FavouriteDatabase.getDatabase(applicationContext).mangaDao()))))[AnimeListViewModel::class.java]
        animeLifecycleOwner = this

        adapter = AnimeListAdapter(animeListViewModel.animeList.value?: emptyList(), object : AnimeListAdapterListener{
            // setting a listener to handle adding an anime to favorites
            override fun addAnimeToFavourite(anime: AnimeEntity) {
                // calling the ViewModel to add the anime to the favorites database
                animeListViewModel.addAnimeToDatabase(anime)
            }
        }, AnimeRepository(RemoteApi.animeService, RoomFavouriteLocalService(FavouriteDatabase.getDatabase(applicationContext).animeDao(), FavouriteDatabase.getDatabase(applicationContext).mangaDao())), animeLifecycleOwner)
    }

    private fun setupBottomNavigationView() {
        // manages translation between one activity and another
        bottomNavigationView = findViewById(R.id.animeBottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_animeList -> true
                R.id.menu_animeFavouriteList -> {
                    Intent(this, AnimeFavListActivity::class.java).also {
                        startActivity(it)
                    }
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
        bottomNavigationView.selectedItemId = R.id.menu_animeList
    }

    // manages the visualization of data taken from the api
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        // displays data within a grid consisting of three columns
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter

        // increments the page keyword in the api to load more anime
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int){
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleAnime = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleAnime + 2) {
                    animeListViewModel.loadMoreAnime()
                }
            }
        })

        animeListViewModel.animeList.observe(this, Observer { anime ->
            adapter.setData(anime)
        })
    }

    private fun setupSearchFunctionality() {
        // initializing UI components for search
        searchInputLayout = findViewById(R.id.anime_searchInputLayout)
        searchInputEditText = findViewById(R.id.anime_searchInputEditText)
        buttonSearch = findViewById(R.id.anime_buttonSearch)

        // setting a click listener for the search button
        buttonSearch.setOnClickListener {
            // extracting the search term from the input field
            val searchTerm = searchInputEditText.text.toString()
            // calling the ViewModel to perform anime search
            animeListViewModel.searchAnime(searchTerm)
        }
    }
}