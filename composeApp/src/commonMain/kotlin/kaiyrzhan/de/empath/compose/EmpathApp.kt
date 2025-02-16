package kaiyrzhan.de.empath.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Suppress("ModifierMissing")
fun EmpathApp() {
    Surface(
        Modifier.fillMaxSize(),
    ) {
        Text("Hello, Empath!")
    }
}

@[Preview Composable]
internal fun EmpathAppPreview() {
    EmpathApp()
}
