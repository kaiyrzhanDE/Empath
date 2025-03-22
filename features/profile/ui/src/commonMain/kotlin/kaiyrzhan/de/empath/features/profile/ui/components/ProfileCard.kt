package kaiyrzhan.de.empath.features.profile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ProfileCard(
    name: String,
    email: String,
    imageUrl: String,
    onUserPageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(EmpathTheme.shapes.small)
            .clickable(onClick = onUserPageClick),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImage(
            imageUrl = imageUrl,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = name,
                style = EmpathTheme.typography.titleMedium,
                color = EmpathTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = email,
                style = EmpathTheme.typography.titleMedium,
                color = EmpathTheme.colors.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
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

@Composable
private fun ProfileImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    val userImagePainter = rememberAsyncImagePainter(
        model = imageUrl,
        placeholder = painterResource(CoreRes.drawable.ic_account_circle),
        error = painterResource(
            resource = if (imageUrl.isNotBlank()) CoreRes.drawable.ic_error_filled
            else CoreRes.drawable.ic_account_circle,
        ),
    )
    val imageState = userImagePainter.state.collectAsState()

    Image(
        modifier = modifier
            .clip(EmpathTheme.shapes.full)
            .aspectRatio(1f)
            .fillMaxSize()
            .thenIf(imageState.value is AsyncImagePainter.State.Loading) {
                Modifier.shimmerLoading()
            }
            .thenIf(imageState.value is AsyncImagePainter.State.Error) {
                Modifier.clickable { userImagePainter.restart() }
            }
            .thenIf(imageState.value is AsyncImagePainter.State.Success) {
                Modifier.border(
                    width = 2.dp,
                    color = EmpathTheme.colors.onSurfaceVariant,
                    shape = EmpathTheme.shapes.full,
                )
            },
        painter = userImagePainter,
        contentScale = ContentScale.Crop,
        contentDescription = "User Image",
    )
}