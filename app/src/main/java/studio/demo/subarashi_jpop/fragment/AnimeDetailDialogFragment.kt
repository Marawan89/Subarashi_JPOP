package studio.demo.subarashi_jpop.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import studio.demo.subarashi_jpop.R
import studio.demo.subarashi_jpop.remote.anime.model.AnimeModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AnimeDetailDialogFragment(private val anime: AnimeModel) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.anime_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animeImage = view.findViewById<ImageView>(R.id.anime_image_detail)
        val animeTitle = view.findViewById<TextView>(R.id.anime_title)
        val animeStartDate = view.findViewById<TextView>(R.id.anime_start_Date)
        val animeEndDate = view.findViewById<TextView>(R.id.anime_end_Date)
        val animeEpisodes = view.findViewById<TextView>(R.id.anime_episodes)
        val animeSynopsis = view.findViewById<TextView>(R.id.anime_synopsis)

        Picasso.get().load(anime.images).into(animeImage)
        animeTitle.text = anime.title
        animeStartDate.text = "Aired from: " + formatDate(anime.aired.from)
        animeEndDate.text = "Aired to: " + formatDate(anime.aired.to)
        animeEpisodes.text = "Episodes: " + anime.episodes.toString()

        val truncatedSynopsis = truncateSynopsis(anime.synopsis, 200)
        val fullSynopsis = truncatedSynopsis + " " + "Know more"
        val spannableString = SpannableString(fullSynopsis)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                openUrl(anime.url)
            }
        }
        spannableString.setSpan(clickableSpan, truncatedSynopsis.length + 1, fullSynopsis.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        animeSynopsis.text = spannableString
        animeSynopsis.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun formatDate(date: String?): String {
        return if (date.isNullOrEmpty()) {
            ""
        } else {
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val parsedDate = inputFormat.parse(date)
                outputFormat.format(parsedDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                date
            }
        }
    }

    private fun truncateSynopsis(synopsis: String, maxLength: Int): String {
        return if (synopsis.length > maxLength) {
            synopsis.substring(0, maxLength) + "..."
        } else {
            synopsis
        }
    }

    private fun openUrl(url: String?) {
        if (!url.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}
