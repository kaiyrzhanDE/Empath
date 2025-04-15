package kaiyrzhan.de.empath.features.articles.ui.articleDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.articles.ui.model.TagUi

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SelectedTags(
    modifier: Modifier = Modifier,
    tags: List<TagUi>,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        tags.forEach { tag ->
            Card(
                shape = EmpathTheme.shapes.small,
                colors = CardDefaults.cardColors(
                    contentColor = EmpathTheme.colors.onPrimaryContainer,
                    containerColor = EmpathTheme.colors.primaryContainer,
                ),
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 6.dp
                    ),
                    text = tag.name,
                    style = EmpathTheme.typography.labelLarge,
                )
            }
        }
    }
}