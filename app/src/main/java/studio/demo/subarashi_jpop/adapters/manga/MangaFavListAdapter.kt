package studio.demo.subarashi_jpop.adapters.manga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity

// interface for handling actions related to manga elimination
interface MangaFavListAdapterListener{
    fun removeMangaFromFavourite(manga: MangaEntity)
}

class MangaFavListAdapter() : RecyclerView.Adapter<MangaFavListAdapter.MangaFavouriteViewHolder>() {

    // data source for the adapter
    private var mangaList: List<MangaEntity> = emptyList()
    // listener to handle actions on manga items
    private var listener: MangaFavListAdapterListener? = null

    // setter for the listener
    fun setListener(listener: MangaFavListAdapterListener){
        this.listener = listener
    }

    // view holder class for holding the views of the item layout
    class MangaFavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mangaImage: ImageView = itemView.findViewById(R.id.itemMangaImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemMangaTitleTextView)
        val deleteIconImageView: ImageView = itemView.findViewById(R.id.deleteIconImageView)
    }

    // method to submit a new list of manga items to the adapter
    fun submitList(newMangaList: List<MangaEntity>){
        mangaList = newMangaList
        notifyDataSetChanged()
    }

    // method to create a new view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaFavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favourite_manga, parent, false)
        return MangaFavouriteViewHolder(view)
    }

    // method to bind data to a view holder
    override fun onBindViewHolder(holder: MangaFavouriteViewHolder, position: Int) {
        val manga = mangaList[position]

        // load manga image using Picasso library
        Picasso.get().load(manga.imageUrl).into(holder.mangaImage)

        holder.titleTextView.text = manga.title

        // on click of the delete icon the query that deletes the manga from the db gets called
        holder.deleteIconImageView.setOnClickListener {

            // notify the listener to remove the manga from favorites
            listener?.removeMangaFromFavourite(manga)

            mangaList = mangaList.filterNot { it.id == manga.id }

            notifyDataSetChanged()
            // when a manga gets deleted this toast message pops up
            Toast.makeText(holder.itemView.context, "Manga removed from favourites successfully", Toast.LENGTH_SHORT).show()
        }
    }

    // method to get the item count in the adapter
    override fun getItemCount(): Int {
        return mangaList.size
    }
}