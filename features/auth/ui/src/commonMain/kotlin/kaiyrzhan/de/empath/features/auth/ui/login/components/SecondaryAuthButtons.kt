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
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import empath.features.auth.ui.generated.resources.continue_with_facebook
import empath.features.auth.ui.generated.resources.continue_with_google
import kaiyrzhan.de.empath.core.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.auth.ui.components.defaultMaxWidth
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SecondaryAuthButtons(
    onGoogleAuthClick: () -> Unit,
    onFacebookAuthClick: () -> Unit,
) {
    Column(
        modifier = Modifier.defaultMaxWidth(),
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
                text = stringResource(FeatureRes.string.continue_with_google),
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
                text = stringResource(FeatureRes.string.continue_with_facebook),
                style = EmpathTheme.typography.labelLarge,
                maxLines = 1,
            )
        }
    }
}
