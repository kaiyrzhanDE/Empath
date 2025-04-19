package kaiyrzhan.de.empath.features.posts.ui.postDetail.components

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
import kaiyrzhan.de.empath.features.posts.ui.model.SubPostUi

@Composable
internal fun SubPost(
    modifier: Modifier = Modifier,
    subPost: SubPostUi,
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
            if (subPost.title.isNotBlank()) {
                Text(
                    text = subPost.title,
                    style = EmpathTheme.typography.headlineLarge,
                )
                HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
            }
            if (subPost.description.isNotBlank()) {
                Text(
                    text = subPost.description,
                    style = EmpathTheme.typography.titleSmall,
                )
            }
            PostImages(
                modifier = Modifier.fillMaxWidth(),
                imageUrls = subPost.imageUrls,
            )
        }
    }
}