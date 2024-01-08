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

    // inflates the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.anime_details, container, false)
    }

    // initializes the UI components and sets up event listeners
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animeImage = view.findViewById<ImageView>(R.id.anime_image_detail)
        val animeTitle = view.findViewById<TextView>(R.id.anime_title)
        val animeStartDate = view.findViewById<TextView>(R.id.anime_start_Date)
        val animeEndDate = view.findViewById<TextView>(R.id.anime_end_Date)
        val animeEpisodes = view.findViewById<TextView>(R.id.anime_episodes)
        val animeSynopsis = view.findViewById<TextView>(R.id.anime_synopsis)

        // loads the anime image using Picasso
        Picasso.get().load(anime.images).into(animeImage)

        animeTitle.text = anime.title
        // setting the "Aired from" text with formatted date so that the date is in the format dd-MM-yyyy
        (getString(R.string.aired_from) + formatAiredDate(anime.aired.from)).also { animeStartDate.text = it }
        // setting the "Aired to" text with formatted date so that the date is in the format dd-MM-yyyy or views "Ongoing" if the date is unknown yet
        "${getString(R.string.aired_to)}${
            if (formatAiredDate(anime.aired.to).isEmpty()) getString(R.string.ongoing) else formatAiredDate(
                anime.aired.to
            )
        }".also { animeEndDate.text = it }
        // setting the "Episodes" text with the number of episodes or views "Ongoing" if the episodes are unknown yet
        "${getString(R.string.episodes)} ${(anime.episodes?.toString() ?: getString(R.string.ongoing))}".also { animeEpisodes.text = it }

        val truncatedSynopsis = anime.synopsis?.let { truncateSynopsis(it, 200) }
        // combining the truncated synopsis with "Know more" with clickable link
        val fullSynopsis = truncatedSynopsis + " " + getString(R.string.know_more)
        val spannableString = SpannableString(fullSynopsis)

        // creating a clickable span to open the URL when clicked
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                openUrl(anime.url)
            }
        }

        if (truncatedSynopsis != null) {
            // adding the clickable span to the "Know more" part of the synopsis
            spannableString.setSpan(clickableSpan, truncatedSynopsis.length + 1, fullSynopsis.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        animeSynopsis.text = spannableString
        animeSynopsis.movementMethod = LinkMovementMethod.getInstance()
    }

    // transforms the date into the right format
    private fun formatAiredDate(date: String?): String {
        return if (date.isNullOrEmpty()) {
            ""
        } else {
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val parsedDate = inputFormat.parse(date)
                outputFormat.format(parsedDate ?: Date())
            } catch (e: ParseException) {
                e.printStackTrace()
                date
            }
        }
    }

    // executes the truncation of the synopsis text
    private fun truncateSynopsis(synopsis: String, maxLength: Int): String {
        return if (synopsis.length > maxLength) {
            synopsis.substring(0, maxLength) + "..."
        } else {
            synopsis
        }
    }

    // makes the link clickable
    private fun openUrl(url: String?) {
        if (!url.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}