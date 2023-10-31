package studio.demo.subarashi_jpop.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.remote.RemoteApi
import studio.demo.subarashi_jpop.remote.model.AnimeViewModel
import studio.demo.subarashi_jpop.viewmodel.AnimeViewModelFactory

class AnimeListActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var titleTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var episodesTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var airedFromTextView: TextView
    private lateinit var airedToTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_list)

        titleTextView = findViewById(R.id.titleTextView)
        imageView = findViewById(R.id.animeImage)
        episodesTextView = findViewById(R.id.episodesTextView)
        statusTextView = findViewById(R.id.statusTextView)
        airedFromTextView = findViewById(R.id.airedFromTextView)
        airedToTextView = findViewById(R.id.airedToTextView)

        val viewModel = ViewModelProvider(this, AnimeViewModelFactory(RemoteApi.service))
            .get(AnimeViewModel::class.java)

        viewModel.animeLiveData.observe(this, Observer { anime ->
            titleTextView.text = anime.title
            Picasso.get().load(anime.imageUrl).into(imageView)
            episodesTextView.text = "Episodes: ${anime.episodes ?: 0}"
            statusTextView.text = "Status: ${anime.status ?: "Unknown"}"
            airedFromTextView.text = "Aired From: ${anime.airedFrom ?: "Unknown"}"
            airedToTextView.text = "Aired To: ${anime.airedTo ?: "Unknown"}"
        })

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_animeList -> {
                    // Gestisci l'azione del menu_animeList
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
}








//    private var TAG = "AnimeListActivity"
//    private lateinit var viewModel: AnimeViewModel
//    private lateinit var favouriteListViewModel: ViewModelFavouriteList
//    private lateinit var recyclerView: RecyclerView

/*viewModel = ViewModelProvider(
            this,
            ViewModelCustomFactory(this, this::class.java)
        )[AnimeListActivity::class.java]*/

    /*favouriteListViewModel = ViewModelProvider(
        this, ViewModelCustomFactory(this, this::class.java)
    )[ViewModelFavouriteList::class.java]*/

    // adapter = AnimeListAdapter(this, this)


    /*override fun ViewInfoOnClick(model: Any){
        val animeModel = model as AnimeModel
        val bundle = Bundle()
        bundle.putString("Anime Name", animeModel.name)
        bundle.putString("Plot", animeModel.plot)

        val dialog: DialogFragment = DetailsDialogFragment()
        dialog.show(supportFragmentManager, "DetailsDialogFragment")

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
    }*/
//}
