package kaiyrzhan.de.empath.core.utils

import java.awt.Desktop
import java.net.URI

public class DesktopAppUtils : AppUtils {
    override fun openUrl(url: String?) {
        if (url == null) return
        val uri = URI.create(url) ?: return
        Desktop.getDesktop().browse(uri)
    }
}