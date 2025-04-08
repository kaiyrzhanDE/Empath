package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi

@Composable
internal fun WorkingConditions(
    modifier: Modifier = Modifier,
    title: String,
    painter: Painter,
    skills: List<SkillUi>,
){
    val text = skills.joinToString(", ") { it.name }

    WorkingCondition(
        modifier = modifier,
        title = title,
        painter = painter,
        skill = text,
    )
}

@Composable
internal fun WorkingCondition(
    modifier: Modifier = Modifier,
    title: String,
    painter: Painter,
    skill: String,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surfaceContainer,
            contentColor = EmpathTheme.colors.onSurfaceVariant,
        )
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painter,
                contentDescription = skill,
            )
            Text(
                buildAnnotatedString {
                    append(title)
                    appendColon()
                    appendSpace()
                    withStyle(
                        style = EmpathTheme.typography.bodyMedium
                            .toSpanStyle()
                            .copy(color = EmpathTheme.colors.onSurface),
                    ) {
                        append(skill)
                    }
                },
                style = EmpathTheme.typography.labelMedium,
            )
        }
    }
}