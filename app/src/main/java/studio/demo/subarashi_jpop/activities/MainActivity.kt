package studio.demo.subarashi_jpop.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.activities.anime.AnimeListActivity
import studio.demo.subarashi_jpop.activities.manga.MangaListActivity

class MainActivity : AppCompatActivity() {

    private lateinit var animeButton: Button
    private lateinit var mangaButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // initialize buttons and their click handlers
        initializeButtons()
    }

    private fun initializeButtons() {
        // initialize button for AnimeList activity
        animeButton = findViewById(R.id.animeButton)
        animeButton.setOnClickListener {
            // start AnimeList activity on button click
            startActivity(Intent(this, AnimeListActivity::class.java))
        }

        // initialize button for MangaList activity
        mangaButton = findViewById(R.id.mangaButton)
        mangaButton.setOnClickListener {
            // start MangaList activity on button click
            startActivity(Intent(this, MangaListActivity::class.java))
        }
    }
}