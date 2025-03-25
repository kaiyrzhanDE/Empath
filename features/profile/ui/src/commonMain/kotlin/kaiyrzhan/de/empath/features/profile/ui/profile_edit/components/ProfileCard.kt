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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.core.uikit.generated.resources.ic_account_circle
import empath.core.uikit.generated.resources.ic_error_filled
import kaiyrzhan.de.empath.core.ui.modifiers.shimmerLoading
import kaiyrzhan.de.empath.core.ui.modifiers.thenIf
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditEvent
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditState
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ProfileCard(
    modifier: Modifier = Modifier,
    imageSize: Dp,
    state: ProfileEditState.Success,
    onEvent: (ProfileEditEvent) -> Unit,
) {
    val image = state.editableUser.image
    val userImagePainter = rememberAsyncImagePainter(
        model = image,
        placeholder = painterResource(CoreRes.drawable.ic_account_circle),
        error = painterResource(
            resource = if (image.isNotBlank()) CoreRes.drawable.ic_error_filled
            else CoreRes.drawable.ic_account_circle,
        ),
    )
    val imageState = userImagePainter.state.collectAsState()

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
                    Modifier.clickable { userImagePainter.restart() }
                }
                .thenIf(imageState.value is AsyncImagePainter.State.Success) {
                    Modifier.border(
                        width = 2.dp,
                        color = EmpathTheme.colors.onSurfaceVariant,
                        shape = EmpathTheme.shapes.full,
                    )
                },
            painter = userImagePainter,
            contentScale = ContentScale.Crop,
            contentDescription = "User Image",
        )
        ImagePickerField(
            modifier = Modifier.fillMaxWidth(),
            imageUrl = image,
            onClick = {
                TODO()
//                onEvent(ProfileEditEvent.PhotoSelect())
            },
            isLoading = state.isImageLoading,
        )
    }
}