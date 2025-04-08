package kaiyrzhan.de.empath.features.vacancies.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

@Composable
internal fun WorkingConditionCard(
    modifier: Modifier = Modifier,
    skills: List<String>,
    painter: Painter,
) {
    val text = skills.joinToString(", ")
    WorkingConditionCard(
        modifier = modifier,
        skill = text,
        painter = painter,
    )
}

@Composable
internal fun WorkingConditionCard(
    modifier: Modifier = Modifier,
    skill: String,
    painter: Painter,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            contentColor = EmpathTheme.colors.onSurface,
            containerColor = EmpathTheme.colors.surfaceContainer,
        )
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painter,
                modifier = Modifier.size(24.dp),
                contentDescription = skill,
                tint = EmpathTheme.colors.onSurfaceVariant,
            )
            Text(
                text = skill,
                style = EmpathTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
internal fun WorkingSkillCard(
    modifier: Modifier = Modifier,
    skill: String,
    containerColor: Color,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            contentColor = EmpathTheme.colors.onSurface,
            containerColor = containerColor,
        )
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = skill,
            style = EmpathTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
