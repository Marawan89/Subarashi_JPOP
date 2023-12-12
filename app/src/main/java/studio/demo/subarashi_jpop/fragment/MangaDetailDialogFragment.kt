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
import java.util.Locale

class MangaDetailDialogFragment(private val manga: MangaModel) : DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.manga_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mangaImage = view.findViewById<ImageView>(R.id.manga_image_detail)
        val mangaTitle = view.findViewById<TextView>(R.id.manga_title)
        val mangaStartDate = view.findViewById<TextView>(R.id.manga_start_Date)
        val mangaEndDate = view.findViewById<TextView>(R.id.manga_end_Date)
        val mangaChapters = view.findViewById<TextView>(R.id.manga_chapters)
        val mangaSynopsis = view.findViewById<TextView>(R.id.manga_synopsis)

        Picasso.get().load(manga.images).into(mangaImage)

        mangaTitle.text = manga.title
        mangaStartDate.text = "Aired from: " + formatDate(manga.published.from)
        mangaEndDate.text = "Aired to: " + if (formatDate(manga.published?.to).isNullOrEmpty()) "Ongoing" else formatDate(manga.published?.to)
        mangaChapters.text = "Chapters: " + (manga.chapters?.toString() ?: "Ongoing")

        val truncatedSynopsis = truncateSynopsis(manga.synopsis, 200)
        val fullSynopsis = truncatedSynopsis + " " + "Know more"
        val spannableString = SpannableString(fullSynopsis)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                openUrl(manga.url)
            }
        }
        spannableString.setSpan(clickableSpan, truncatedSynopsis.length + 1, fullSynopsis.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        mangaSynopsis.text = spannableString
        mangaSynopsis.movementMethod = LinkMovementMethod.getInstance()
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