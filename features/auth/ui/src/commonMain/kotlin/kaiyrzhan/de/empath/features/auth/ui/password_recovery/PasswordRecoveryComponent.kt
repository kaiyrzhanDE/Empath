package kaiyrzhan.de.empath.features.auth.ui.password_recovery

import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryAction
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryEvent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.model.PasswordRecoveryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

public interface PasswordRecoveryComponent {

    public val state: StateFlow<PasswordRecoveryState>

    public val action: Flow<PasswordRecoveryAction>

    public fun onEvent(event: PasswordRecoveryEvent)

}