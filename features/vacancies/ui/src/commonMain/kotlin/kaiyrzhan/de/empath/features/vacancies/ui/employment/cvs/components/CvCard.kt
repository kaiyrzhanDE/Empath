package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.modifiers.noRippleClickable
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.vacancies.ui.components.WorkingSkillCard
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model.CvsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.CvUi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CvCard(
    modifier: Modifier = Modifier,
    cv: CvUi,
    onEvent: (CvsEvent) -> Unit,
) {
    Card(
        modifier = modifier
            .noRippleClickable { onEvent(CvsEvent.CvSelect(cv)) },
        shape = EmpathTheme.shapes.small,
        border = BorderStroke(
            width = 1.dp,
            color = if (cv.isSelected) EmpathTheme.colors.primary
            else EmpathTheme.colors.outlineVariant,
        ),
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surfaceContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = cv.title,
                style = EmpathTheme.typography.headlineMedium,
                color = EmpathTheme.colors.onSurface,
            )
            Text(
                text = buildString {
                    append(stringResource(Res.string.from))
                    appendSpace()
                    append(cv.salary.from.toGroupedString())
                    appendSpace()
                    append(stringResource(Res.string.kzt))
                    appendSpace()
                    append(stringResource(Res.string.to))
                    appendSpace()
                    append(cv.salary.to.toGroupedString())
                    appendSpace()
                    append(stringResource(Res.string.kzt))
                },
                style = EmpathTheme.typography.labelLarge,
                color = EmpathTheme.colors.onSurface,
            )

            Text(
                text = buildAnnotatedString {
                    append(stringResource(Res.string.about_me))
                    appendColon()
                    appendSpace()
                    withStyle(
                        style = SpanStyle(color = EmpathTheme.colors.onSurface),
                    ) {
                        append(cv.aboutMe)
                    }
                },
                style = EmpathTheme.typography.labelMedium,
                color = EmpathTheme.colors.onSurfaceVariant,
                maxLines = 6,
            )
            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
            if (cv.hasSkills()) {
                FlowRow(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    cv.skills.forEach { skill ->
                        WorkingSkillCard(
                            skill = skill,
                            containerColor = EmpathTheme.colors.primaryContainer,
                        )
                    }
                    cv.additionalSkills.forEach { skill ->
                        WorkingSkillCard(
                            skill = skill,
                            containerColor = EmpathTheme.colors.secondaryContainer,
                        )
                    }
                }
            }
        }

    }
}

