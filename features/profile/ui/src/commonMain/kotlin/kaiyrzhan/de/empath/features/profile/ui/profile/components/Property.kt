package kaiyrzhan.de.empath.features.profile.ui.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

@Composable
internal fun Property(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    painter: Painter? = null,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 18.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (painter != null) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painter,
                contentDescription = text,
            )
        }
        Text(
            text = text,
            style = EmpathTheme.typography.titleSmall,
        )
    }
}
