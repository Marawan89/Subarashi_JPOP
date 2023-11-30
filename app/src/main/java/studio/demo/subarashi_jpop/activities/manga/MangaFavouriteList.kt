package studio.demo.subarashi_jpop.activities.manga

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.activities.MainActivity
import studio.demo.subarashi_jpop.activities.anime.AnimeListActivity

class MangaFavouriteList : AppCompatActivity(){
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_manga_list)
        bottomNavigationView = findViewById(R.id.mangaBottomNavigationView)

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