package studio.demo.subarashi_jpop.activities.manga

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.activities.MainActivity
import studio.demo.subarashi_jpop.adapters.manga.MangaListAdapter
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.AnimeDao
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.dao.MangaDao
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.repositories.MangaRepository
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModel
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModelFactory

class MangaListActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MangaListAdapter
    private lateinit var mangaListViewModel: MangaListViewModel
    private lateinit var roomFavouriteLocalService: RoomFavouriteLocalService
    private lateinit var searchInputLayout: TextInputLayout
    private lateinit var searchInputEditText: TextInputEditText
    private lateinit var buttonSearch: Button
    private lateinit var animeDao: AnimeDao
    private lateinit var mangaDao: MangaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_list)

        val mangaRepository = MangaRepository(RemoteApi.mangaService)
        val animeDao = FavouriteDatabase.getDatabase(applicationContext).animeDao()
        val mangaDao = FavouriteDatabase.getDatabase(applicationContext).mangaDao()
        val localService = RoomFavouriteLocalService(animeDao, mangaDao)
        val viewModelFactory = MangaListViewModelFactory(mangaRepository, localService)

        mangaListViewModel = ViewModelProvider(this, viewModelFactory).get(MangaListViewModel::class.java)


        bottomNavigationView = findViewById(R.id.mangaBottomNavigationView)
        recyclerView = findViewById(R.id.recyclerView)
        searchInputLayout = findViewById(R.id.manga_searchInputLayout)
        searchInputEditText = findViewById(R.id.manga_searchInputEditText)
        buttonSearch = findViewById(R.id.manga_buttonSearch)

        roomFavouriteLocalService = RoomFavouriteLocalService(animeDao, mangaDao)
        adapter = MangaListAdapter(mangaListViewModel.mangaList.value ?: emptyList(), roomFavouriteLocalService)

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibileItem = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibileItem + 2) {
                    mangaListViewModel.loadMoreManga()
                }
            }
        })

        mangaListViewModel.mangaList.observe(this, Observer {
            manga -> adapter.setData(manga)
        })

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_mangaList -> true
                R.id.menu_mangaFavouriteList -> {
                    Intent(this, MangaFavouriteList::class.java).also {
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

        buttonSearch.setOnClickListener {
            val searchTerm = searchInputEditText.text.toString()
            mangaListViewModel.searchManga(searchTerm)
        }
        bottomNavigationView.selectedItemId = R.id.menu_mangaList
    }
}
