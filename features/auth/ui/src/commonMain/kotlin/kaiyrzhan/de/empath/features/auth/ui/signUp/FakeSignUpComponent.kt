package kaiyrzhan.de.empath.features.auth.ui.signUp

import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpAction
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpEvent
import kaiyrzhan.de.empath.features.auth.ui.signUp.model.SignUpState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

internal class FakeSignUpComponent : SignUpComponent {
    override val state = MutableStateFlow(
        SignUpState.Success(
            email = "sansyzbaev.de@gmail.com",
            password = "12345",
            repeatedPassword = "12345",
            isUserAgreementAccepted = true,
        )
    )

    override val action: Flow<SignUpAction> = emptyFlow()

    override fun onEvent(event: SignUpEvent) = Unit
}