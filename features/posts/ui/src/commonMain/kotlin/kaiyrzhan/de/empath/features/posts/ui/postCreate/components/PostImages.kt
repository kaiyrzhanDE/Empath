package kaiyrzhan.de.empath.features.posts.ui.postCreate.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.select_images
import empath.core.uikit.generated.resources.select_photo
import empath.core.uikit.generated.resources.selected_images
import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.core.ui.files.rememberImagesPicker
import kaiyrzhan.de.empath.core.ui.modifiers.wideRatio
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.posts.ui.postCreate.model.PostCreateEvent
import kaiyrzhan.de.empath.features.posts.ui.model.ImageUi
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
internal fun ColumnScope.PostImages(
    modifier: Modifier = Modifier,
    images: List<ImageUi>,
    onImagesSelected: (List<PlatformFile>) -> Unit,
    onEvent: (PostCreateEvent) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    val imagesLauncher = rememberImagesPicker(
        title = stringResource(Res.string.select_photo),
        maxItems = 3,
    ) { files ->
        onImagesSelected(files)
    }
    if (images.isNotEmpty()) {
        Text(
            text = stringResource(Res.string.selected_images),
            style = EmpathTheme.typography.bodyLarge,
            color = EmpathTheme.colors.primary,
        )
    }

    BoxWithConstraints(
        modifier = modifier,
    ) {
        val height = maxWidth * 9 / 16
        if (images.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .border(
                        width = 1.dp,
                        color = EmpathTheme.colors.outlineVariant,
                        shape = EmpathTheme.shapes.small,
                    ),
                state = lazyListState,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                itemsIndexed(
                    items = images,
                    key = { _, image -> image.id },
                ) { index, image ->
                    PostImage(
                        modifier = Modifier.wideRatio(),
                        image = image,
                        index = index,
                        onEvent = onEvent,
                    )
                }
            }
        } else {
            PostImagePlaceholder(
                modifier = Modifier
                    .size(width = maxWidth, height = height),
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = imagesLauncher::launch,
        ) {
            Text(
                text = stringResource(Res.string.select_images),
                style = EmpathTheme.typography.labelLarge,
                color = EmpathTheme.colors.primary,
            )
        }
    }
}


