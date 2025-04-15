package kaiyrzhan.de.empath.features.articles.ui.articleCreate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.articles.ui.articleCreate.model.ArticleCreateEvent
import kaiyrzhan.de.empath.features.articles.ui.model.article_create.NewSubArticleUi
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
internal fun SubArticle(
    modifier: Modifier = Modifier,
    subArticle: NewSubArticleUi,
    position: Int,
    onEvent: (ArticleCreateEvent) -> Unit
) {
    val subArticlePosition = remember(position) {
        position.plus(1).toGroupedString()
    }
    Column(
        modifier = modifier
            .clip(EmpathTheme.shapes.small)
            .background(EmpathTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                shape = EmpathTheme.shapes.small,
                colors = CardDefaults.cardColors(
                    containerColor = EmpathTheme.colors.surfaceContainer,
                    contentColor = EmpathTheme.colors.onSurface,
                ),
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 4.dp,
                        vertical = 2.dp,
                    ),
                    text = subArticlePosition,
                    style = EmpathTheme.typography.labelLarge,
                    color = EmpathTheme.colors.onSurface,
                )
            }

            TextButton(
                onClick = { onEvent(ArticleCreateEvent.SubArticleRemove(subArticle.id)) },
            ) {
                Text(
                    text = stringResource(Res.string.remove_sub_article),
                    style = EmpathTheme.typography.labelLarge,
                    color = EmpathTheme.colors.tertiary,
                )
            }
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = subArticle.title,
            textStyle = EmpathTheme.typography.headlineLarge,
            onValueChange = { title ->
                onEvent(
                    ArticleCreateEvent.SubArticleTitleChange(
                        id = subArticle.id,
                        title = title,
                    )
                )
            },
            label = {
                Text(
                    text = stringResource(Res.string.title),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = subArticle.description,
            textStyle = EmpathTheme.typography.titleSmall,
            onValueChange = { description ->
                onEvent(
                    ArticleCreateEvent.SubArticleDescriptionChange(
                        id = subArticle.id,
                        description = description,
                    )
                )
            },
            label = {
                Text(
                    text = stringResource(Res.string.description),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            minLines = 5,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = EmpathTheme.colors.outlineVariant,
            )
        )
        ArticleImages(
            modifier = Modifier.heightIn(max = 400.dp),
            images = subArticle.images,
            onImagesSelected = { files ->
                onEvent(
                    ArticleCreateEvent.SubArticleImageAdd(
                        id = subArticle.id,
                        files = files,
                    )
                )
            },
            onEvent = onEvent,
        )
    }
}