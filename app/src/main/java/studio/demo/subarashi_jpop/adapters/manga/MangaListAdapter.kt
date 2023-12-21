package studio.demo.subarashi_jpop.adapters.manga

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.favouriteLocalService.FavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.RoomFavouriteLocalService
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity
import studio.demo.subarashi_jpop.fragment.MangaDetailDialogFragment
import studio.demo.subarashi_jpop.remote.manga.model.MangaModel

class MangaListAdapter (
    private var mangaList: List<MangaModel>,
    private val favouriteLocalService: FavouriteLocalService
) : RecyclerView.Adapter<MangaListAdapter.MangaViewHolder>(){

    class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mangaImage: ImageView = itemView.findViewById(R.id.itemMangaImageView)
        val addIcon: ImageView = itemView.findViewById(R.id.manga_add_icon)
    }

    fun setData(newMangaList:List<MangaModel>){
        mangaList = newMangaList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
        return MangaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga = mangaList[position]

        Picasso.get().load(manga.images).into(holder.mangaImage)

        holder.addIcon.setOnClickListener{
            println("Add icon clicked for manga: ${manga.title}")

           GlobalScope.launch {
                val mangaEntity = MangaEntity(
                    id = manga.mal_id,
                    imageUrl = manga.images,
                    title = manga.title
                )
               favouriteLocalService.addMangaToFavourites(mangaEntity)
          }
            holder.addIcon.setImageResource(R.drawable.check_icon)
            Toast.makeText(holder.itemView.context, "Manga added to favourites successfully", Toast.LENGTH_SHORT).show()
        }

        holder.mangaImage.setOnClickListener { v ->
            val dialogFragment = MangaDetailDialogFragment(manga)
            dialogFragment.show(
                (v?.context as FragmentActivity).supportFragmentManager,
                "studio.demo.subarashi_jpop.fragment.MangaDetailDialogFragment"
            )
        }
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }
}