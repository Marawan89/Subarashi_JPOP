package studio.demo.subarashi_jpop.adapters.anime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
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

class AnimeFavouriteAdapter : RecyclerView.Adapter<AnimeFavouriteAdapter.AnimeFavouriteViewHolder>() {

    private var animeList: List<AnimeEntity> = emptyList()

    class AnimeFavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeImage: ImageView = itemView.findViewById(R.id.itemAnimeImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemAnimeTitleTextView)
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
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}

