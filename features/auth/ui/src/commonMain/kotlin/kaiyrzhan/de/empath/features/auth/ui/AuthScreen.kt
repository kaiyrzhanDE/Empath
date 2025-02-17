package kaiyrzhan.de.empath.features.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.uikit.EmpathTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
public fun AuthScreen(modifier: Modifier = Modifier) {
    var tokenFieldValue: String by rememberSaveable { mutableStateOf("") }
    AuthScreen(
        tokenFieldValue = tokenFieldValue,
        onTokenFieldValueChange = { tokenFieldValue = it },
        onNextClick = {},
        onRequestTokenClick = {},
        modifier = modifier,
    )
}

@Composable
internal fun AuthScreen(
    tokenFieldValue: String,
    onTokenFieldValueChange: (String) -> Unit,
    onNextClick: () -> Unit,
    onRequestTokenClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = "Welcome",
            maxLines = 1,
            color = EmpathTheme.colors.onSurface,
            style = EmpathTheme.typography.headlineLarge,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Enter your email or token to log in.",
            maxLines = 2,
            color = EmpathTheme.colors.onSurfaceVariant,
            style = EmpathTheme.typography.labelLarge,
        )
        Spacer(Modifier.height(48.dp))
        OutlinedTextField(
            value = tokenFieldValue,
            onValueChange = onTokenFieldValueChange,
            label = {
                Text(
                    "E-mail / Token",
                    color = EmpathTheme.colors.primary,
                    maxLines = 1,
                    style = EmpathTheme.typography.bodySmall,
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(48.dp))
        Column(Modifier.wrapContentSize()) {
            FilledTonalButton(
                onClick = onNextClick,
                shape = EmpathTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Next")
            }
            Spacer(Modifier.height(10.dp))
            FilledTonalButton(
                onClick = onRequestTokenClick,
                shape = EmpathTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Request a token")
            }
        }
        Spacer(Modifier.height(24.dp))
        Text(
            "Don’t have an account? Sign Up",
            maxLines = 2,
            color = EmpathTheme.colors.onSurfaceVariant,
            style = EmpathTheme.typography.labelMedium,
        )
    }
}

@Preview
@Composable
internal fun AuthScreenPreview() {
    EmpathTheme {
        AuthScreen(Modifier.fillMaxSize())
    }
}
