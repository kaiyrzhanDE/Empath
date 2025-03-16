package kaiyrzhan.de.empath.features.auth.ui.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.features.auth.ui.generated.resources.*
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import empath.features.auth.ui.generated.resources.Res as FeatureRes
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TitledDivider(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .defaultMaxWidth()
            .padding(vertical = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(FeatureRes.string.dividers_placeholder),
            style = EmpathTheme.typography.bodyMedium,
            color = EmpathTheme.colors.outlineVariant,
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}