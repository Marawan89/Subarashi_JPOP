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

interface AnimeListAdapterListener{
    fun addAnimeToFavourite(anime :AnimeEntity)
}

class AnimeListAdapter(
    private var animeList: List<AnimeModel>,
    private val listener: AnimeListAdapterListener,
    private val animeRepository: AnimeRepository,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder>() {

    class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeImage: ImageView = itemView.findViewById(R.id.itemAnimeImageView)
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

        // funzione di controllo per vedere se l'anime è già all'interno del db visualizzare l'icona check se no add
        animeRepository.isAnimeFavourite(anime.mal_id).observe(lifecycleOwner, Observer { isFavourite ->
            if (isFavourite) {
                holder.addIcon.setImageResource(R.drawable.check_icon)
            } else {
                holder.addIcon.setImageResource(R.drawable.add_icon)
            }
        })

        holder.addIcon.setOnClickListener {
            println("Add icon clicked for anime: ${anime.title}")

            val animeEntity = AnimeEntity(
                id = anime.mal_id,
                imageUrl = anime.images,
                title = anime.title
            )
            listener.addAnimeToFavourite(animeEntity)

            holder.addIcon.setImageResource(R.drawable.check_icon)
            Toast.makeText(holder.itemView.context, "Anime added to favourites successfully", Toast.LENGTH_SHORT).show()
        }

        holder.animeImage.setOnClickListener { v ->
            val dialogFragment = AnimeDetailDialogFragment(anime)
            dialogFragment.show(
                (v?.context as FragmentActivity).supportFragmentManager,
                "studio.demo.subarashi_jpop.fragment.AnimeDetailDialogFragment"
            )
        }
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}