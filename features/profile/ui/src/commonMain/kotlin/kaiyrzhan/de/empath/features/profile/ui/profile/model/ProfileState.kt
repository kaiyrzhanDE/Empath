package kaiyrzhan.de.empath.features.profile.ui.profile.model

import kaiyrzhan.de.empath.features.profile.ui.model.UserUi

internal sealed class ProfileState {
    object Loading : ProfileState()
    object Initial : ProfileState()
    class Error(val message: String) : ProfileState()
    data class Success(
        val user: UserUi,
    ) : ProfileState()


    companion object{
        fun default(): ProfileState = Initial
    }
}