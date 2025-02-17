package kaiyrzhan.de.empath.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.uikit.EmpathTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Suppress("ModifierMissing")
fun EmpathApp() {
    EmpathTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
                .background(color = EmpathTheme.colors.surface),
        ) {
            LoginContent()
        }
    }
}

@Composable
private fun LoginContent(modifier: Modifier = Modifier) {
    var text = remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Em-path",
                style = EmpathTheme.typography.displayLarge,
                color = EmpathTheme.colors.onSurface,
            )
            Text(
                text = "Em-path",
                style = EmpathTheme.typography.labelLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
            )
        }

        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it},
        )
    }
}

@[Preview Composable]
internal fun EmpathAppPreview() {
    EmpathApp()
}
