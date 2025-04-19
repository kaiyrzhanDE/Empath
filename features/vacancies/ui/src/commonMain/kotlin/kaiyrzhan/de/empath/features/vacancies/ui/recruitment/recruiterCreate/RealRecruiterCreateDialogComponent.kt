package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.recruiterCreate

import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.CreateRecruiterUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.recruiterCreate.model.RecruiterCreateEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.recruiterCreate.model.RecruiterCreateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.get

internal class RealRecruiterCreateDialogComponent(
    componentContext: ComponentContext,
    private val onDismissClick: () -> Unit,
    private val onRecruiterCreate: () -> Unit,
) : BaseComponent(componentContext), RecruiterCreateDialogComponent {

    private val createRecruiterUseCase: CreateRecruiterUseCase = get()

    override val state = MutableStateFlow<RecruiterCreateState>(
        RecruiterCreateState.default()
    )

    override fun onEvent(event: RecruiterCreateEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is RecruiterCreateEvent.CompanyNameChange -> changeCompanyName(event.companyName)
            is RecruiterCreateEvent.CompanyDescriptionChange -> changeCompanyDescription(event.companyDescription)
            is RecruiterCreateEvent.EmailChange -> changeEmail(event.email)
            is RecruiterCreateEvent.ClickCreate -> createRecruiter()
            is RecruiterCreateEvent.DismissClick -> onDismissClick()
        }
    }

    private fun changeCompanyName(companyName: String) {
        state.update { currentState ->
            currentState.copy(
                companyName = companyName,
                errorMessage = "",
            )
        }
    }

    private fun changeCompanyDescription(companyDescription: String) {
        state.update { currentState ->
            currentState.copy(
                companyDescription = companyDescription,
                errorMessage = "",
            )
        }
    }

    private fun changeEmail(email: String) {
        state.update { currentState ->
            currentState.copy(
                email = email,
                errorMessage = "",
            )
        }
    }

    private fun createRecruiter() {
        val currentState = state.value
        state.update { currentState -> currentState.copy(isLoading = true) }
        coroutineScope.launch {
            createRecruiterUseCase(
                companyName = currentState.companyName,
                companyDescription = currentState.companyDescription,
                email = currentState.email,
            ).onSuccess {
                state.update { currentState -> currentState.copy(isLoading = false) }
                onRecruiterCreate()
            }.onFailure { error ->
                state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = error.toString()
                    )
                }
            }
        }
    }
}