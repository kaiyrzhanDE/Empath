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
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
            text = stringResource(Res.string.dividers_placeholder),
            style = EmpathTheme.typography.bodyMedium,
            color = EmpathTheme.colors.outlineVariant,
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun Preview(){
    TitledDivider()
}