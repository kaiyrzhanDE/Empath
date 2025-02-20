package kaiyrzhan.de.empath

import androidx.compose.ui.window.ComposeUIViewController
import kaiyrzhan.de.empath.compose.EmpathApp
import kaiyrzhan.de.empath.di.initKoin
import platform.UIKit.UIViewController

@Suppress("unused", "ktlint:standard:function-naming")
public fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        initKoin()
    },
) {
    EmpathApp()
}
