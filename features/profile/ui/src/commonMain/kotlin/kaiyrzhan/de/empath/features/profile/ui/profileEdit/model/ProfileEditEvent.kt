package kaiyrzhan.de.empath.features.profile.ui.profileEdit.model

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.features.profile.domain.model.User

internal sealed interface ProfileEditEvent {
    data class NicknameChange(val nickname: String) : ProfileEditEvent
    data class NameChange(val name: String) : ProfileEditEvent
    data class LastnameChange(val lastname: String) : ProfileEditEvent
    data class PatronymicChange(val patronymic: String) : ProfileEditEvent
    data class GenderSelect(val gender: User.Gender) : ProfileEditEvent
    data class PhotoSelect(val image: PlatformFile) : ProfileEditEvent
    data object DatePickerShow : ProfileEditEvent
    data object LoadProfile : ProfileEditEvent
    data object BackClick : ProfileEditEvent
    data object Save : ProfileEditEvent
    data object Cancel : ProfileEditEvent
}