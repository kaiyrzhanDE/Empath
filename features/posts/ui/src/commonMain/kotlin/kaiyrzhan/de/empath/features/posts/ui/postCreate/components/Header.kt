package kaiyrzhan.de.empath.features.posts.ui.postCreate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_ac_unit
import empath.core.uikit.generated.resources.ic_account_circle
import empath.core.uikit.generated.resources.ic_error_filled
import empath.core.uikit.generated.resources.rating
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.files.rememberImagePainter
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun Header(
    modifier: Modifier = Modifier,
    nickname: String,
    rating: Int,
    fullName: String,
    imageUrl: String?,
) {
    val authorImagePainter = rememberImagePainter(
        model = imageUrl,
        error = painterResource(Res.drawable.ic_error_filled),
        placeholder = painterResource(Res.drawable.ic_account_circle),
        fallback = painterResource(Res.drawable.ic_account_circle),
        filterQuality = FilterQuality.High,
    )
    val imageState = authorImagePainter.state.collectAsState()
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .clip(EmpathTheme.shapes.full)
                .aspectRatio(1f)
                .fillMaxSize()
                .thenIf(imageState.value is AsyncImagePainter.State.Loading) {
                    Modifier.shimmerLoading()
                }
                .thenIf(imageState.value is AsyncImagePainter.State.Error) {
                    Modifier.clickable { authorImagePainter.restart() }
                }
                .thenIf(imageState.value is AsyncImagePainter.State.Success) {
                    Modifier.border(
                        width = 1.dp,
                        color = EmpathTheme.colors.onSurfaceVariant,
                        shape = EmpathTheme.shapes.full,
                    )
                },
            painter = authorImagePainter,
            contentScale = ContentScale.Crop,
            contentDescription = "Comment image",
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = nickname,
                    style = EmpathTheme.typography.titleMedium,
                    color = EmpathTheme.colors.onSurface,
                )
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
                        style = EmpathTheme.typography.labelMedium,
                        color = EmpathTheme.colors.onPrimaryContainer,
                    )
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(Res.drawable.ic_ac_unit),
                        contentDescription = null,
                        tint = EmpathTheme.colors.onPrimaryContainer,
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = fullName,
                style = EmpathTheme.typography.labelMedium,
                color = EmpathTheme.colors.onSurfaceVariant,
            )
        }
    }
}
