package studio.demo.subarashi_jpop.activities.manga

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.activities.MainActivity
import studio.demo.subarashi_jpop.adapters.manga.MangaListAdapter
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.repositories.MangaRepository
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModel
import studio.demo.subarashi_jpop.viewmodel.manga.MangaListViewModelFactory

class MangaListActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MangaListAdapter
    private lateinit var mangaListViewModel: MangaListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_list)

        val mangaRepository = MangaRepository(RemoteApi.mangaService)
        val viewModelFactory = MangaListViewModelFactory(mangaRepository)

        mangaListViewModel = ViewModelProvider(this,viewModelFactory).get(MangaListViewModel::class.java)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = MangaListAdapter(mutableListOf())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibileItem = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibileItem + 2){
                    mangaListViewModel.getTopManga()
                }
            }
        })

        mangaListViewModel.mangaList.observe(this,
            {manga ->
                adapter.setData(manga)
            })

        bottomNavigationView.setOnItemSelectedListener {
            menuItem ->
                when(menuItem.itemId){
                    R.id.menu_mangaList -> true
                    R.id.menu_favouriteList -> {
                        Intent(this, MangaFavouriteList::class.java).also {
                            startActivity(it)
                        }
                        true
                    }
                    else -> {
                        Intent(this,MainActivity::class.java).also {
                            startActivity(it)
                        }
                        true
                    }
                }
            }
        bottomNavigationView.selectedItemId = R.id.menu_mangaList
    }
}
