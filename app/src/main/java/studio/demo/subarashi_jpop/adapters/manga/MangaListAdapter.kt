package studio.demo.subarashi_jpop.adapters.manga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.remote.manga.model.MangaModel

class MangaListAdapter (private var mangaList: List<MangaModel>) : RecyclerView.Adapter<MangaListAdapter.MangaViewHolder>(){

    class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mangaImage: ImageView = itemView.findViewById(R.id.itemMangaImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemMangaTitleTextView)
        val chapterTextView: TextView = itemView.findViewById(R.id.itemChaptersTextView)
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

        val truncatedTitle = if (manga.title.length > 16) {
            manga.title.substring(0, 16) + "..."
        } else {
            manga.title
        }
        holder.titleTextView.text = truncatedTitle
        holder.chapterTextView.text = manga.chapters?.toString() ?: "Ongoing"
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }
}