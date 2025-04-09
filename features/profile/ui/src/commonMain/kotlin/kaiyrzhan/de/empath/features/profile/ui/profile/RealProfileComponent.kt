package kaiyrzhan.de.empath.features.profile.ui.profile

import com.arkivanov.decompose.ComponentContext
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.profile.domain.usecase.GetUserUseCase
import kaiyrzhan.de.empath.features.profile.ui.model.toUi
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileAction
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileEvent
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.inject

internal class RealProfileComponent(
    componentContext: ComponentContext,
    private val onUserPageClick: () -> Unit,
    private val onProfileEditClick: () -> Unit,
    private val onLogOutClick: () -> Unit,
) : BaseComponent(componentContext), ProfileComponent {

    private val getUserUseCase: GetUserUseCase by inject()

    override val state = MutableStateFlow<ProfileState>(
        ProfileState.default()
    )

    private val _action = Channel<ProfileAction>(capacity = Channel.BUFFERED)
    override val action: Flow<ProfileAction> = _action.receiveAsFlow()

    init {
        loadProfile()
    }

    override fun onEvent(event: ProfileEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is ProfileEvent.LogOut -> onLogOutClick()
            is ProfileEvent.UserPageClick -> clickUserPage()
            is ProfileEvent.EditProfileClick -> onProfileEditClick()
            is ProfileEvent.Reload -> loadProfile()
        }
    }

    private fun clickUserPage(){
        coroutineScope.launch {
            _action.send(
                ProfileAction.ShowSnackBar(
                    message = getString(Res.string.under_development)
                )
            )
        }
    }

    private fun loadProfile() {
        state.update { ProfileState.Loading }
        coroutineScope.launch {
            getUserUseCase().onSuccess { user ->
                state.update {
                    ProfileState.Success(user.toUi())
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        state.update { ProfileState.Error(message = getString(Res.string.unknown_error)) }
                    }
                }
            }
        }
    }
}