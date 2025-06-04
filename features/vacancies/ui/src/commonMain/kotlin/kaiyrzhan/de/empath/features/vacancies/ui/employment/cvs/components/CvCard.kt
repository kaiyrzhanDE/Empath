package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CvCard(
    modifier: Modifier = Modifier,
    isIndicator: Boolean,
    cv: CvUi,
    onEvent: (CvsEvent) -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier.noRippleClickable {
            if (isIndicator) {
                onEvent(CvsEvent.CvDetailClick(cv))
            } else {
                onEvent(CvsEvent.CvSelect(cv))
            }
        },
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = cv.title,
                    style = EmpathTheme.typography.headlineMedium,
                    color = EmpathTheme.colors.onSurface,
                )
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(
                        onClick = { isExpanded = true }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_more_vert),
                            contentDescription = "CV more options",
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
                                onEvent(CvsEvent.CvEditClick(cv.id))
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
                                onEvent(CvsEvent.CvDeleteClick(cv.id))
                                isExpanded = false
                            }
                        )
                    }
                }
            }
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
            if(isIndicator) {
                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { onEvent(CvsEvent.CvDetailClick(cv)) },
                    shape = EmpathTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmpathTheme.colors.primary,
                        contentColor = EmpathTheme.colors.onPrimary,
                    ),
                ) {
                    Text(
                        text = stringResource(Res.string.cv_detail),
                        style = EmpathTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

