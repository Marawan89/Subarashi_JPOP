package studio.demo.subarashi_jpop.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import studio.demo.subarashi_jpop.R

class MainActivity : AppCompatActivity() {

    private lateinit var animeButton: Button
    private lateinit var mangaButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animeButton = findViewById(R.id.animeButton)
        animeButton.setOnClickListener{
            Intent(this, AnimeListActivity::class.java).also{
                startActivity(it)
            }
        }

        mangaButton = findViewById(R.id.mangaButton)
        mangaButton.setOnClickListener{
            Intent(this, MangaListActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}