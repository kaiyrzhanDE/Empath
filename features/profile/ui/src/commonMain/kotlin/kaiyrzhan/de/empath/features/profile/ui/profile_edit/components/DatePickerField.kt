package kaiyrzhan.de.empath.features.profile.ui.profile_edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empath.core.uikit.generated.resources.ic_arrow_forward
import empath.core.uikit.generated.resources.ic_calendar_today
import empath.features.profile.ui.generated.resources.Res
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.features.profile.ui.generated.resources.select_date_of_birth
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DatePickerField(
    modifier: Modifier = Modifier,
    dateOfBirth: String,
    onClick: () -> Unit,
) {
    PickerField(
        modifier = Modifier.fillMaxWidth(),
        title = stringResource(Res.string.select_date_of_birth),
        selected = dateOfBirth,
        onClick = onClick,
        leadingPainter = painterResource(CoreRes.drawable.ic_calendar_today),
        trailingPainter = painterResource(CoreRes.drawable.ic_arrow_forward),
    )
}