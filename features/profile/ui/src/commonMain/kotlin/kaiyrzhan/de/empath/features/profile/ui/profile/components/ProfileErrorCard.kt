package kaiyrzhan.de.empath.features.profile.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.error_description
import empath.core.uikit.generated.resources.error_title
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.core.uikit.generated.resources.ic_arrow_forward
import empath.core.uikit.generated.resources.ic_broken_ice
import empath.core.uikit.generated.resources.try_again
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProfileErrorCard(
    modifier: Modifier = Modifier,
    imageSize: Dp,
    onReloadClick: () -> Unit,
    onUserPageClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(EmpathTheme.shapes.small)
            .clickable(onClick = onUserPageClick),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .clip(EmpathTheme.shapes.full)
                .aspectRatio(1f)
                .fillMaxSize(),
            painter = painterResource(CoreRes.drawable.ic_broken_ice),
            contentDescription = "Error",
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(CoreRes.string.error_title),
                style = EmpathTheme.typography.titleMedium,
                color = EmpathTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(CoreRes.string.error_description),
                style = EmpathTheme.typography.titleMedium,
                color = EmpathTheme.colors.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onReloadClick,
            ) {
                Text(
                    text = stringResource(CoreRes.string.try_again),
                    style = EmpathTheme.typography.titleMedium,
                    color = EmpathTheme.colors.primary,
                )
            }
        }
        IconButton(
            onClick = onUserPageClick,
        ) {
            Icon(
                painter = painterResource(CoreRes.drawable.ic_arrow_forward),
                contentDescription = "Open User Page",
                tint = EmpathTheme.colors.onSurfaceVariant,
            )
        }
    }
}