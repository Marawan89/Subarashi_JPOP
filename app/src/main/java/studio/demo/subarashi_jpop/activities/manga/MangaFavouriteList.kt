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
import studio.demo.subarashi_jpop.repositories.manga.MangaRepository
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModel
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModelFactory

class MangaFavouriteList : AppCompatActivity(){
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mangaListViewModel: MangaListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_manga_list)

        val animeDao = FavouriteDatabase.getDatabase(applicationContext).animeDao()
        val mangaDao = FavouriteDatabase.getDatabase(applicationContext).mangaDao()
        val localService = RoomFavouriteLocalService(animeDao, mangaDao)
        val mangaRepository = MangaRepository(mangaService, localService)

        mangaListViewModel = ViewModelProvider(this, MangaListViewModelFactory(mangaRepository, localService))[MangaListViewModel::class.java]

        Log.d("MangaFavouriteList", "onCreate() executed")

        bottomNavigationView = findViewById(R.id.mangaBottomNavigationView)
        val favouriteMangaRecyclerView = findViewById<RecyclerView>(R.id.mangaFavouriteRecyclerView)
        val adapter = MangaFavouriteAdapter()
        favouriteMangaRecyclerView.adapter = adapter
        favouriteMangaRecyclerView.layoutManager = LinearLayoutManager(this)

        mangaListViewModel.getFavouriteMangaList().observe(this, Observer {
            mangaList -> adapter.submitList(mangaList)
        })

        favouriteMangaRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = favouriteMangaRecyclerView.layoutManager as LinearLayoutManager
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