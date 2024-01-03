package studio.demo.subarashi_jpop.adapters.anime

import studio.demo.subarashi_jpop.fragment.AnimeDetailDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.favouriteLocalService.FavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.AnimeEntity
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel

interface AnimeListAdapterListener{
    fun addItemToFavourite(item :AnimeEntity)
}

class AnimeListAdapter(
    private var animeList: List<AnimeModel>,
    private val listener: AnimeListAdapterListener
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


    // modificare le liste in modo tale che se un anime o un manga è gia nel db si deve vedere la spuntina, ci deve essere il controllo appena viene partita l'app quindi se un'anime o un manga è gia nel db si deve vedere fin da subito con la spuntina non con il +, e far partire il db subito non solo al click del +
    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animeList[position]

        Picasso.get().load(anime.images).into(holder.animeImage)


        holder.addIcon.setOnClickListener {
            println("Add icon clicked for anime: ${anime.title}")

            val animeEntity = AnimeEntity(
                id = anime.mal_id,
                imageUrl = anime.images,
                title = anime.title
            )
            listener.addItemToFavourite(animeEntity)

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