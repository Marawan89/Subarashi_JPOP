package studio.demo.subarashi_jpop.adapters.anime

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel

class AnimeListAdapter(
    private var animeList: List<AnimeModel>,
    private val roomFavouriteLocalService: RoomFavouriteLocalService
) : RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder>() {


    class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeImage: ImageView = itemView.findViewById(R.id.itemAnimeImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemAnimeTitleTextView)
        val episodesTextView: TextView = itemView.findViewById(R.id.itemEpisodesTextView)
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

        val truncatedTitle = if (anime.title.length > 14) {
            anime.title.substring(0, 14) + "..."
        } else {
            anime.title
        }
        holder.titleTextView.text = truncatedTitle

        holder.episodesTextView.text = anime.episodes?.toString() ?: "Ongoing"

        holder.addIcon.setOnClickListener{
            println("Add icon clicked for anime: ${anime.title}")
            holder.addIcon.setImageResource(R.drawable.check_icon)

            Toast.makeText(holder.itemView.context, "Anime added to favourites successfully", Toast.LENGTH_SHORT).show()

            val animeEntity = AnimeEntity(
                id = anime.mal_id,
                title = anime.title,
                imageUrl = anime.images
            )

            CoroutineScope(Dispatchers.IO).launch {
                roomFavouriteLocalService.insertAnime(animeEntity)
            }
        }
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}
