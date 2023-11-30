package studio.demo.subarashi_jpop.adapters.manga

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.remote.manga.model.MangaModel

class MangaListAdapter (private var mangaList: List<MangaModel>) : RecyclerView.Adapter<MangaListAdapter.MangaViewHolder>(){

    class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mangaImage: ImageView = itemView.findViewById(R.id.itemImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemTitleTextView)
        val chapterTextView: TextView = itemView.findViewById(R.id.itemChapterTextView)
    }


}