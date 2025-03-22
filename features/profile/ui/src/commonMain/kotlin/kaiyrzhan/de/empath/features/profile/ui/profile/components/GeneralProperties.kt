package kaiyrzhan.de.empath.features.profile.ui.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.features.profile.ui.generated.resources.Res as FeatureRes
import empath.core.uikit.generated.resources.*
import empath.features.profile.ui.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun GeneralProperties(
    modifier: Modifier = Modifier,
    onLogOutClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
            contentColor = EmpathTheme.colors.onSurface,
        ),
    ) {
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "Settings",
            painter = painterResource(CoreRes.drawable.ic_error),
            onClick = { /* TODO */ },
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = EmpathTheme.colors.outlineVariant,
        )
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "FAQ",
            painter = painterResource(CoreRes.drawable.ic_error),
            onClick = { /* TODO */ },
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = EmpathTheme.colors.outlineVariant,
        )
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "Share Feedback",
            painter = painterResource(CoreRes.drawable.ic_error),
            onClick = { /* TODO */ },
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = EmpathTheme.colors.outlineVariant,
        )
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "Privacy Policy and Agreement",
            painter = painterResource(CoreRes.drawable.ic_error),
            onClick = { /* TODO */ },
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = EmpathTheme.colors.outlineVariant,
        )
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(FeatureRes.string.log_out),
            onClick = onLogOutClick,
        )
    }
}
