package studio.demo.subarashi_jpop.adapters.manga

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
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.entities.MangaEntity
import studio.demo.subarashi_jpop.fragment.MangaDetailDialogFragment
import studio.demo.subarashi_jpop.remote.manga.model.MangaModel
import studio.demo.subarashi_jpop.repositories.manga.MangaRepository

// interface for handling actions related to manga addition
interface MangaListAdapterListener{
    fun addMangaToFavourite(manga: MangaEntity)
}

class MangaListAdapter (
    private var mangaList: List<MangaModel>,
    private val listener: MangaListAdapterListener,
    private val mangaRepository: MangaRepository,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<MangaListAdapter.MangaViewHolder>(){

    // view holder class for holding the views of the item layout
    class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mangaImage: ImageView = itemView.findViewById(R.id.itemMangaImageView)
        val addIcon: ImageView = itemView.findViewById(R.id.manga_add_icon)
    }

    // method to set a new data list to the adapter
    fun setData(newMangaList:List<MangaModel>){
        mangaList = newMangaList
        notifyDataSetChanged()
    }

    // method to create a new view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
        return MangaViewHolder(view)
    }

    // method to bind data to a view holder
    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga = mangaList[position]

        // load manga image using Picasso library
        Picasso.get().load(manga.images).into(holder.mangaImage)

        // check if the manga is already in the database and set the appropriate icon
        mangaRepository.isMangaFavourite(manga.mal_id).observe(lifecycleOwner, Observer { isFavourite ->
            if (isFavourite) {
                holder.addIcon.setImageResource(R.drawable.check_icon)
            } else {
                holder.addIcon.setImageResource(R.drawable.add_icon)
            }
        })

        // on click of the add icon the query that adds the manga to the db gets called
        holder.addIcon.setOnClickListener{

            // create MangaEntity from MangaModel
            val mangaEntity = MangaEntity(
                id = manga.mal_id,
                imageUrl = manga.images,
                title = manga.title
            )
            // notify the listener to add manga to favourites
            listener.addMangaToFavourite(mangaEntity)

            // update the icon to indicate the manga is now in favourites
            holder.addIcon.setImageResource(R.drawable.check_icon)

            // when manga gets added this toast message pops up
            Toast.makeText(holder.itemView.context, "Manga added to favourites successfully", Toast.LENGTH_SHORT).show()
        }

        // set click listener for the manga image to show details
        holder.mangaImage.setOnClickListener { v ->
            val dialogFragment = MangaDetailDialogFragment(manga)
            dialogFragment.show(
                (v?.context as FragmentActivity).supportFragmentManager,
                "studio.demo.subarashi_jpop.fragment.MangaDetailDialogFragment"
            )
        }
    }

    // method to get the item count in the adapter
    override fun getItemCount(): Int {
        return mangaList.size
    }
}