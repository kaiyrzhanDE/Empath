package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailEvent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    state: VacancyDetailState,
    onEvent: (VacancyDetailEvent) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(Res.string.vacancy_detail),
                    style = EmpathTheme.typography.titleMedium,
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { onEvent(VacancyDetailEvent.BackClick) },
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_back),
                        contentDescription = stringResource(Res.string.ic_arrow_back_description),
                    )
                }
            },
            actions = {
                if(state is VacancyDetailState.Success && state.isRecommendations) {
                    IconButton(
                        onClick = { onEvent(VacancyDetailEvent.VacancyRecommendationsClick) },
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_find_in_page),
                            contentDescription = null,
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = EmpathTheme.colors.surface,
                navigationIconContentColor = EmpathTheme.colors.onSurface,
                titleContentColor = EmpathTheme.colors.onSurface,
            ),
        )
        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
    }
}