package studio.demo.subarashi_jpop.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.remote.model.AnimeListResponse
import studio.demo.subarashi_jpop.remote.model.AnimeModel

class AnimeListAdapter(private val animeList: List<AnimeModel>):
    RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder>(){
    class AnimeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemTitleTextView)
        val episodesTextView: TextView = itemView.findViewById(R.id.itemEpisodesTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.itemStatusTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val currentAnime = animeList[position]

        Picasso.get().load(currentAnime.images.jpg.image_url).placeholder(R.drawable.placeholder_image).into(holder.imageView)

        holder.titleTextView.text = currentAnime.title
        holder.episodesTextView.text = "Episodes: ${currentAnime.episodes}"
        holder.statusTextView.text = "Status: ${currentAnime.status}"
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}