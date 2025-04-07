package kaiyrzhan.de.empath.features.articles.ui.article_create.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_add_photo_alternate_placeholder
import empath.core.uikit.generated.resources.selected_images_placeholder
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ArticleImagePlaceholder(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(shape = EmpathTheme.shapes.small)
            .border(
                width = 1.dp,
                color = EmpathTheme.colors.outlineVariant,
                shape = EmpathTheme.shapes.small,
            )
            .padding(vertical = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            painter = painterResource(Res.drawable.ic_add_photo_alternate_placeholder),
            contentDescription = null,
            tint = EmpathTheme.colors.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(Res.string.selected_images_placeholder),
            style = EmpathTheme.typography.bodySmall,
            color = EmpathTheme.colors.onSurfaceVariant,
        )
    }
}