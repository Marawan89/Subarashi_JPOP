package studio.demo.subarashi_jpop.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Response
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.TopAnime
import studio.demo.subarashi_jpop.adapter.AnimeListAdapter
import studio.demo.subarashi_jpop.databinding.ActivityAnimeListBinding
import studio.demo.subarashi_jpop.listeners.OnClickListener
import studio.demo.subarashi_jpop.services.AnimeService
import studio.demo.subarashi_jpop.viewmodel.AnimeListViewModel
import studio.demo.subarashi_jpop.viewmodel.ViewModelFavouriteList
import javax.security.auth.callback.Callback

class AnimeListActivity : AppCompatActivity(), /*OnClickListener*/ {
    private lateinit var viewModel: AnimeListViewModel
    private lateinit var favouriteListViewModel: ViewModelFavouriteList
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeListAdapter
    private lateinit var bottomNavigationView: BottomNavigationView
    private var TAG = "AnimeListActivity"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = ActivityAnimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val animeService = AnimeService.create()
            val call = animeService.getTopAnime()

            call.enqueue(object : retrofit2.Callback<TopAnime>{

                override fun onResponse(call: Call<TopAnime>, response: Response<TopAnime>) {
                    if(response.body() != null){
                        val data = response.body()!!.data            //da mettere a posto sta parte se cambio il file JSON
                        animeRecyclerView.adapter =
                    }
                }

                override fun onFailure(call: Call<TopAnime>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }


        bottomNavigationView = findViewById(R.id.bottomNavigationView) //basta fare l'activity anime list

        /*viewModel = ViewModelProvider(
            this,
            ViewModelCustomFactory(this, this::class.java)
        )[AnimeListActivity::class.java]*/

        /*favouriteListViewModel = ViewModelProvider(
            this, ViewModelCustomFactory(this, this::class.java)
        )[ViewModelFavouriteList::class.java]*/

        // adapter = AnimeListAdapter(this, this)


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
}
