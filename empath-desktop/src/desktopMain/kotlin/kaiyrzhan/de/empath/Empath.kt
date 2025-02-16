@file:JvmName("Empath")

package kaiyrzhan.de.empath

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kaiyrzhan.de.empath.compose.EmpathApp

@Suppress("ktlint:standard:function-signature")
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Empath",
    ) {
        EmpathApp()
    }
}
