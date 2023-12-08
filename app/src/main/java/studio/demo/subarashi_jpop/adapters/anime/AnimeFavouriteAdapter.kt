package studio.demo.subarashi_jpop.adapters.anime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity

class AnimeFavouriteAdapter(
    private var favouriteAnimeList: List<AnimeEntity>,
    private val roomFavouriteLocalService: RoomFavouriteLocalService
) : RecyclerView.Adapter<AnimeFavouriteAdapter.AnimeFavouriteViewHolder>(){

    class AnimeFavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeImage: ImageView = itemView.findViewById(R.id.itemAnimeImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemAnimeTitleTextView)
        val addIcon: ImageView = itemView.findViewById(R.id.add_icon)
    }


    fun setData(newFavouriteAnimeList: List<AnimeEntity>) {
        favouriteAnimeList = newFavouriteAnimeList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeFavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeFavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeFavouriteViewHolder, position: Int) {
        val anime = favouriteAnimeList[position]

        Picasso.get().load(anime.imageUrl).into(holder.animeImage)

        val truncatedTitle = if (anime.title.length > 16) {
            anime.title.substring(0, 16) + "..."
        } else {
            anime.title
        }
        holder.titleTextView.text = truncatedTitle

        val animeEntity = AnimeEntity(
            id = anime.id,
            title = anime.title,
            imageUrl = anime.imageUrl
        )

        CoroutineScope(Dispatchers.IO).launch {
            roomFavouriteLocalService.insertAnime(animeEntity)
            println("Anime added to favorites: ${animeEntity.title}")
        }
    }

    override fun getItemCount(): Int {
        return favouriteAnimeList.size
    }
}