package kaiyrzhan.de.empath.features.profile.ui.profileEdit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import empath.core.uikit.generated.resources.*
import io.github.vinceglb.filekit.PlatformFile
import empath.core.uikit.generated.resources.Res
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.DatePickerComponent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.RealDatePickerComponent
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.model.DatePickerDialogState
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.usecase.EditUserUseCase
import kaiyrzhan.de.empath.features.profile.domain.usecase.GetUserUseCase
import kaiyrzhan.de.empath.features.profile.domain.usecase.UpdateUserImageUseCase
import kaiyrzhan.de.empath.features.profile.domain.usecase.UpdateUserImageUseCaseError
import kaiyrzhan.de.empath.features.profile.ui.model.toDomain
import kaiyrzhan.de.empath.features.profile.ui.model.toUi
import kaiyrzhan.de.empath.features.profile.ui.profileEdit.model.ProfileEditAction
import kaiyrzhan.de.empath.features.profile.ui.profileEdit.model.ProfileEditEvent
import kaiyrzhan.de.empath.features.profile.ui.profileEdit.model.ProfileEditState
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
    private val onBackClick: (isProfileEdited: Boolean) -> Unit,
) : BaseComponent(componentContext), ProfileEditComponent {

    private val getUserUseCase: GetUserUseCase by inject()
    private val editUserUseCase: EditUserUseCase by inject()
    private val updateUserImageUseCase: UpdateUserImageUseCase by inject()

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
            is ProfileEditEvent.PhotoSelect -> selectPhoto(event.image)
            is ProfileEditEvent.NicknameChange -> changeNickname(event.nickname)
            is ProfileEditEvent.NameChange -> changeName(event.name)
            is ProfileEditEvent.LastnameChange -> changeLastname(event.lastname)
            is ProfileEditEvent.PatronymicChange -> changePatronymic(event.patronymic)
            is ProfileEditEvent.GenderSelect -> selectGender(event.gender)
            is ProfileEditEvent.BackClick -> backClick()
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
                        text = getString(Res.string.cancel),
                    ),
                    onDismissClick = datePickerNavigation::dismiss,
                    confirmActionConfig = DialogActionConfig(
                        text = getString(Res.string.okay),
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

    private fun backClick() {
        when (val currentState = state.value) {
            is ProfileEditState.Success -> onBackClick(currentState.isUserChanged())
            else -> onBackClick(false)
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
                    is Result.Error.DefaultError -> {
                        state.update { ProfileEditState.Error(error.toString()) }
                    }
                }
            }
        }
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

    private fun selectPhoto(image: PlatformFile) {
        val currentState = state.value
        check(currentState is ProfileEditState.Success)
        state.update {
            currentState.copy(
                isImageLoading = true,
            )
        }
        coroutineScope.launch {
            val imageBytes = image.readBytes()
            updateUserImageUseCase(
                image = imageBytes,
                imageName = image.name,
                imageType = image.extension,
            ).onSuccess {
                state.update {
                    currentState.copy(
                        isImageLoading = false,
                        selectedImage = image,
                    )
                }
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is UpdateUserImageUseCaseError.UserImageTooLarge -> {
                        _action.send(
                            ProfileEditAction.ShowSnackbar(
                                message = getString(Res.string.file_too_large),
                            ),
                        )
                    }

                    is Result.Error.DefaultError -> {
                        _action.send(
                            ProfileEditAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun save() {
        val currentState = state.value
        check(currentState is ProfileEditState.Success)
        state.update { ProfileEditState.Loading }
        coroutineScope.launch {
            editUserUseCase(
                user = currentState.editableUser.toDomain(),
            ).onSuccess {
                loadProfile()
                _action.send(
                    ProfileEditAction.ShowSnackbar(
                        message = getString(Res.string.profile_edit_successfully)
                    ),
                )
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            ProfileEditAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            ),
                        )
                    }
                }
            }
        }
    }
}