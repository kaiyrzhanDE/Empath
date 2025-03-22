package kaiyrzhan.de.empath.features.profile.ui.profile.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

@Composable
internal fun Header(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp, bottom = 8.dp),
        text = text,
        style = EmpathTheme.typography.titleSmall,
        color = EmpathTheme.colors.onSurfaceVariant,
    )
}
