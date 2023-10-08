package studio.demo.subarashi_jpop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.Result

class AnimeListAdapter (
    private val parentActivity: AppCompatActivity,
    private val anime: List<Result>
): RecyclerView.Adapter<AnimeListAdapter.CustomViewHolder>(){

    inner class CustomViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.anime_item_layout, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return anime.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val anime = anime[position]
        val view = holder.itemView
        val name = view.findViewById<TextView>(R.id.animeName)
        val image = view.findViewById<ImageView>(R.id.animeImage)

        name.text = anime.title
        Picasso.get().load(anime.imageUrl).into(image)
    }
}