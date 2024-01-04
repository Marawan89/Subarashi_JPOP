package studio.demo.subarashi_jpop.adapters.manga

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
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.adapters.anime.AnimeFavouriteAdapterListener
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

interface MangaFavouriteAdapterListener{
    fun removeMangaFromFavourite(manga: MangaEntity)
}

class MangaFavouriteAdapter() : RecyclerView.Adapter<MangaFavouriteAdapter.MangaFavouriteViewHolder>() {

    private var mangaList: List<MangaEntity> = emptyList()
    private var listener: MangaFavouriteAdapterListener? = null

    fun setListener(listener: MangaFavouriteAdapterListener){
        this.listener = listener
    }

    class MangaFavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mangaImage: ImageView = itemView.findViewById(R.id.itemMangaImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemMangaTitleTextView)
        val deleteIconImageView: ImageView = itemView.findViewById(R.id.deleteIconImageView)
    }

    fun submitList(newMangaList: List<MangaEntity>){
        mangaList = newMangaList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaFavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favourite_manga, parent, false)
        return MangaFavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: MangaFavouriteViewHolder, position: Int) {
        val manga = mangaList[position]

        Picasso.get().load(manga.imageUrl).into(holder.mangaImage)

        holder.titleTextView.text = manga.title

        holder.deleteIconImageView.setOnClickListener {
            println("Delete icon clicked for anime: ${manga.title}")

            listener?.removeMangaFromFavourite(manga)

            // Opzionalmente, puoi anche rimuovere l'anime dalla lista locale immediatamente
            mangaList = mangaList.filterNot { it.id == manga.id }

            notifyDataSetChanged()
            Toast.makeText(holder.itemView.context, "Manga removed from favourites successfully", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }
}