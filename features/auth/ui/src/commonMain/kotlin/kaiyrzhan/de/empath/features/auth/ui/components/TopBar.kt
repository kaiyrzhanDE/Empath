package kaiyrzhan.de.empath.features.auth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import empath.core.uikit.generated.resources.Res as CoreRes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    description: AnnotatedString,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier.defaultMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(CoreRes.drawable.ic_arrow_back),
                    contentDescription = stringResource(CoreRes.string.ic_arrow_back_description),
                    tint = EmpathTheme.colors.onSurface,
                )
            }
            Text(
                text = title,
                style = EmpathTheme.typography.headlineMedium,
                color = EmpathTheme.colors.onSurface,
            )
        }
        Text(
            text = description,
            style = EmpathTheme.typography.labelLarge,
            color = EmpathTheme.colors.onSurfaceVariant,
        )
    }
}

@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier.defaultMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(CoreRes.drawable.ic_arrow_back),
                    contentDescription = stringResource(CoreRes.string.ic_arrow_back_description),
                    tint = EmpathTheme.colors.onSurface,
                )
            }
            Text(
                text = title,
                style = EmpathTheme.typography.headlineMedium,
                color = EmpathTheme.colors.onSurface,
            )
        }
        Text(
            text = description,
            style = EmpathTheme.typography.labelLarge,
            color = EmpathTheme.colors.onSurfaceVariant,
        )
    }
}

@Preview
@Composable
private fun Preview(){
    TopBar(
        title = "Title",
        description = "Description",
        onBackClick = {},
    )
}