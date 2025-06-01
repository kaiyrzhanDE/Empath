package kaiyrzhan.de.empath.core.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIActivityViewController

public class IOSAppUtils() : AppUtils {
    override fun openUrl(url: String?) {
        if (url == null) return
        val nsUrl = NSURL.URLWithString(url) ?: return
        UIApplication.sharedApplication.openURL(nsUrl)
    }

    override fun shareText(
        title: String,
        text: String?,
    ) {
        val activityItems = listOf(text)
        val activityViewController = UIActivityViewController(activityItems, null)

        val application = UIApplication.sharedApplication
        application.keyWindow?.rootViewController?.presentViewController(
            activityViewController,
            animated = true,
            completion = null
        )
    }
}
