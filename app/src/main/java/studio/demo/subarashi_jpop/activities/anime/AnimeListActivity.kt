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
import studio.demo.subarashi_jpop.adapters.anime.AnimeListAdapter
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.repositories.AnimeRepository
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModel
import studio.demo.subarashi_jpop.viewmodel.anime.AnimeListViewModelFactory

class AnimeListActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeListAdapter
    private lateinit var animeListViewModel: AnimeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_list)

        val animeRepository = AnimeRepository(RemoteApi.service)
        val viewModelFactory = AnimeListViewModelFactory(animeRepository)

        animeListViewModel = ViewModelProvider(this, viewModelFactory).get(AnimeListViewModel::class.java)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = AnimeListAdapter(mutableListOf())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (totalItemCount <= lastVisibleItem + 2) {
                    animeListViewModel.getTopAnime()
                }
            }
        })

        animeListViewModel.animeList.observe(this, Observer { anime ->
            adapter.setData(anime)
        })

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_animeList -> true
                R.id.menu_favouriteList -> {
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

        bottomNavigationView.selectedItemId = R.id.menu_animeList
    }
}
