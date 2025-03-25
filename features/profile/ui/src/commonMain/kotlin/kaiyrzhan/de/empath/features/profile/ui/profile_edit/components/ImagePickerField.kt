package kaiyrzhan.de.empath.features.profile.ui.profile_edit.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empath.core.uikit.generated.resources.select_photo
import empath.core.uikit.generated.resources.ic_add_a_photo
import empath.core.uikit.generated.resources.Res as CoreRes
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.FieldState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ImagePickerField(
    modifier: Modifier = Modifier,
    imageUrl: String,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    PickerField(
        modifier = modifier,
        title = stringResource(CoreRes.string.select_photo),
        selected = imageUrl,
        onClick = onClick,
        isLoading = isLoading,
        leadingPainter = painterResource(CoreRes.drawable.ic_add_a_photo),
    )
}