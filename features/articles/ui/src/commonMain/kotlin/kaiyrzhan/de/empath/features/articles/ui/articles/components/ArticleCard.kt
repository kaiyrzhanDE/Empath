package kaiyrzhan.de.empath.features.articles.ui.articles.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.modifiers.noRippleClickable
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.components.SelectedTags
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesEvent
import kaiyrzhan.de.empath.features.articles.ui.model.ArticleUi
import kaiyrzhan.de.empath.features.articles.ui.model.TagUi


@Composable
internal fun ArticleCard(
    article: ArticleUi,
    userId: String,
    onEvent: (ArticlesEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(EmpathTheme.shapes.small)
            .background(EmpathTheme.colors.surface)
            .noRippleClickable { onEvent(ArticlesEvent.ArticleClick(article.id)) }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ArticleHeader(
            article = article,
            userId = userId,
            modifier = Modifier.fillMaxWidth(),
            onEvent = onEvent,
        )
        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
        ArticleContent(
            modifier = Modifier.fillMaxWidth(),
            title = article.title,
            description = article.description,
            tags = article.tags,
        )
        ArticleImages(
            imageUrls = article.imageUrls,
            modifier = Modifier.fillMaxWidth(),
        )
        ArticleActions(
            modifier = Modifier.fillMaxWidth(),
            article = article,
            onLikeClick = { onEvent(ArticlesEvent.ArticleLike(article.id)) },
            onShareClick = { onEvent(ArticlesEvent.ArticleShare(article.id)) },
            onDislikeClick = { onEvent(ArticlesEvent.ArticleDislike(article.id)) },
        )
        ArticleComment(
            modifier = Modifier.fillMaxWidth(),

        )
    }
}

@Composable
private fun ArticleContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    tags: List<TagUi>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = title,
            style = EmpathTheme.typography.headlineSmall,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
        SelectedTags(
            modifier = Modifier.fillMaxWidth(),
            tags = tags,
        )
        Text(
            text = description,
            style = EmpathTheme.typography.titleSmall,
            maxLines = 8,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}




