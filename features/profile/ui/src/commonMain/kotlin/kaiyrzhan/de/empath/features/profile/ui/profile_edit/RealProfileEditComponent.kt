package kaiyrzhan.de.empath.features.profile.ui.profile_edit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import empath.core.uikit.generated.resources.cancel
import empath.core.uikit.generated.resources.okay
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.features.profile.ui.generated.resources.select_date_of_birth
import empath.features.profile.ui.generated.resources.Res as FeatureRes
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.DatePickerComponent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.RealDatePickerComponent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogState
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.format
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.usecase.GetUserUseCase
import kaiyrzhan.de.empath.features.profile.ui.model.toUi
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.FieldState
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditAction
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditEvent
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.getString
import org.koin.core.component.inject

internal class RealProfileEditComponent(
    componentContext: ComponentContext,
    private val onBackClick: () -> Unit,
) : BaseComponent(componentContext), ProfileEditComponent {

    private val getUserUseCase: GetUserUseCase by inject()

    override val state = MutableStateFlow<ProfileEditState>(
        ProfileEditState.default()
    )

    private val _action = Channel<ProfileEditAction>(capacity = Channel.BUFFERED)
    override val action: Flow<ProfileEditAction> = _action.receiveAsFlow()

    private val datePickerNavigation = SlotNavigation<DatePickerDialogState>()
    override val datePicker: Value<ChildSlot<*, DatePickerComponent>> = childSlot(
        source = datePickerNavigation,
        key = DatePickerComponent.DEFAULT_KEY,
        serializer = DatePickerDialogState.serializer(),
        childFactory = ::createDatePicker,
    )

    init {
        loadProfile()
    }

    override fun onEvent(event: ProfileEditEvent) {
        when (event) {
            is ProfileEditEvent.PhotoSelect -> selectPhoto(event.imageUrl)
            is ProfileEditEvent.NicknameChange -> changeNickname(event.nickname)
            is ProfileEditEvent.NameChange -> changeName(event.name)
            is ProfileEditEvent.LastnameChange -> changeLastname(event.lastname)
            is ProfileEditEvent.PatronymicChange -> changePatronymic(event.patronymic)
            is ProfileEditEvent.GenderSelect -> selectGender(event.gender)
            is ProfileEditEvent.BackClick -> onBackClick()
            is ProfileEditEvent.Save -> save()
            is ProfileEditEvent.Cancel -> cancel()
            is ProfileEditEvent.LoadProfile -> loadProfile()
            is ProfileEditEvent.DatePickerShow -> showDatePicker()
        }
    }

    private fun createDatePicker(
        state: DatePickerDialogState,
        childComponentContext: ComponentContext,
    ): DatePickerComponent {
        return RealDatePickerComponent(
            componentContext = childComponentContext,
            dialogState = state,
        )
    }

    private fun showDatePicker() {
        val currentState = state.value
        check(currentState is ProfileEditState.Success)
        coroutineScope.launch {
            datePickerNavigation.activate(
                configuration = DatePickerDialogState(
                    selectedDate = currentState.editableUser.dateOfBirth,
                    dismissActionConfig = DialogActionConfig(
                        text = getString(CoreRes.string.cancel),
                    ),
                    onDismissClick = datePickerNavigation::dismiss,
                    confirmActionConfig = DialogActionConfig(
                        text = getString(CoreRes.string.okay),
                        isPrimary = true,
                    ),
                    onConfirmClick = { dateOfBirth ->
                        selectDateOfBirth(dateOfBirth)
                        datePickerNavigation.dismiss()
                    },
                ),
            )
        }
    }


    private fun loadProfile() {
        state.update { ProfileEditState.Loading }
        coroutineScope.launch {
            getUserUseCase().onSuccess { user ->
                state.update {
                    ProfileEditState.Success(
                        originalUser = user.toUi(),
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.UnknownError -> {
                        state.update { ProfileEditState.Error(error.toString()) }
                    }

                    is Result.Error.UnknownRemoteError -> {
                        state.update { ProfileEditState.Error(error.toString()) }
                    }
                }
            }
        }
    }

    private fun selectPhoto(imageUrl: String) {
//        TODO("")
    }

    private fun changeNickname(nickname: String) {
        state.update { currentState ->
            check(currentState is ProfileEditState.Success)
            currentState.copy(
                editableUser = currentState.editableUser.copy(
                    nickname = nickname,
                )
            )
        }
    }

    private fun changeName(name: String) {
        state.update { currentState ->
            check(currentState is ProfileEditState.Success)
            currentState.copy(
                editableUser = currentState.editableUser.copy(
                    name = name,
                )
            )
        }
    }

    private fun changeLastname(lastname: String) {
        state.update { currentState ->
            check(currentState is ProfileEditState.Success)
            currentState.copy(
                editableUser = currentState.editableUser.copy(
                    lastname = lastname,
                )
            )
        }
    }

    private fun changePatronymic(patronymic: String) {
        state.update { currentState ->
            check(currentState is ProfileEditState.Success)
            currentState.copy(
                editableUser = currentState.editableUser.copy(
                    patronymic = patronymic,
                )
            )
        }
    }

    private fun selectDateOfBirth(dateOfBirth: LocalDateTime?) {
        state.update { currentState ->
            check(currentState is ProfileEditState.Success)
            currentState.copy(
                editableUser = currentState.editableUser.copy(
                    dateOfBirth = dateOfBirth,
                ),
            )
        }
    }

    private fun selectGender(gender: User.Gender) {
        state.update { currentState ->
            check(currentState is ProfileEditState.Success)
            currentState.copy(
                editableUser = currentState.editableUser.copy(
                    gender = gender,
                )
            )
        }
    }

    private fun cancel() {
        state.update { currentState ->
            check(currentState is ProfileEditState.Success)
            currentState.copy(
                editableUser = currentState.originalUser,
            )
        }
    }

    private fun save() {

    }

}