package kaiyrzhan.de.empath.features.profile.ui.profile_edit.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ImagePickerField(
    modifier: Modifier = Modifier,
    selected: String,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    PickerField(
        modifier = modifier,
        title = stringResource(Res.string.select_photo),
        selected = selected,
        onClick = onClick,
        isLoading = isLoading,
        leadingPainter = painterResource(Res.drawable.ic_add_a_photo),
    )
}