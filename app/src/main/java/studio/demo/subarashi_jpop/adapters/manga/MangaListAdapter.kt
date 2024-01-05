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

interface MangaListAdapterListener{
    fun addMangaToFavourite(manga: MangaEntity)
}

class MangaListAdapter (
    private var mangaList: List<MangaModel>,
    private val listener: MangaListAdapterListener,
    private val mangaRepository: MangaRepository,
    private val lifecycleOwner: LifecycleOwner
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

        // funzione di controllo per vedere se il manga è gia all'interno del db visualizzare l'icona check se no add
        mangaRepository.isMangaFavourite(manga.mal_id).observe(lifecycleOwner, Observer { isFavourite ->
            if (isFavourite) {
                holder.addIcon.setImageResource(R.drawable.check_icon)
            } else {
                holder.addIcon.setImageResource(R.drawable.add_icon)
            }
        })

        holder.addIcon.setOnClickListener{
            println("Add icon clicked for manga: ${manga.title}")

            val mangaEntity = MangaEntity(
                id = manga.mal_id,
                imageUrl = manga.images,
                title = manga.title
            )
            listener.addMangaToFavourite(mangaEntity)

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