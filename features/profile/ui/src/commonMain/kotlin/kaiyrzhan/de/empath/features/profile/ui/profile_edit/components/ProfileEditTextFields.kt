package kaiyrzhan.de.empath.features.profile.ui.profile_edit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.utils.dateFormat
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditEvent
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProfileEditTextFields(
    modifier: Modifier = Modifier,
    state: ProfileEditState.Success,
    onEvent: (ProfileEditEvent) -> Unit,

    ) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.editableUser.nickname,
            onValueChange = { nickname ->
                onEvent(ProfileEditEvent.NicknameChange(nickname))
            },
            label = {
                Text(
                    text = stringResource(Res.string.nickname),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            maxLines = 1,
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.editableUser.name,
            onValueChange = { name ->
                onEvent(ProfileEditEvent.NameChange(name))
            },
            label = {
                Text(
                    text = stringResource(Res.string.name),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            maxLines = 1,
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.editableUser.lastname,
            onValueChange = { lastname ->
                onEvent(ProfileEditEvent.LastnameChange(lastname))
            },
            label = {
                Text(
                    text = stringResource(Res.string.lastname),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            maxLines = 1,
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.editableUser.patronymic,
            onValueChange = { patronymic ->
                onEvent(ProfileEditEvent.PatronymicChange(patronymic))
            },
            label = {
                Text(
                    text = stringResource(Res.string.patronymic),
                    style = EmpathTheme.typography.bodyLarge,
                )
            },
            maxLines = 1,
        )

        DatePickerField(
            modifier = Modifier.fillMaxWidth(),
            dateOfBirth = state.editableUser.dateOfBirth.dateFormat(),
            onClick = {
                onEvent(ProfileEditEvent.DatePickerShow)
            },
        )

        GenderSelectorField(
            modifier = Modifier.fillMaxWidth(),
            gender = state.editableUser.gender.toString(),
            onGenderSelect = { gender ->
                onEvent(ProfileEditEvent.GenderSelect(gender))
            },
        )
    }
}