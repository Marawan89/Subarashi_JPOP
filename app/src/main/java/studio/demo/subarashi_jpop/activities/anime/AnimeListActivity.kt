package studio.demo.subarashi_jpop.activities.anime

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_list)

        // activity deve conoscere solo il viewmodel, il repository deve conoscere il localservice e l'altro, poi il localservice segue anime e mangadao

        val animeDao = FavouriteDatabase.getDatabase(applicationContext).animeDao()
        val mangaDao = FavouriteDatabase.getDatabase(applicationContext).mangaDao()
        val localService = RoomFavouriteLocalService(animeDao, mangaDao)
        val animeRepository = AnimeRepository(RemoteApi.animeService, localService)


        // viewmodel dispone i dati all'activty
        animeListViewModel = ViewModelProvider(this, AnimeListViewModelFactory(animeRepository))[AnimeListViewModel::class.java]

        bottomNavigationView = findViewById(R.id.animeBottomNavigationView)
        recyclerView = findViewById(R.id.recyclerView)
        searchInputLayout = findViewById(R.id.anime_searchInputLayout)
        searchInputEditText = findViewById(R.id.anime_searchInputEditText)
        buttonSearch = findViewById(R.id.anime_buttonSearch)

        adapter = AnimeListAdapter(animeListViewModel.animeList.value ?: emptyList(), object : AnimeListAdapterListener{
            override fun addAnimeToFavourite(anime: AnimeEntity) {
                animeListViewModel.addAnimeToDatabase(anime)
            }
        })

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int){
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleItem + 2) {
                    animeListViewModel.loadMoreAnime()
                }
            }
        })

        animeListViewModel.animeList.observe(this, Observer { anime ->
            adapter.setData(anime)
        })

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_animeList -> true
                R.id.menu_animeFavouriteList -> {
                    Intent(this, AnimeFavouriteList::class.java).also {
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
            animeListViewModel.searchAnime(searchTerm)
        }
        bottomNavigationView.selectedItemId = R.id.menu_animeList
    }
}