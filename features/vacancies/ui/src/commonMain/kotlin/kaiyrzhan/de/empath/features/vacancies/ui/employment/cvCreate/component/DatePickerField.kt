package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvCreate.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empath.core.uikit.generated.resources.*
import empath.core.uikit.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DatePickerField(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    onClick: () -> Unit,
) {
    PickerField(
        modifier = modifier,
        title = title,
        selected = date,
        onClick = onClick,
        leadingPainter = painterResource(Res.drawable.ic_calendar_today),
    )
}