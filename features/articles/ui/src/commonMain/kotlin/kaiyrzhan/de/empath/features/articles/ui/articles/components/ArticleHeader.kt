package kaiyrzhan.de.empath.features.articles.ui.articles.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.articles.ui.articles.model.ArticlesEvent
import kaiyrzhan.de.empath.features.articles.ui.model.ArticleUi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ArticleHeader(
    article: ArticleUi,
    modifier: Modifier = Modifier,
    userId: String,
    onEvent: (ArticlesEvent) -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UserImage(
            imageUrl = article.author.imageUrl,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = article.author.nickname,
                style = EmpathTheme.typography.titleMedium,
                color = EmpathTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = article.author.fullName,
                style = EmpathTheme.typography.labelMedium,
                color = EmpathTheme.colors.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (userId == article.author.id) {
            Box(
                modifier = Modifier.size(60.dp),
                contentAlignment = Alignment.Center,
            ) {
                IconButton(
                    onClick = { isExpanded = true }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_more_vert),
                        contentDescription = "Article more options",
                    )
                }
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(Res.string.edit),
                            )
                        },
                        onClick = {
                            onEvent(ArticlesEvent.ArticleEdit(article.id))
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(Res.string.delete),
                            )
                        },
                        onClick = {
                            onEvent(ArticlesEvent.ArticleDelete(article.id))
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}