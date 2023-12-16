package studio.demo.subarashi_jpop.adapters.anime

import studio.demo.subarashi_jpop.fragment.AnimeDetailDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.favouriteLocalService.FavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel

class AnimeListAdapter(
    private var animeList: List<AnimeModel>,
    private val favouriteLocalService: FavouriteLocalService
) : RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder>() {

    class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeImage: ImageView = itemView.findViewById(R.id.itemAnimeImageView)
        // val titleTextView: TextView = itemView.findViewById(R.id.itemAnimeTitleTextView)
        // val episodesTextView: TextView = itemView.findViewById(R.id.itemEpisodesTextView)
        val addIcon: ImageView = itemView.findViewById(R.id.anime_add_icon)
    }

    fun setData(newAnimeList: List<AnimeModel>) {
        animeList = newAnimeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }


    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animeList[position]

        Picasso.get().load(anime.images).into(holder.animeImage)


        holder.addIcon.setOnClickListener {
            println("Add icon clicked for anime: ${anime.title}")

            GlobalScope.launch {
                val animeEntity = AnimeEntity(
                    id = anime.mal_id,
                    imageUrl = anime.images,
                    title = anime.title
                )
                favouriteLocalService.addToFavourites(animeEntity)
            }
            holder.addIcon.setImageResource(R.drawable.check_icon)

            Toast.makeText(holder.itemView.context, "Anime added to favourites successfully", Toast.LENGTH_SHORT).show()

        }

        holder.animeImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val dialogFragment = AnimeDetailDialogFragment(anime)
                dialogFragment.show((v?.context as FragmentActivity).supportFragmentManager, "studio.demo.subarashi_jpop.fragment.AnimeDetailDialogFragment")
            }
        })
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}
