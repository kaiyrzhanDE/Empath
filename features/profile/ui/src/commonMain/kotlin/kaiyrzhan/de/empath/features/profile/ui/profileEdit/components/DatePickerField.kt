package kaiyrzhan.de.empath.features.profile.ui.profileEdit.components

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
    dateOfBirth: String,
    onClick: () -> Unit,
) {
    PickerField(
        modifier = Modifier.fillMaxWidth(),
        title = stringResource(Res.string.select_date_of_birth),
        selected = dateOfBirth,
        onClick = onClick,
        leadingPainter = painterResource(Res.drawable.ic_calendar_today),
        trailingPainter = painterResource(Res.drawable.ic_arrow_forward),
    )
}