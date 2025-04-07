package kaiyrzhan.de.empath.features.articles.ui.articles.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_account_circle
import empath.core.uikit.generated.resources.ic_error_filled
import kaiyrzhan.de.empath.core.ui.files.rememberImagePainter
import kaiyrzhan.de.empath.core.ui.modifiers.noRippleClickable
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ArticleComment(
    modifier: Modifier = Modifier,
    comment: String = "LoremIpsum LoremIpsum LoremIpsum LoremIpsum LoremIpsum",
    nickname: String = "Ove1lo1d", //TODO("Need to add nickname and imageUrl")
    fullName: String = "Sansyzbaev Dias Ermekuly", //TODO("Need to add nickname and imageUrl")
    imageUrl: String? = null,
) {
    val authorImagePainter = rememberImagePainter(
        model = imageUrl,
        error = painterResource(Res.drawable.ic_error_filled),
        placeholder = painterResource(Res.drawable.ic_account_circle),
        fallback = painterResource(Res.drawable.ic_account_circle),
        filterQuality = FilterQuality.High,
    )
    val imageState = authorImagePainter.state.collectAsState()

    Column(
        modifier = modifier
            .clip(EmpathTheme.shapes.small)
            .background(EmpathTheme.colors.surfaceContainer)
            .noRippleClickable {
                //TODO("Navigate to comments in detail article)
            }
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Image(
                modifier = Modifier
                    .clip(EmpathTheme.shapes.full)
                    .size(36.dp)
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
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = nickname,
                    style = EmpathTheme.typography.labelSmall,
                    color = EmpathTheme.colors.onSurface,
                )

                Text(
                    text = fullName,
                    style = EmpathTheme.typography.labelSmall,
                    color = EmpathTheme.colors.onSurfaceVariant,
                )
            }
            Text(
                text = "1 year ago",
                style = EmpathTheme.typography.labelSmall,
                color = EmpathTheme.colors.onSurfaceVariant,
                textAlign = TextAlign.End,
            )
        }
        Text(
            text = comment,
            style = EmpathTheme.typography.labelSmall,
            color = EmpathTheme.colors.onSurface,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
        )
    }
}