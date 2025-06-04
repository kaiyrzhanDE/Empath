package kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.vacancies.ui.components.WorkingSkillCard
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.component.WorkExperienceCard
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.component.TopBar
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model.CvDetailEvent
import kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model.CvDetailState
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.components.WorkingCondition
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.components.WorkingConditions
import kaiyrzhan.de.empath.features.vacancies.ui.model.getSelected
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CvDetailScreen(
    modifier: Modifier = Modifier,
    component: CvDetailComponent,
) {
    val state = component.state.collectAsState()

    CvDetailScreen(
        modifier = modifier,
        state = state.value,
        onEvent = component::onEvent,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CvDetailScreen(
    modifier: Modifier = Modifier,
    state: CvDetailState,
    onEvent: (CvDetailEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { onEvent(CvDetailEvent.BackClick) },
            )
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) { contentPadding ->
        when (state) {
            is CvDetailState.Success -> {
                val education = state.cv.educations.firstOrNull { it.isSelected }?.type

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
                        text = state.cv.title,
                        style = EmpathTheme.typography.headlineMedium,
                        color = EmpathTheme.colors.onSurface,
                    )

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
                                    append(state.cv.email)
                                }
                            },
                            style = EmpathTheme.typography.bodyMedium,
                            color = EmpathTheme.colors.onSurfaceVariant,
                        )
                    }

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(Res.string.salary))
                            appendColon()
                            appendSpace()
                            append(stringResource(Res.string.from))
                            appendSpace()
                            append(state.cv.salary.from.toGroupedString())
                            appendSpace()
                            append(stringResource(Res.string.kzt))
                            appendSpace()
                            append(stringResource(Res.string.to))
                            appendSpace()
                            append(state.cv.salary.to.toGroupedString())
                            appendSpace()
                            append(stringResource(Res.string.kzt))
                        },
                        style = EmpathTheme.typography.bodyLarge,
                        color = EmpathTheme.colors.onSurface,
                    )

                    WorkingConditions(
                        title = stringResource(Res.string.employment_types),
                        painter = painterResource(Res.drawable.ic_schedule),
                        skills = state.cv.selectedEmploymentTypes,
                    )
                    WorkingConditions(
                        title = stringResource(Res.string.work_schedules),
                        painter = painterResource(Res.drawable.ic_calendar_today),
                        skills = state.cv.selectedWorkSchedules,
                    )
                    WorkingConditions(
                        title = stringResource(Res.string.work_formats),
                        painter = painterResource(Res.drawable.ic_domain),
                        skills = state.cv.selectedWorkFormats,
                    )
                    WorkingCondition(
                        title = stringResource(Res.string.address),
                        painter = painterResource(Res.drawable.ic_distance),
                        skill = state.cv.address,
                    )
                    if (education?.res != null) {
                        WorkingCondition(
                            title = stringResource(Res.string.education),
                            painter = painterResource(Res.drawable.ic_school),
                            skill = stringResource(education.res),
                        )
                    }

                    HorizontalDivider(color = EmpathTheme.colors.outlineVariant)

                    state.cv.workExperiences
                        .forEachIndexed { index, workExperience ->
                            WorkExperienceCard(
                                modifier = Modifier.fillMaxWidth(),
                                position = index,
                                workExperience = workExperience,
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
                            state.cv.skills.forEach { skill ->
                                WorkingSkillCard(
                                    skill = skill.name,
                                    containerColor = EmpathTheme.colors.primaryContainer,
                                )
                            }
                        }
                    }

                    if (state.cv.additionalSkills.isNotEmpty()) {
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
                                state.cv.additionalSkills.forEach { skill ->
                                    WorkingSkillCard(
                                        skill = skill.name,
                                        containerColor = EmpathTheme.colors.secondaryContainer,
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            is CvDetailState.Error -> {
                ErrorScreen(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                    message = state.message,
                    onTryAgainClick = { onEvent(CvDetailEvent.LoadCv) },
                )
            }

            is CvDetailState.Loading -> {
                CircularLoadingScreen(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                )
            }

            is CvDetailState.Initial -> Unit
        }

    }
}