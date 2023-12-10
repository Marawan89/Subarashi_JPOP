package studio.demo.subarashi_jpop.adapters.manga

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
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

class MangaFavouriteAdapter(
    private var favouriteMangaList: List<MangaEntity>
    private val roomFavouriteLocalService: RoomFavouriteLocalService
) : RecyclerView.Adapter<MangaFavouriteAdapter.MangaFavouriteViewHolder>() {

    class MangaFavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mangaImage: ImageView = itemView.findViewById(R.id.itemMangaImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemMangaTitleTextView)
    }

    fun setData(newFavouriteMangaList: List<MangaEntity>){
        favouriteMangaList = newFavouriteMangaList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaFavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
        return MangaFavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: MangaFavouriteViewHolder, position: Int) {
        val manga = favouriteMangaList[position]

        Picasso.get().load(manga.imageUrl).into(holder.mangaImage)

        val truncatedTitle = if (manga.title.length > 14) {
            manga.title.substring(0, 14) + "..."
        } else {
            manga.title
        }
        holder.titleTextView.text = truncatedTitle

        val mangaEntity = MangaEntity(
            id = manga.id,
            title = manga.title,
            imageUrl = manga.imageUrl
        )

        CoroutineScope(Dispatchers.IO).launch {
            roomFavouriteLocalService.insertManga(mangaEntity)
            print("Manga added to favourites: ${mangaEntity.title}")
        }
    }

    override fun getItemCount(): Int {
        return favouriteMangaList.size
    }
}