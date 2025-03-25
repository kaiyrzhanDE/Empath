package kaiyrzhan.de.empath.features.profile.ui.profile_edit.model

import kaiyrzhan.de.empath.features.profile.ui.model.UserUi

internal sealed class ProfileEditState {
    object Initial : ProfileEditState()
    object Loading : ProfileEditState()
    class Error(val message: String) : ProfileEditState()
    data class Success(
        val originalUser: UserUi,
        val editableUser: UserUi = originalUser,
        val isImageLoading: Boolean = false,
    ) : ProfileEditState(){
        fun isUserChanged(): Boolean {
            return originalUser != editableUser
        }
    }

    companion object {
        fun default(): ProfileEditState {
            return Initial
        }
    }
}