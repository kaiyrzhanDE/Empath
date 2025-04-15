package kaiyrzhan.de.empath.features.articles.ui.articleDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.extensions.appendDot
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.articles.ui.model.ArticleUi
import kotlin.text.appendLine

@Composable
internal fun Article(
    modifier: Modifier = Modifier,
    article: ArticleUi,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = article.title,
            style = EmpathTheme.typography.displayMedium,
        )
        SelectedTags(
            modifier = Modifier.fillMaxWidth(),
            tags = article.tags,
        )
        Text(
            text = article.description,
            style = EmpathTheme.typography.titleSmall,
        )
        Text(
            text = buildAnnotatedString {
                article.subArticles.forEach { subArticle ->
                    appendDot()
                    appendSpace()
                    withStyle(
                        style = SpanStyle(textDecoration = TextDecoration.Underline)
                    ) {
                        append(subArticle.title)
                    }
                    appendLine()
                }
            },
            style = EmpathTheme.typography.titleMedium,
        )
        ArticleImages(
            modifier = Modifier.fillMaxWidth(),
            imageUrls = article.imageUrls,
        )
        article.subArticles.forEach { subArticle ->
            SubArticle(
                modifier = Modifier.fillMaxWidth(),
                subArticle = subArticle,
            )
        }
    }
}