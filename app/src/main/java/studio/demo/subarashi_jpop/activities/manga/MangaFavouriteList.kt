package studio.demo.subarashi_jpop.activities.manga

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
import studio.demo.subarashi_jpop.adapters.manga.MangaFavouriteAdapter
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.MangaDao
import studio.demo.subarashi_jpop.remote.RemoteApi.mangaService
import studio.demo.subarashi_jpop.repositories.MangaRepository
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModel
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModelFactory

class MangaFavouriteList : AppCompatActivity(){
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var favouriteMangaRecyclerView: RecyclerView
    private lateinit var roomFavouriteLocalService: RoomFavouriteLocalService
    private lateinit var adapter: MangaFavouriteAdapter
    private lateinit var animeDao: AnimeDao
    private lateinit var mangaDao: MangaDao
    private lateinit var viewModel: MangaListViewModel
    private lateinit var mangaRepository: MangaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_manga_list)

        val favouriteDatabase = FavouriteDatabase.getDatabase(application)

        animeDao = favouriteDatabase.animeDao()
        mangaDao = favouriteDatabase.mangaDao()
        mangaRepository = MangaRepository(mangaService)
        roomFavouriteLocalService = RoomFavouriteLocalService(animeDao, mangaDao)
        viewModel = ViewModelProvider(this, MangaListViewModelFactory(mangaRepository, roomFavouriteLocalService)).get(MangaListViewModel::class.java)
        adapter = MangaFavouriteAdapter(emptyList())

        Log.d("MangaFavouriteList", "onCreate() executed")

        bottomNavigationView = findViewById(R.id.mangaBottomNavigationView)
        favouriteMangaRecyclerView = findViewById(R.id.mangaFavouriteRecyclerView)
        favouriteMangaRecyclerView.layoutManager = LinearLayoutManager(this)
        favouriteMangaRecyclerView.adapter = adapter

        viewModel.getFavouriteMangaFromLocal().observe(this, Observer {
            manga -> adapter.setData(manga)
        })

        favouriteMangaRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = favouriteMangaRecyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            }
        })

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
}