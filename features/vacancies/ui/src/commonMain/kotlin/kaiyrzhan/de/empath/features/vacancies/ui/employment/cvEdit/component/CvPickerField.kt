package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_work_outlined
import empath.core.uikit.generated.resources.select_cv
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CvPickerField(
    modifier: Modifier = Modifier,
    selected: String,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    PickerField(
        modifier = modifier,
        title = stringResource(Res.string.select_cv),
        selected = selected,
        onClick = onClick,
        leadingPainter = painterResource(Res.drawable.ic_work_outlined),
    )
}