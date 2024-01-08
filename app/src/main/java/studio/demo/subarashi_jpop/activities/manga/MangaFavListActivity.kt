package studio.demo.subarashi_jpop.activities.manga

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
import studio.demo.subarashi_jpop.adapters.manga.MangaFavListAdapter
import studio.demo.subarashi_jpop.adapters.manga.MangaFavListAdapterListener
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.repositories.manga.MangaRepository
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModel
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModelFactory

class MangaFavListActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mangaListViewModel: MangaListViewModel
    private lateinit var adapter: MangaFavListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manga_fav_list_activity)

        // setup ViewModel and Adapter
        setupViewModelAndAdapter()

        // setup BottomNavigationView
        setupBottomNavigationView()

        // setup RecyclerView
        setupRecyclerView()
    }

    private fun setupViewModelAndAdapter() {

        // viewModel arranges data to the activity: it knows the viewModelFactory which knows the repository which knows both the service and the localService and finally the localService knows the dao
        mangaListViewModel = ViewModelProvider(this, MangaListViewModelFactory(MangaRepository(RemoteApi.mangaService, RoomFavouriteLocalService(FavouriteDatabase.getDatabase(applicationContext).animeDao(), FavouriteDatabase.getDatabase(applicationContext).mangaDao()))))[MangaListViewModel::class.java]

        adapter = MangaFavListAdapter().apply {
            // setting a listener to handle removing a manga from favorites
            setListener(object : MangaFavListAdapterListener {
                override fun removeMangaFromFavourite(manga: MangaEntity) {
                    // calling the ViewModel to remove the manga from the favorites database
                    mangaListViewModel.removeMangaFromDatabase(manga)
                }
            })
        }
    }

    private fun setupBottomNavigationView() {
        // manages translation between one activity and another
        bottomNavigationView = findViewById(R.id.mangaBottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_mangaList -> {
                    Intent(this, MangaListActivity::class.java).also {
                        startActivity(it)
                    }
                    true
                }
                R.id.menu_mangaFavouriteList -> {
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
        bottomNavigationView.selectedItemId = R.id.menu_mangaFavouriteList
    }

    // setting up the RecyclerView for displaying the list of favorite manga
    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.mangaFavouriteRecyclerView)
        recyclerView.adapter = adapter
        // displays data in a linear pattern
        recyclerView.layoutManager = LinearLayoutManager(this)

        // observing changes in the favorite manga list and updating the adapter
        mangaListViewModel.getFavouriteMangaList().observe(this, Observer { mangaList ->
            adapter.submitList(mangaList)
        })
    }
}
