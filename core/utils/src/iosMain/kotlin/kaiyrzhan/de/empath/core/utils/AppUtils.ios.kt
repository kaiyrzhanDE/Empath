package kaiyrzhan.de.empath.core.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

public class IOSAppUtils() : AppUtils {
    override fun openUrl(url: String?) {
        if (url == null) return
        val nsUrl = NSURL.URLWithString(url) ?: return
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}
