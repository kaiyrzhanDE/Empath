package kaiyrzhan.de.empath.features.auth.ui.password_recovery

import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryAction
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryEvent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

internal class FakePasswordRecoveryComponent : PasswordRecoveryComponent {
    override val state = MutableStateFlow(
        PasswordRecoveryState.Success(
            email = "sansyzbaev.de@gmail.com",
            password = "12345",
            repeatedPassword = "12345",
        )
    )

    override val action: Flow<PasswordRecoveryAction> = emptyFlow()

    override fun onEvent(event: PasswordRecoveryEvent) = Unit
}