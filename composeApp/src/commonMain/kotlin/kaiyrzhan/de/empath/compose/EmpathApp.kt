package kaiyrzhan.de.empath.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.auth.ui.AuthScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Suppress("ModifierMissing")
fun EmpathApp() {
    EmpathTheme {
        Scaffold(
            containerColor = EmpathTheme.colors.surfaceDim,
        ) { innerPadding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
            ) {
                AuthScreen()
            }
        }
    }
}

@[Preview Composable]
internal fun EmpathAppPreview() {
    EmpathApp()
}
