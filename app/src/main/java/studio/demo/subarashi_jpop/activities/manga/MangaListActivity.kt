package studio.demo.subarashi_jpop.activities.manga

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
import studio.demo.subarashi_jpop.adapters.manga.MangaListAdapter
import studio.demo.subarashi_jpop.adapters.manga.MangaListAdapterListener
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.repositories.manga.MangaRepository
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModel
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModelFactory

class MangaListActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MangaListAdapter
    private lateinit var mangaListViewModel: MangaListViewModel
    private lateinit var searchInputLayout: TextInputLayout
    private lateinit var searchInputEditText: TextInputEditText
    private lateinit var buttonSearch: Button
    private lateinit var mangaLifecycleOwner: LifecycleOwner // is for displaying the right icon based on whether the manga is within the db or not

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manga_list_activity)

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
        mangaListViewModel = ViewModelProvider(this, MangaListViewModelFactory(MangaRepository(RemoteApi.mangaService, RoomFavouriteLocalService(FavouriteDatabase.getDatabase(applicationContext).animeDao(), FavouriteDatabase.getDatabase(applicationContext).mangaDao()))))[MangaListViewModel::class.java]
        mangaLifecycleOwner = this

        adapter = MangaListAdapter(mangaListViewModel.mangaList.value?: emptyList(), object : MangaListAdapterListener {
            // setting a listener to handle adding a manga to favorites
            override fun addMangaToFavourite(manga: MangaEntity) {
                // calling the ViewModel to add the manga to the favorites database
                mangaListViewModel.addMangaToDatabase(manga)
            }
        }, MangaRepository(RemoteApi.mangaService, RoomFavouriteLocalService(FavouriteDatabase.getDatabase(applicationContext).animeDao(), FavouriteDatabase.getDatabase(applicationContext).mangaDao())), mangaLifecycleOwner)
    }

    private fun setupBottomNavigationView() {
        // manages translation between one activity and another
        bottomNavigationView = findViewById(R.id.mangaBottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_mangaList -> true
                R.id.menu_mangaFavouriteList -> {
                    Intent(this, MangaFavListActivity::class.java).also {
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
        bottomNavigationView.selectedItemId = R.id.menu_mangaList
    }

    // manages the visualization of data taken from the api
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        // displays data within a grid consisting of three columns
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter

        // increments the page keyword in the api to load more manga
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleManga = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleManga + 2) {
                    mangaListViewModel.loadMoreManga()
                }
            }
        })

        mangaListViewModel.mangaList.observe(this, Observer { manga ->
            adapter.setData(manga)
        })
    }

    private fun setupSearchFunctionality() {
        // initializing UI components for search
        searchInputLayout = findViewById(R.id.manga_searchInputLayout)
        searchInputEditText = findViewById(R.id.manga_searchInputEditText)
        buttonSearch = findViewById(R.id.manga_buttonSearch)

        // setting a click listener for the search button
        buttonSearch.setOnClickListener {
            // extracting the search term from the input field
            val searchTerm = searchInputEditText.text.toString()
            // calling the ViewModel to perform manga search
            mangaListViewModel.searchManga(searchTerm)
        }
    }
}