package studio.demo.subarashi_jpop.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.google.android.material.bottomnavigation.BottomNavigationView
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.remote.model.AnimeModel
import studio.demo.subarashi_jpop.repositories.AnimeRepository
import studio.demo.subarashi_jpop.viewmodel.AnimeListViewModel
import studio.demo.subarashi_jpop.viewmodel.AnimeListViewModelFactory

class AnimeListActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var titleTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var episodesTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var airedFromTextView: TextView
    private lateinit var airedToTextView: TextView
    private lateinit var animeListViewModel: AnimeListViewModel
    private var remoteApi = RemoteApi
    private var animeRepository: AnimeRepository = AnimeRepository(remoteApi)

    private var animeList: MutableList<AnimeModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_list)
        Log.d("AnimeListActivity", "sono AnimeListActivity")

        titleTextView = findViewById(R.id.titleTextView)
        imageView = findViewById(R.id.animeImage)
        episodesTextView = findViewById(R.id.episodesTextView)
        statusTextView = findViewById(R.id.statusTextView)
        airedFromTextView = findViewById(R.id.airedFromTextView)
        airedToTextView = findViewById(R.id.airedToTextView)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
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
            Log.d("AnimeListActivity", animeList[0].title)

            Picasso.get().load(animeList[0].images.jpg.image_url)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView)

            // Imposta il titolo dell'anime
            titleTextView.text = animeList[0].title
            episodesTextView.text = animeList[0].episodes.toString()
            statusTextView.text = animeList[0].status
            airedFromTextView.text = animeList[0].aired.from
            airedToTextView.text = animeList[0].aired.to
        })

        animeListViewModel.getTopAnime()
    }
}

