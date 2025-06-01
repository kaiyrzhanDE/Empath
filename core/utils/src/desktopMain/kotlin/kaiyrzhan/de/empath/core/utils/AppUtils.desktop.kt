package kaiyrzhan.de.empath.core.utils

import java.awt.Desktop
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.net.URI

public class DesktopAppUtils : AppUtils {
    override fun openUrl(url: String?) {
        if (url == null) return
        val uri = URI.create(url) ?: return
        Desktop.getDesktop().browse(uri)
    }

    override fun shareText(
        title: String,
        text: String?,
    ) {
        if (text.isNullOrEmpty()) return
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val selection = StringSelection(text)
        clipboard.setContents(selection, selection)
    }
}