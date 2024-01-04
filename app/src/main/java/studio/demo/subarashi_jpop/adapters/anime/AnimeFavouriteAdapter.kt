package studio.demo.subarashi_jpop.adapters.anime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.favouriteLocalService.FavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel

interface AnimeFavouriteAdapterListener {
    fun removeAnimeFromFavourite(anime: AnimeEntity)
}

class AnimeFavouriteAdapter() : RecyclerView.Adapter<AnimeFavouriteAdapter.AnimeFavouriteViewHolder>() {

    private var animeList: List<AnimeEntity> = emptyList()
    private var listener: AnimeFavouriteAdapterListener? = null

    // Aggiungi un metodo per impostare il listener
    fun setListener(listener: AnimeFavouriteAdapterListener) {
        this.listener = listener
    }

    class AnimeFavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeImage: ImageView = itemView.findViewById(R.id.itemAnimeImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemAnimeTitleTextView)
        val deleteIconImageView: ImageView = itemView.findViewById(R.id.deleteIconImageView)
    }

    fun submitList(newAnimeList: List<AnimeEntity>) {
        animeList = newAnimeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeFavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favourite_anime, parent, false)
        return AnimeFavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeFavouriteViewHolder, position: Int) {
        val anime = animeList[position]

        Picasso.get().load(anime.imageUrl).into(holder.animeImage)

        holder.titleTextView.text = anime.title

        holder.deleteIconImageView.setOnClickListener {
            println("Delete icon clicked for anime: ${anime.title}")

            // Rimuovi l'anime dai preferiti
            listener?.removeAnimeFromFavourite(anime)

            // Opzionalmente, puoi anche rimuovere l'anime dalla lista locale immediatamente
            animeList = animeList.filterNot { it.id == anime.id }

            notifyDataSetChanged()
            Toast.makeText(holder.itemView.context, "Anime removed from favourites successfully", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}

