package kaiyrzhan.de.empath.features.profile.ui.profileEdit.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.profile.ui.profileEdit.model.ProfileEditEvent
import kaiyrzhan.de.empath.features.profile.ui.profileEdit.model.ProfileEditState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProfileEditActions(
    modifier: Modifier = Modifier,
    state: ProfileEditState.Success,
    onEvent: (ProfileEditEvent) -> Unit,
) {
    val isUserChanged = state.isUserChanged()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        if (isUserChanged) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .animateContentSize(),
                onClick = { onEvent(ProfileEditEvent.Cancel) },
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.surfaceContainer,
                    contentColor = EmpathTheme.colors.onSurface,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.cancel),
                    style = EmpathTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Button(
            modifier = Modifier.weight(1f),
            onClick = { onEvent(ProfileEditEvent.Save) },
            shape = EmpathTheme.shapes.small,
            enabled = isUserChanged,
            colors = ButtonDefaults.buttonColors(
                containerColor = EmpathTheme.colors.primary,
                contentColor = EmpathTheme.colors.onPrimary,
            ),
        ) {
            Text(
                text = stringResource(Res.string.save),
                style = EmpathTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}