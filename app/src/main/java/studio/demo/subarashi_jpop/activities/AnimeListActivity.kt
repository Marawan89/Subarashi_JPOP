package studio.demo.subarashi_jpop.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Retrofit
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
    private var remoteAPi = RemoteApi
    var animeRepository: AnimeRepository = AnimeRepository(remoteAPi)

    private var animeList: MutableList<AnimeModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_list)
        Log.d("AnimeListActivity","sono AnimeListActivity")


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



        animeListViewModel = ViewModelProvider(this, AnimeListViewModelFactory(this.remoteAPi))[AnimeListViewModel::class.java]

        animeListViewModel.animeList.observe(this, Observer { anime ->
            animeList.addAll(anime)
            Log.d("AnimeListActivity", animeList[0].title)
        })

        animeListViewModel.getTopAnime()
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
