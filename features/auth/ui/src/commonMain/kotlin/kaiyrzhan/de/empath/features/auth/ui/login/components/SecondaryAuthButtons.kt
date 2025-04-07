package kaiyrzhan.de.empath.features.auth.ui.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SecondaryAuthButtons(
    modifier: Modifier = Modifier,
    onGoogleAuthClick: () -> Unit,
    onFacebookAuthClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onGoogleAuthClick,
            shape = EmpathTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = EmpathTheme.colors.onScrim,
                contentColor = EmpathTheme.colors.shadow,
                disabledContentColor = EmpathTheme.colors.onSurface,
                disabledContainerColor = EmpathTheme.colors.surfaceBright
            ),
        ) {
            Text(
                text = stringResource(Res.string.continue_with_google),
                style = EmpathTheme.typography.labelLarge,
                maxLines = 1,
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onFacebookAuthClick,
            shape = EmpathTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = EmpathTheme.colors.primary,
                contentColor = EmpathTheme.colors.onPrimary,
                disabledContentColor = EmpathTheme.colors.onSurface,
                disabledContainerColor = EmpathTheme.colors.surfaceBright
            ),
        ) {
            Text(
                text = stringResource(Res.string.continue_with_facebook),
                style = EmpathTheme.typography.labelLarge,
                maxLines = 1,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SecondaryAuthButtons(
        modifier = Modifier.defaultMaxWidth(),
        onGoogleAuthClick = {},
        onFacebookAuthClick = {},
    )
}
