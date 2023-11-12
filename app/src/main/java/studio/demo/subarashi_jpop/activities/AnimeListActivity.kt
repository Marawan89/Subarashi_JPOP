package studio.demo.subarashi_jpop.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.adapters.AnimeListAdapter
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.remote.model.AnimeModel
import studio.demo.subarashi_jpop.repositories.AnimeRepository
import studio.demo.subarashi_jpop.viewmodel.AnimeListViewModel
import studio.demo.subarashi_jpop.viewmodel.AnimeListViewModelFactory

class AnimeListActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeListAdapter
    private lateinit var animeListViewModel: AnimeListViewModel
    private var remoteApi = RemoteApi
    private var animeRepository: AnimeRepository = AnimeRepository(remoteApi)
    private var animeList: MutableList<AnimeModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_list)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = AnimeListAdapter(mutableListOf())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_animeList -> {
                    // Gestisci l'azione del menu_animeList
                    true
                }

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
        bottomNavigationView.selectedItemId = R.id.menu_favouriteList

        animeListViewModel =
            ViewModelProvider(this, AnimeListViewModelFactory(this.remoteApi))[AnimeListViewModel::class.java]

        animeListViewModel.animeList.observe(this, Observer { anime ->
            animeList.addAll(anime)
            adapter.setData(anime)
        })

        animeListViewModel.getTopAnime()
    }
}


