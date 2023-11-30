package studio.demo.subarashi_jpop.adapters.manga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.remote.manga.model.MangaModel

class MangaListAdapter (private var mangaList: List<MangaModel>) : RecyclerView.Adapter<MangaListAdapter.MangaViewHolder>(){

    class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mangaImage: ImageView = itemView.findViewById(R.id.itemMangaImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemMangaTitleTextView)
        val chapterTextView: TextView = itemView.findViewById(R.id.itemChapterTextView)
    }

    fun setData(newMangaList:List<MangaModel>){
        mangaList = newMangaList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
        return MangaViewHolder(view)
    }
}