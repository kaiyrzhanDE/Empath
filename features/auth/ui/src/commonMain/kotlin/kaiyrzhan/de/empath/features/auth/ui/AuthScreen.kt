package kaiyrzhan.de.empath.features.auth.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import kaiyrzhan.de.empath.core.uikit.EmpathTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
public fun AuthScreen(modifier: Modifier = Modifier) {
    var tokenFieldValue: String by rememberSaveable { mutableStateOf("") }

    val windowInfo = currentWindowAdaptiveInfo()

    if (windowInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM ||
        windowInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED
    ) {
        Row {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 24.dp)
                    .fillMaxHeight()
                    .weight(1f)
                    .background(color = EmpathTheme.colors.primary, shape = EmpathTheme.shapes.large),
            )

            AuthScreen(
                tokenFieldValue = tokenFieldValue,
                onNextClick = {},
                onRequestTokenClick = {},
                onTokenFieldValueChange = { tokenFieldValue = it },
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 24.dp)
                    .fillMaxSize()
                    .weight(1f),
            )
        }
    } else {
        AuthScreen(
            tokenFieldValue = tokenFieldValue,
            onNextClick = {},
            onRequestTokenClick = {},
            onTokenFieldValueChange = { tokenFieldValue = it },
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
        )
    }
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
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.widthIn(max = 350.dp),
            text = "Welcome",
            maxLines = 1,
            color = EmpathTheme.colors.onSurface,
            style = EmpathTheme.typography.headlineLarge,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            modifier = Modifier.widthIn(max = 350.dp),
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
            modifier = Modifier.widthIn(max = 350.dp),
        )
        Spacer(Modifier.height(48.dp))
        Column(Modifier.wrapContentSize().widthIn(max = 350.dp)) {
            Button(
                onClick = onNextClick,
                shape = EmpathTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Next")
            }
            Spacer(Modifier.height(10.dp))
            Button(
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
            modifier = Modifier.widthIn(max = 350.dp),
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
