package studio.demo.subarashi_jpop.adapters.anime

import android.content.Context
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
        val favoriteIcon: ImageView = itemView.findViewById(R.id.add_icon)
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

<<<<<<< HEAD
        holder.addToFavouritesLayout.setOnClickListener{
            onAddToFavouriteClickListener(anime)
        }
=======
        // Gestisci il clic sull'icona del cuore
        holder.favoriteIcon.setOnClickListener {
            toggleFavoriteState(holder.favoriteIcon, anime)
            anime.isFavorite = !anime.isFavorite
            saveFavoritesLocally(holder.favoriteIcon.context, animeList)
        }

        // Imposta l'icona del cuore come visibile o invisibile
        holder.favoriteIcon.setImageResource(
            if (anime.isFavorite) R.drawable.check_icon
            else R.drawable.add_icon
        )
    }

    private fun toggleFavoriteState(imageView: ImageView, anime: AnimeModel) {
        if (imageView.tag == "unselected") {
            imageView.setImageResource(R.drawable.check_icon)
            imageView.tag = "selected"
        } else {
            imageView.setImageResource(R.drawable.add_icon)
            imageView.tag = "unselected"
        }
    }

    private fun saveFavoritesLocally(context: Context, animeList: List<AnimeModel>) {
        // Implementa il salvataggio della lista dei preferiti in uno storage locale
        // Puoi utilizzare SharedPreferences o un altro metodo di persistenza locale
        val sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(animeList)
        editor.putString("favoriteAnimeList", json)
        editor.apply()
>>>>>>> 8f0b587fda080a56d0fe4f748930fa26baef3bba
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}


