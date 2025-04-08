package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import empath.core.uikit.generated.resources.accept
import empath.core.uikit.generated.resources.email
import empath.core.uikit.generated.resources.ic_alternate_email
import empath.core.uikit.generated.resources.ic_more_vert
import empath.core.uikit.generated.resources.reject
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseStatus
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesEvent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ResponseCard(
    response: ResponseUi,
    onEvent: (VacanciesEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    Card(
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
            contentColor = EmpathTheme.colors.onSurface,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = response.cvTitle,
                    style = EmpathTheme.typography.headlineMedium,
                )
                ResponseOptions(
                    response = response,
                    onEvent = onEvent,
                )
            }
            Text(
                text = response.authorFullName,
                style = EmpathTheme.typography.labelLarge,
            )

            Card(
                shape = EmpathTheme.shapes.small,
                colors = CardDefaults.cardColors(
                    contentColor = EmpathTheme.colors.onSurface,
                    containerColor = EmpathTheme.colors.surfaceContainer,
                )
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = response.status.toString(),
                    style = EmpathTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Card(
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
                        painter = painterResource(Res.drawable.ic_alternate_email),
                        modifier = Modifier.size(24.dp),
                        contentDescription = response.authorEmail,
                        tint = EmpathTheme.colors.onSurfaceVariant,
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = EmpathTheme.typography.labelMedium
                                    .toSpanStyle()
                                    .copy(color = EmpathTheme.colors.onSurfaceVariant),
                            ) {
                                append(stringResource(Res.string.email))
                                appendColon()
                            }
                            appendSpace()
                            append(response.authorEmail)
                        },
                        style = EmpathTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
            RecruitmentResponseActions(
                modifier = Modifier.fillMaxWidth(),
                response = response,
                onEvent = onEvent,
            )
        }
    }
}

@Composable
private fun ResponseOptions(
    modifier: Modifier = Modifier,
    response: ResponseUi,
    onEvent: (VacanciesEvent) -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Box(modifier = modifier) {
        IconButton(
            onClick = { isExpanded = true }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_more_vert),
                contentDescription = "Response more options",
            )
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            if (response.status != ResponseStatus.ACCEPTED) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(Res.string.accept),
                        )
                    },
                    onClick = {
                        onEvent(VacanciesEvent.ResponseAccept(response))
                        isExpanded = false
                    }
                )
            }
            if (response.status != ResponseStatus.REJECTED) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(Res.string.reject),
                        )
                    },
                    onClick = {
                        onEvent(VacanciesEvent.ResponseReject(response))
                        isExpanded = false
                    }
                )
            }
        }
    }
}

