package studio.demo.subarashi_jpop.activities.anime

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.activities.MainActivity
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService

class AnimeFavouriteList : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var favouriteAnimeRecylerView: RecyclerView
    private lateinit var roomFavouriteLocalService: RoomFavouriteLocalService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_anime_list)
        bottomNavigationView = findViewById(R.id.animeBottomNavigationView)
        favouriteAnimeRecylerView = findViewById(R.id.favouriteRecyclerView)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "anime-database"
        ).build()

        animeDao = AppDatabase.getInstance(application).animeDao()
        roomFavouriteLocalService = RoomFavouriteLocalService(animeDao)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_animeList -> {
                    Intent(this, AnimeListActivity::class.java).also {
                        startActivity(it)
                    }
                    true
                }

                R.id.menu_animeFavouriteList -> {
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
        bottomNavigationView.selectedItemId = R.id.menu_animeFavouriteList

    }
}