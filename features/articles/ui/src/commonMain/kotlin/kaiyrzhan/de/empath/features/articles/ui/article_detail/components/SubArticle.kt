package kaiyrzhan.de.empath.features.articles.ui.article_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.articles.ui.model.SubArticleUi

@Composable
internal fun SubArticle(
    modifier: Modifier = Modifier,
    subArticle: SubArticleUi,
) {
    Card(
        shape = EmpathTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
            contentColor = EmpathTheme.colors.onSurface,
        ),
    ) {
        Column(
            modifier = modifier.screenPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (subArticle.title.isNotBlank()) {
                Text(
                    text = subArticle.title,
                    style = EmpathTheme.typography.headlineLarge,
                )
                HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
            }
            if (subArticle.description.isNotBlank()) {
                Text(
                    text = subArticle.description,
                    style = EmpathTheme.typography.titleSmall,
                )
            }
            ArticleImages(
                modifier = Modifier.fillMaxWidth(),
                imageUrls = subArticle.imageUrls,
            )
        }
    }
}