package kaiyrzhan.de.empath.features.posts.ui.postCreate.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.extensions.appendRequiredMarker
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.posts.ui.postCreate.model.PostCreateEvent
import kaiyrzhan.de.empath.features.posts.ui.model.postCreate.NewPostUi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ColumnScope.Post(
    post: NewPostUi,
    onEvent: (PostCreateEvent) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = post.title,
        onValueChange = { title ->
            onEvent(PostCreateEvent.PostTitleChange(title))
        },
        label = {
            Text(
                text = buildAnnotatedString {
                    append(stringResource(Res.string.title))
                    appendRequiredMarker()
                },
                style = EmpathTheme.typography.bodyLarge,
            )
        },
        textStyle = EmpathTheme.typography.headlineMedium,
        shape = EmpathTheme.shapes.small,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
        )
    )
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = buildAnnotatedString {
            append(stringResource(Res.string.selected_tags))
            appendRequiredMarker()
        },
        color = EmpathTheme.colors.onSurface,
    )
    if (post.tags.isNotEmpty()) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            post.tags.forEach { tag ->
                Box(
                    modifier = Modifier
                        .clip(EmpathTheme.shapes.small)
                        .background(EmpathTheme.colors.secondaryContainer)
                        .padding(vertical = 6.dp, horizontal = 16.dp),
                ) {
                    Text(
                        text = tag.name,
                        style = EmpathTheme.typography.labelMedium,
                        color = EmpathTheme.colors.onSecondaryContainer,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            contentColor = EmpathTheme.colors.primary,
            containerColor = EmpathTheme.colors.surfaceContainerLow,
        ),
        shape = EmpathTheme.shapes.small,
        border = BorderStroke(
            width = 1.dp,
            color = EmpathTheme.colors.outlineVariant,
        ),
        onClick = {
            onEvent(PostCreateEvent.TagAddClick)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(Res.string.add_tags),
                style = EmpathTheme.typography.labelLarge,
            )
        }
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = post.description,
        shape = EmpathTheme.shapes.small,
        textStyle = EmpathTheme.typography.titleSmall,
        onValueChange = { description ->
            onEvent(PostCreateEvent.PostDescriptionChange(description))
        },
        label = {
            Text(
                text = buildAnnotatedString {
                    append(stringResource(Res.string.description))
                    appendRequiredMarker()
                },
                style = EmpathTheme.typography.bodyLarge,
            )
        },
        minLines = 5,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
        )
    )
    PostImages(
        modifier = Modifier.heightIn(max = 400.dp),
        images = post.images,
        onImagesSelected = { files ->
            onEvent(PostCreateEvent.PostImagesAdd(files))
        },
        onEvent = onEvent,
    )
}