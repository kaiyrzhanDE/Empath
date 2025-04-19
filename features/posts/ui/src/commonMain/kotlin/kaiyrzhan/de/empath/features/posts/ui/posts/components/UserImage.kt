package kaiyrzhan.de.empath.features.posts.ui.posts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
internal fun UserImage(
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
            .size(60.dp)
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