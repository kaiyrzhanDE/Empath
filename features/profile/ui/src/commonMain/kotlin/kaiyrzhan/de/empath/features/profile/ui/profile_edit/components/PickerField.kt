package kaiyrzhan.de.empath.features.profile.ui.profile_edit.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.animateTextAsState
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PickerField(
    modifier: Modifier = Modifier,
    title: String,
    selected: String,
    onClick: () -> Unit,
    leadingPainter: Painter,
    isLoading: Boolean = false,
    trailingPainter: Painter? = null,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.extraSmall,
        border = BorderStroke(width = 1.dp, color = EmpathTheme.colors.outline),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
            contentColor = EmpathTheme.colors.onSurfaceVariant,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (isLoading) {
                LoadingCard(
                    modifier = Modifier.padding(12.dp),
                )
            } else {
                IconButton(
                    onClick = onClick,
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = leadingPainter,
                        contentDescription = title,
                        tint = EmpathTheme.colors.onSurfaceVariant,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                when {
                    isLoading -> {
                        val loadingText =
                            animateTextAsState(text = stringResource(Res.string.loading))
                        Text(
                            text = loadingText.value,
                            style = EmpathTheme.typography.bodySmall,
                            color = EmpathTheme.colors.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    selected.isNotBlank() -> {
                        Text(
                            text = title,
                            style = EmpathTheme.typography.bodySmall,
                            color = EmpathTheme.colors.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = selected,
                            style = EmpathTheme.typography.bodyLarge,
                            color = EmpathTheme.colors.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    else -> {
                        Text(
                            text = title,
                            style = EmpathTheme.typography.bodyLarge,
                            color = EmpathTheme.colors.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }

            if (trailingPainter != null) {
                IconButton(
                    onClick = onClick,
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = trailingPainter,
                        contentDescription = title,
                        tint = EmpathTheme.colors.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingCard(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier.size(24.dp),
        color = EmpathTheme.colors.primary,
        trackColor = EmpathTheme.colors.secondary,
        strokeCap = StrokeCap.Square,
    )
}