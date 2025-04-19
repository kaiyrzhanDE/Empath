package kaiyrzhan.de.empath.features.posts.ui.postEdit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import empath.core.uikit.generated.resources.ic_account_circle
import empath.core.uikit.generated.resources.ic_error_filled
import kaiyrzhan.de.empath.core.ui.files.rememberImagePainter
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun Header(
    modifier: Modifier = Modifier,
    nickname: String,
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
            Text(
                text = nickname,
                style = EmpathTheme.typography.labelLarge,
                color = EmpathTheme.colors.onSurface,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = fullName,
                style = EmpathTheme.typography.labelMedium,
                color = EmpathTheme.colors.onSurfaceVariant,
            )
        }
    }
}
