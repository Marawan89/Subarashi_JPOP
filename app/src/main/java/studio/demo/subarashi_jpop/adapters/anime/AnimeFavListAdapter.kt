package studio.demo.subarashi_jpop.adapters.anime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity

// interface for handling actions related to anime elimination
interface AnimeFavListAdapterListener {
    fun removeAnimeFromFavourite(anime: AnimeEntity)
}

class AnimeFavListAdapter : RecyclerView.Adapter<AnimeFavListAdapter.AnimeFavouriteViewHolder>() {

    // data source for the adapter
    private var animeList: List<AnimeEntity> = emptyList()
    // listener to handle actions on anime items
    private var listener: AnimeFavListAdapterListener? = null

    // setter for the listener
    fun setListener(listener: AnimeFavListAdapterListener) {
        this.listener = listener
    }

    // view holder class for holding the views of the item layout
    class AnimeFavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeImage: ImageView = itemView.findViewById(R.id.itemAnimeImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemAnimeTitleTextView)
        val deleteIconImageView: ImageView = itemView.findViewById(R.id.deleteIconImageView)
    }

    // method to submit a new list of anime items to the adapter
    fun submitList(newAnimeList: List<AnimeEntity>) {
        animeList = newAnimeList
        notifyDataSetChanged()
    }

    // method to create a new view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeFavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favourite_anime, parent, false)
        return AnimeFavouriteViewHolder(view)
    }

    // method to bind data to a view holder
    override fun onBindViewHolder(holder: AnimeFavouriteViewHolder, position: Int) {
        val anime = animeList[position]

        // load anime image using Picasso library
        Picasso.get().load(anime.imageUrl).into(holder.animeImage)

        holder.titleTextView.text = anime.title


        // on click of the delete icon the query that deletes the anime from the db gets called
        holder.deleteIconImageView.setOnClickListener {

            // notify the listener to remove the anime from favorites
            listener?.removeAnimeFromFavourite(anime)

            animeList = animeList.filterNot { it.id == anime.id }

            notifyDataSetChanged()
            // when an anime gets deleted this toast message pops up
            Toast.makeText(holder.itemView.context, "Anime removed from favourites successfully", Toast.LENGTH_SHORT).show()
        }


    }

    // method to get the item count in the adapter
    override fun getItemCount(): Int {
        return animeList.size
    }
}
