package studio.demo.subarashi_jpop.adapters.anime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel

class AnimeListAdapter(private var animeList: MutableList<AnimeModel>, private val onAddToFavouriteClickListener: (AnimeModel) -> Unit) : RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder>() {

    class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addToFavouritesLayout: LinearLayout = itemView.findViewById(R.id.addToFavoriteLayout)
        val animeImage: ImageView = itemView.findViewById(R.id.itemAnimeImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemAnimeTitleTextView)
        val episodesTextView: TextView = itemView.findViewById(R.id.itemEpisodesTextView)
    }

    fun setData(newAnimeList: List<AnimeModel>) {
        animeList.clear()
        animeList.addAll(newAnimeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }


    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animeList[position]

        Picasso.get().load(anime.images).into(holder.animeImage)

        val truncatedTitle = if (anime.title.length > 16) {
            anime.title.substring(0, 16) + "..."
        } else {
            anime.title
        }
        holder.titleTextView.text = truncatedTitle

        holder.episodesTextView.text = anime.episodes?.toString() ?: "Ongoing"

        holder.addToFavouritesLayout.setOnClickListener{
            onAddToFavouriteClickListener(anime)
        }
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}

