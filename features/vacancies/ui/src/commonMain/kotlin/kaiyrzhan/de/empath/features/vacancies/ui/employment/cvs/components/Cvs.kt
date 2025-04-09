package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.animations.CollapseAnimatedVisibility
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model.CvsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.CvUi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun ColumnScope.Cvs(
    modifier: Modifier = Modifier,
    cvs: List<CvUi>,
    onEvent: (CvsEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val rotateAnimation by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        label = "RotateArrow"
    )

    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        border = BorderStroke(1.dp, EmpathTheme.colors.outlineVariant),
        onClick = { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surfaceContainer,
            contentColor = EmpathTheme.colors.onSurfaceVariant,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            IconButton(onClick = { isExpanded = isExpanded.not() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_picture_as_pdf),
                    contentDescription = null,
                    tint = EmpathTheme.colors.onSurfaceVariant,
                )
            }

            Text(
                text = stringResource(Res.string.select_cv),
                style = EmpathTheme.typography.bodyLarge,
                color = EmpathTheme.colors.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotateAnimation),
                    painter = painterResource(Res.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = EmpathTheme.colors.onSurfaceVariant,
                )
            }
        }
    }

    CollapseAnimatedVisibility(visible = isExpanded) {
        Card(
            modifier = Modifier
                .heightIn(min = 300.dp, max = 600.dp)
                .fillMaxSize(),
            shape = EmpathTheme.shapes.small,
            border = BorderStroke(1.dp, EmpathTheme.colors.outline),
            colors = CardDefaults.cardColors(
                containerColor = EmpathTheme.colors.surface,
                contentColor = EmpathTheme.colors.onSurfaceVariant,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                cvs.forEach { cv ->
                    CvCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        cv = cv,
                        onEvent = onEvent,
                    )
                }
            }
        }
    }
}
