package studio.demo.subarashi_jpop.adapters.anime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel

class AnimeListAdapter(private var animeList: List<AnimeModel>) : RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder>() {

    class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeImage: ImageView = itemView.findViewById(R.id.itemAnimeImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemAnimeTitleTextView)
        val episodesTextView: TextView = itemView.findViewById(R.id.itemEpisodesTextView)
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

        // Popola gli elementi dell'UI con i dati dell'anime
        Picasso.get().load(anime.images)
            .into(holder.animeImage)

        holder.titleTextView.text = anime.title
        holder.episodesTextView.text = anime.episodes.toString()
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}

