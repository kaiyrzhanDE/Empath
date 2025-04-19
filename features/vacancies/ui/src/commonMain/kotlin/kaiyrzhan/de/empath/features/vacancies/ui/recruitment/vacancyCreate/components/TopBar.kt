package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.components

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
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.model.VacancyCreateEvent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    onEvent: (VacancyCreateEvent) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(Res.string.create_vacancy),
                    style = EmpathTheme.typography.titleMedium,
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { onEvent(VacancyCreateEvent.BackClick) },
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_back),
                        contentDescription = stringResource(Res.string.ic_arrow_back_description),
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = EmpathTheme.colors.surface,
                navigationIconContentColor = EmpathTheme.colors.onSurface,
                titleContentColor = EmpathTheme.colors.onSurface,
            )
        )
        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
    }
}