package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.core.utils.dateFormat
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.vacancies.ui.components.WorkingSkillCard
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.components.TopBar
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.components.WorkingCondition
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.components.WorkingConditions
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailAction
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailEvent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun VacancyDetailScreen(
    component: VacancyDetailComponent,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    val state = component.state.collectAsState()

    SingleEventEffect(component.action) { action ->
        when (action) {
            is VacancyDetailAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }

    VacancyDetailScreen(
        modifier = modifier,
        state = state.value,
        onEvent = component::onEvent,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun VacancyDetailScreen(
    modifier: Modifier = Modifier,
    state: VacancyDetailState,
    onEvent: (VacancyDetailEvent) -> Unit,
) {

    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                onEvent = onEvent,
            )
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) { contentPadding ->
        when (state) {
            is VacancyDetailState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .screenHorizontalPadding(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.vacancyDetail.title,
                        style = EmpathTheme.typography.headlineMedium,
                        color = EmpathTheme.colors.onSurface,
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(Res.string.salary))
                            appendColon()
                            appendSpace()
                            append(stringResource(Res.string.from))
                            appendSpace()
                            append(state.vacancyDetail.salary.from.toGroupedString())
                            appendSpace()
                            append(stringResource(Res.string.kzt))
                            appendSpace()
                            append(stringResource(Res.string.to))
                            appendSpace()
                            append(state.vacancyDetail.salary.to.toGroupedString())
                            appendSpace()
                            append(stringResource(Res.string.kzt))
                        },
                        style = EmpathTheme.typography.bodyLarge,
                        color = EmpathTheme.colors.onSurface,
                    )

                    WorkingCondition(
                        title = stringResource(Res.string.work_experience),
                        painter = painterResource(Res.drawable.ic_work_history),
                        skill = state.vacancyDetail.workExperience,
                    )
                    WorkingConditions(
                        title = stringResource(Res.string.employment_types),
                        painter = painterResource(Res.drawable.ic_schedule),
                        skills = state.vacancyDetail.employmentTypes,
                    )
                    WorkingConditions(
                        title = stringResource(Res.string.work_schedules),
                        painter = painterResource(Res.drawable.ic_calendar_today),
                        skills = state.vacancyDetail.workSchedules,
                    )
                    WorkingConditions(
                        title = stringResource(Res.string.work_formats),
                        painter = painterResource(Res.drawable.ic_domain),
                        skills = state.vacancyDetail.workFormats,
                    )
                    WorkingCondition(
                        title = stringResource(Res.string.address),
                        painter = painterResource(Res.drawable.ic_distance),
                        skill = state.vacancyDetail.address,
                    )
                    WorkingCondition(
                        title = stringResource(Res.string.education),
                        painter = painterResource(Res.drawable.ic_school),
                        skill = state.vacancyDetail.education,
                    )

                    HorizontalDivider(color = EmpathTheme.colors.outlineVariant)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = EmpathTheme.shapes.small,
                        colors = CardDefaults.cardColors(
                            containerColor = EmpathTheme.colors.surfaceContainer,
                            contentColor = EmpathTheme.colors.onSurfaceVariant,
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = state.vacancyDetail.author.companyName,
                            style = EmpathTheme.typography.headlineLarge,
                            color = EmpathTheme.colors.onSurface,
                        )
                    }
                    Text(
                        text = state.vacancyDetail.author.companyDescription,
                        style = EmpathTheme.typography.titleSmall,
                        color = EmpathTheme.colors.onSurface,
                    )

                    HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                    VacancyInfo(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(Res.string.responsibilities),
                        description = state.vacancyDetail.responsibilities,
                    )
                    HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                    VacancyInfo(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(Res.string.requirements),
                        description = state.vacancyDetail.requirements,
                    )
                    if (state.vacancyDetail.additionalDescription.isNotEmpty()) {
                        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
                        VacancyInfo(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(Res.string.additional_description),
                            description = state.vacancyDetail.additionalDescription,
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            style = EmpathTheme.typography.labelLarge,
                            text = buildAnnotatedString {
                                append(stringResource(Res.string.key_skills))
                                appendColon()
                            }
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            state.vacancyDetail.skills.forEach { skill ->
                                WorkingSkillCard(
                                    skill = skill.name,
                                    containerColor = EmpathTheme.colors.primaryContainer,
                                )
                            }
                        }
                    }

                    if (state.vacancyDetail.additionalSkills.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                style = EmpathTheme.typography.labelLarge,
                                text = buildAnnotatedString {
                                    append(stringResource(Res.string.additional_skills))
                                    appendColon()
                                },
                            )
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                state.vacancyDetail.additionalSkills.forEach { skill ->
                                    WorkingSkillCard(
                                        skill = skill.name,
                                        containerColor = EmpathTheme.colors.secondaryContainer,
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(Res.string.contacts))
                                appendColon()
                            },
                            style = EmpathTheme.typography.labelLarge,
                        )
                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(Res.string.email))
                                appendColon()
                                appendSpace()
                                withStyle(
                                    style = EmpathTheme.typography.bodyLarge
                                        .toSpanStyle()
                                        .copy(color = EmpathTheme.colors.onSurface),
                                ) {
                                    append(state.vacancyDetail.email)
                                }
                            },
                            style = EmpathTheme.typography.bodyMedium,
                            color = EmpathTheme.colors.onSurfaceVariant,
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = state.vacancyDetail.dateOfCreated.dateFormat(),
                            style = EmpathTheme.typography.bodyMedium,
                            color = EmpathTheme.colors.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Button(
                                onClick = { onEvent(VacancyDetailEvent.VacancyDeleteClick) },
                                shape = EmpathTheme.shapes.small,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = EmpathTheme.colors.surfaceContainer,
                                    contentColor = EmpathTheme.colors.onSurface,
                                ),
                            ) {
                                Text(
                                    text = stringResource(Res.string.delete),
                                    style = EmpathTheme.typography.labelLarge,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }

                            Button(
                                onClick = { onEvent(VacancyDetailEvent.VacancyEditClick) },
                                shape = EmpathTheme.shapes.small,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = EmpathTheme.colors.primary,
                                    contentColor = EmpathTheme.colors.onPrimary,
                                ),
                            ) {
                                Text(
                                    text = stringResource(Res.string.edit),
                                    style = EmpathTheme.typography.labelLarge,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            is VacancyDetailState.Error -> {
                ErrorScreen(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                    message = state.message,
                    onTryAgainClick = { onEvent(VacancyDetailEvent.ReloadVacancyDetail) },
                )
            }

            is VacancyDetailState.Loading -> {
                CircularLoadingScreen(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                )
            }

            is VacancyDetailState.Initial -> Unit
        }

    }
}

@Composable
private fun VacancyInfo(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = title,
            style = EmpathTheme.typography.titleLarge,
            color = EmpathTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = description,
            style = EmpathTheme.typography.titleSmall,
            color = EmpathTheme.colors.onSurface,
        )
    }
}


