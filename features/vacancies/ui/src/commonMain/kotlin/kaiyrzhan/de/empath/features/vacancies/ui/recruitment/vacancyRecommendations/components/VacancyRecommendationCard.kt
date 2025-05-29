package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendPercent
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.vacancies.ui.components.WorkingSkillCard
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.components.WorkingCondition
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.CvUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.model.VacancyRecommendationsEvent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CvCard(
    modifier: Modifier = Modifier,
    cv: CvUi,
    onEvent: (VacancyRecommendationsEvent) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
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

            WorkingCondition(
                title = stringResource(Res.string.email),
                skill = cv.author.name,
                painter = painterResource(Res.drawable.ic_alternate_email),
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
            Text(
                text = buildString {
                    append(stringResource(Res.string.accordance))
                    appendColon()
                },
                style = EmpathTheme.typography.labelLarge,
                color = EmpathTheme.colors.onSurface,
            )
            ProgressIndicator(cv.weight)
            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
            if (cv.hasSkills()) {
                FlowRow(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    cv.skills.forEach { skill ->
                        WorkingSkillCard(
                            skill = buildString {
                                append(skill.name)
                                appendSpace()
                                append(skill.weight.format())
                                appendPercent()
                            },
                            containerColor = EmpathTheme.colors.primaryContainer,
                        )
                    }
                    cv.additionalSkills.forEach { skill ->
                        WorkingSkillCard(
                            skill = buildString {
                                append(skill.name)
                                appendSpace()
                                append(skill.weight.format())
                                appendPercent()
                            },
                            containerColor = EmpathTheme.colors.secondaryContainer,
                        )
                    }
                }
            }

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = { onEvent(VacancyRecommendationsEvent.ContactEmailClick(cv.author.name)) },
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.primary,
                    contentColor = EmpathTheme.colors.onPrimary,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.contact_email),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

