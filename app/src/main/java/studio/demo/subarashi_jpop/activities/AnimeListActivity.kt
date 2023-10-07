package studio.demo.subarashi_jpop.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.R.layout.activity_anime_list
import studio.demo.subarashi_jpop.ViewModelCustomFactory
import studio.demo.subarashi_jpop.adapter.AnimeListAdapter
import studio.demo.subarashi_jpop.entities.AnimeEntity
import studio.demo.subarashi_jpop.listeners.OnClickListener
import studio.demo.subarashi_jpop.model.AnimeModel
import studio.demo.subarashi_jpop.viewmodel.FavouriteListViewModel
import studio.demo.subarashi_jpop.viewmodel.AnimeListViewModel

class AnimeListActivity : AppCompatActivity(), OnClickListener {
    private lateinit var viewModel: AnimeListViewModel
    private lateinit var favouriteListViewModel: FavouriteListViewModel
    // private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeListAdapter
    private lateinit var bottomNavigationView: BottomNavigationView
    private var TAG = "AnimeListActivity"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(activity_anime_list)

        bottomNavigationView = findViewById(R.id.bottomNavigationView) //basta fare l'activity anime list

        viewModel = ViewModelProvider(
            this,
            ViewModelCustomFactory(this, this::class.java)
        )[AnimeListActivity::class.java]

        favouriteListViewModel = ViewModelProvider(
            this, ViewModelCustomFactory(this, this::class.java)
        )[FavouriteListViewModel::class.java]

        adapter = AnimeListAdapter(this, this)


        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.menu_animeList -> {
                    true
                }
                R.id.menu_favouriteList -> {
                    Intent(this, AnimeFavouritesList::class.java).also {
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
    }

    override fun ViewInfoOnClick(model: Any){
        val animeModel = model as AnimeModel
        val bundle = Bundle()
        bundle.putString("Anime Name", animeModel.name)
        bundle.putString("Plot", animeModel.plot)

        /*val dialog: DialogFragment = DetailsDialogFragment()
        dialog.show(supportFragmentManager, "DetailsDialogFragment")*/

    }

    override fun AddToFavouriteOnClick(model: Any){
        val animeModel = model as AnimeModel
        val favouriteAnime = AnimeEntity(
            id = animeModel.id.toString(),
            name = animeModel.name,
            spriteurl = animeModel.urlNorlmal
        )
        favouriteListViewModel.insertFavouriteAnime(favouriteAnime)
        Toast.makeText(this, "${favouriteAnime.name} anime added to favourites list", Toast.LENGTH_SHORT).show()
    }
}
