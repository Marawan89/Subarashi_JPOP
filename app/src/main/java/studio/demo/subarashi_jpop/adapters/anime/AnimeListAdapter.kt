package studio.demo.subarashi_jpop.adapters.anime

import studio.demo.subarashi_jpop.fragment.AnimeDetailDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel
import studio.demo.subarashi_jpop.repositories.anime.AnimeRepository

// interface for handling actions related to anime addition
interface AnimeListAdapterListener {
    fun addAnimeToFavourite(anime: AnimeEntity)
}

class AnimeListAdapter(
    private var animeList: List<AnimeModel>,
    private val listener: AnimeListAdapterListener,
    private val animeRepository: AnimeRepository,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder>() {

    // view holder class for holding the views of the item layout
    class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeImage: ImageView = itemView.findViewById(R.id.itemAnimeImageView)
        val addIcon: ImageView = itemView.findViewById(R.id.anime_add_icon)
    }

    // method to set a new data list to the adapter
    fun setData(newAnimeList: List<AnimeModel>) {
        animeList = newAnimeList
        notifyDataSetChanged()
    }

    // method to create a new view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }

    // method to bind data to a view holder
    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animeList[position]

        // load anime image using Picasso library
        Picasso.get().load(anime.images).into(holder.animeImage)

        // check if the anime is already in the database and set the appropriate icon
        animeRepository.isAnimeFavourite(anime.mal_id).observe(lifecycleOwner, Observer { isFavourite ->
            if (isFavourite) {
                holder.addIcon.setImageResource(R.drawable.check_icon)
            } else {
                holder.addIcon.setImageResource(R.drawable.add_icon)
            }
        })

        // on click of the add icon the query that adds the anime to the db gets called
        holder.addIcon.setOnClickListener {

            // create AnimeEntity from AnimeModel
            val animeEntity = AnimeEntity(
                id = anime.mal_id,
                imageUrl = anime.images,
                title = anime.title
            )
            // notify the listener to add the anime to favorites
            listener.addAnimeToFavourite(animeEntity)

            // update the icon to indicate the anime is now in favorites
            holder.addIcon.setImageResource(R.drawable.check_icon)

            // when anime gets added this toast message pops up
            Toast.makeText(holder.itemView.context, "Anime added to favourites successfully", Toast.LENGTH_SHORT).show()
        }

        // set click listener for the anime image to show details
        holder.animeImage.setOnClickListener { v ->
            val dialogFragment = AnimeDetailDialogFragment(anime)
            dialogFragment.show(
                (v?.context as FragmentActivity).supportFragmentManager,
                "studio.demo.subarashi_jpop.fragment.AnimeDetailDialogFragment"
            )
        }
    }

    // method to get the item count in the adapter
    override fun getItemCount(): Int {
        return animeList.size
    }
}