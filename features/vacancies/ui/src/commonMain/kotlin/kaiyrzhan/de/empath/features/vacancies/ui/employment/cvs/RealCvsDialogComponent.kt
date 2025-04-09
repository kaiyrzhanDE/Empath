package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.activate
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.cvs_not_found
import empath.core.uikit.generated.resources.unknown_error
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment.GetCvsUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment.GetCvsUseCaseError
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model.CvsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model.CvsState
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.CvUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.get

internal class RealCvsDialogComponent(
    componentContext: ComponentContext,
    private val onDismissClick: () -> Unit,
    private val onSelectCv: (CvUi) -> Unit,
) : BaseComponent(componentContext), CvsDialogComponent {

    private val getCvsUseCase: GetCvsUseCase = get()

    override val state = MutableStateFlow<CvsState>(
        CvsState.default()
    )

    init {
        loadCvs()
    }

    override fun onEvent(event: CvsEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is CvsEvent.ReloadCvs -> loadCvs()
            is CvsEvent.DismissClick -> onDismissClick()
            is CvsEvent.CvSelectClick -> selectCvClick()
            is CvsEvent.CvSelect -> selectCv(event.cv)
        }
    }

    private fun selectCv(selectedCv: CvUi) {
        val currentState = state.value
        check(currentState is CvsState.Success)
        state.update {
            CvsState.Success(
                cvs = currentState.cvs.map { cv ->
                    cv.copy(
                        isSelected = cv.id == selectedCv.id,
                    )
                },
            )
        }
    }

    private fun selectCvClick() {
        val currentState = state.value
        check(currentState is CvsState.Success)
        val selectedCv = currentState.cvs.find { it.isSelected }
        if (selectedCv != null) {
            onSelectCv(selectedCv)
        }
    }

    private fun loadCvs() {
        state.update { CvsState.Loading }
        coroutineScope.launch {
            getCvsUseCase().onSuccess { cvs ->
                state.update {
                    CvsState.Success(
                        cvs = cvs.data.map { cv -> cv.toUi() },
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is GetCvsUseCaseError.CvsNotFound -> {
                        state.update {
                            CvsState.Error(
                                message = getString(Res.string.cvs_not_found),
                            )
                        }
                    }

                    is Result.Error.DefaultError -> {
                        state.update {
                            CvsState.Error(
                                message = getString(Res.string.unknown_error),
                            )
                        }
                    }
                }
            }
        }
    }
}