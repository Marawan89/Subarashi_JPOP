package studio.demo.subarashi_jpop.activities.manga

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.activities.MainActivity
import studio.demo.subarashi_jpop.activities.anime.AnimeListActivity
import studio.demo.subarashi_jpop.adapters.manga.MangaFavouriteAdapter
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.MangaDao

class MangaFavouriteList : AppCompatActivity(){
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var favouriteMangaRecyclerView: RecyclerView
    private lateinit var roomFavouriteLocalService: RoomFavouriteLocalService
    private lateinit var adapter: MangaFavouriteAdapter
    private lateinit var animeDao: AnimeDao
    private lateinit var mangaDao: MangaDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_manga_list)

        Log.d("MangaFavouriteList", "onCreate() executed")

        bottomNavigationView = findViewById(R.id.mangaBottomNavigationView)
        favouriteMangaRecyclerView = findViewById(R.id.mangaFavouriteRecyclerView)

        val favouriteDatabase = FavouriteDatabase.getDatabase(application)
        animeDao = favouriteDatabase.animeDao()
        mangaDao = favouriteDatabase.mangaDao()
        roomFavouriteLocalService = RoomFavouriteLocalService(animeDao, mangaDao)

        adapter = MangaFavouriteAdapter(emptyList(), roomFavouriteLocalService)
        favouriteMangaRecyclerView.layoutManager = LinearLayoutManager(this)
        favouriteMangaRecyclerView.adapter = adapter
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

    private fun loadFavouriteManga() {
        Log.d("MangaFavouriteList", "Loading favourite manga...")
        CoroutineScope(Dispatchers.IO).launch {
            val favouriteMangaList = roomFavouriteLocalService.getFavouriteManga()
            Log.d("MangaFavouriteList", "Favourite manga loaded: $favouriteMangaList")
            withContext(Dispatchers.Main) {
                adapter.setData(favouriteMangaList)
            }
        }
    }
}