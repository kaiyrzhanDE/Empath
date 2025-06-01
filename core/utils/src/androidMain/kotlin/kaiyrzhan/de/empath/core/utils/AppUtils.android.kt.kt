package kaiyrzhan.de.empath.core.utils

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

public class AndroidAppUtils(private val context: Context) : AppUtils {
    override fun openUrl(url: String?) {
        if (url == null) return
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        context.startActivity(intent)
    }

    override fun shareText(
        title: String,
        text: String?,
    ) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        context.startActivity(
            Intent.createChooser(shareIntent, title)
        )
    }
}