package kaiyrzhan.de.empath.features.profile.ui.profile_edit.model

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.features.profile.ui.model.UserUi

internal sealed class ProfileEditState {
    object Initial : ProfileEditState()
    object Loading : ProfileEditState()
    class Error(val message: String) : ProfileEditState()
    data class Success(
        val originalUser: UserUi,
        val editableUser: UserUi = originalUser,
        val selectedImage: PlatformFile? = null,
        val isImageLoading: Boolean = false,
    ) : ProfileEditState(){
        fun isUserChanged(): Boolean {
            return originalUser != editableUser || selectedImage != null
        }
    }

    companion object {
        fun default(): ProfileEditState {
            return Initial
        }
    }
}