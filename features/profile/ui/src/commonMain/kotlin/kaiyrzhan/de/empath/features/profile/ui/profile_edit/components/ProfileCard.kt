package kaiyrzhan.de.empath.features.profile.ui.profile_edit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.files.ifEmpty
import empath.core.uikit.generated.resources.Res as CoreRes
import kaiyrzhan.de.empath.core.ui.files.toString
import kaiyrzhan.de.empath.core.ui.files.rememberImagePainter
import kaiyrzhan.de.empath.core.ui.files.rememberImagePicker
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditEvent
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProfileCard(
    modifier: Modifier = Modifier,
    imageSize: Dp,
    state: ProfileEditState.Success,
    onEvent: (ProfileEditEvent) -> Unit,
) {
    val singleFilePicker = rememberImagePicker(
        title = stringResource(CoreRes.string.select_photo),
    ) { selectedFile ->
        onEvent(ProfileEditEvent.PhotoSelect(selectedFile))
    }

    val imagePainter = rememberImagePainter(
        model = state.selectedImage.ifEmpty { state.editableUser.image },
        error = painterResource(CoreRes.drawable.ic_error_filled),
        fallback = painterResource(CoreRes.drawable.ic_account_circle),
        placeholder = painterResource(CoreRes.drawable.ic_account_circle),
        filterQuality = FilterQuality.High,
    )

    val imageState = imagePainter.state.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .clip(EmpathTheme.shapes.full)
                .size(imageSize)
                .thenIf(imageState.value is AsyncImagePainter.State.Loading) {
                    Modifier.shimmerLoading()
                }
                .thenIf(imageState.value is AsyncImagePainter.State.Error) {
                    Modifier.clickable { imagePainter.restart() }
                }
                .thenIf(imageState.value is AsyncImagePainter.State.Success) {
                    Modifier.border(
                        width = 2.dp,
                        color = EmpathTheme.colors.onSurfaceVariant,
                        shape = EmpathTheme.shapes.full,
                    )
                },
            painter = imagePainter,
            contentDescription = stringResource(CoreRes.string.user_image),
            contentScale = ContentScale.Crop,
        )
        ImagePickerField(
            modifier = Modifier.fillMaxWidth(),
            selected = state.selectedImage.toString(),
            onClick = { singleFilePicker.launch() },
            isLoading = state.isImageLoading,
        )
    }
}
