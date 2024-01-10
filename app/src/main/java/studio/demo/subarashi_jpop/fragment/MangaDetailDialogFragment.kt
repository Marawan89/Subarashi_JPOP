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
import studio.demo.subarashi_jpop.remote.manga.model.MangaModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MangaDetailDialogFragment(private val manga: MangaModel) : DialogFragment() {

    // inflates the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.manga_details, container, false)
    }

    // initializes the UI components and sets up event listeners
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mangaImage = view.findViewById<ImageView>(R.id.manga_image_detail)
        val mangaTitle = view.findViewById<TextView>(R.id.manga_title)
        val mangaStartDate = view.findViewById<TextView>(R.id.manga_start_Date)
        val mangaEndDate = view.findViewById<TextView>(R.id.manga_end_Date)
        val mangaChapters = view.findViewById<TextView>(R.id.manga_chapters)
        val mangaSynopsis = view.findViewById<TextView>(R.id.manga_synopsis)

        // loads the manga image using Picasso
        Picasso.get().load(manga.images).into(mangaImage)

        mangaTitle.text = manga.title
        // setting the "Aired from" text with formatted date so that the date is in the format dd-MM-yyyy
        (getString(R.string.aired_from) + formatDate(manga.published.from)).also { mangaStartDate.text = it }
        // setting the "Aired to" text with formatted date so that the date is in the format dd-MM-yyyy or views "Ongoing" if the date is unknown yet
        "${getString(R.string.aired_to)}${
            if (formatDate(manga.published.to).isEmpty())  getString(R.string.ongoing) else formatDate(
                manga.published.to
            )
        }".also { mangaEndDate.text = it }
        // setting the "Chapters" text with the number of chapters or views "Ongoing" if the chapters are unknown yet
        "Chapters: ${(manga.chapters?.toString() ?: "Ongoing")}".also { mangaChapters.text = it }

        val truncatedSynopsis = manga.synopsis?.let { truncateSynopsis(it, 200) }
        // combining the truncated synopsis with "Know more" with clickable link
        val fullSynopsis = if (!truncatedSynopsis.isNullOrEmpty()) {
            // combining the truncated synopsis with "Know more" with clickable link
            truncatedSynopsis + " " + getString(R.string.know_more)
        } else {
            getString(R.string.no_synopsis_manga)
        }
        val spannableString = SpannableString(fullSynopsis)

        // creating a clickable span to open the URL when clicked
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                openUrl(manga.url)
            }
        }

        if (truncatedSynopsis != null) {
            // adding the clickable span to the "Know more" part of the synopsis
            spannableString.setSpan(
                clickableSpan,
                truncatedSynopsis.length + 1,
                fullSynopsis.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        mangaSynopsis.text = spannableString
        mangaSynopsis.movementMethod = LinkMovementMethod.getInstance()
    }

    // transforms the date into the right format
    private fun formatDate(date: String?): String {
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