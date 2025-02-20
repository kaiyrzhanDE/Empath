@file:JvmName("Empath")

package kaiyrzhan.de.empath

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kaiyrzhan.de.empath.compose.EmpathApp
import kaiyrzhan.de.empath.di.initKoin

@Suppress("ktlint:standard:function-signature")
public fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Empath",
        ) {
            EmpathApp()
        }
    }
}
