package kaiyrzhan.de.empath.features.profile.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.core.uikit.generated.resources.*
import empath.core.uikit.generated.resources.Res
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.files.rememberImagePainter
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProfileCard(
    name: String,
    email: String,
    imageUrl: String?,
    rating: Int,
    onUserPageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = name,
                    style = EmpathTheme.typography.titleMedium,
                    color = EmpathTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (windowAdaptiveInfo.isPhone().not()) {
                    Row(
                        modifier = Modifier
                            .clip(EmpathTheme.shapes.small)
                            .background(EmpathTheme.colors.primaryContainer)
                            .padding(horizontal = 6.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = buildString {
                                append(stringResource(Res.string.rating))
                                appendColon()
                                appendSpace()
                                append(rating.toGroupedString())
                            },
                            style = EmpathTheme.typography.bodyLarge,
                            color = EmpathTheme.colors.onPrimaryContainer,
                        )
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(CoreRes.drawable.ic_ac_unit),
                            contentDescription = null,
                            tint = EmpathTheme.colors.onPrimaryContainer,
                        )
                    }
                }
            }
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
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    val userImagePainter = rememberImagePainter(
        model = imageUrl,
        error = painterResource(Res.drawable.ic_error_filled),
        fallback = painterResource(Res.drawable.ic_account_circle),
        placeholder = painterResource(Res.drawable.ic_account_circle),
        filterQuality = FilterQuality.High,
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