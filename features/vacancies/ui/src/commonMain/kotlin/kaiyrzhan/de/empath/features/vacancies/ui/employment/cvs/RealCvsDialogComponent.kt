package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs

import com.arkivanov.decompose.ComponentContext
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.cvs_not_found
import empath.core.uikit.generated.resources.unknown_error
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment.DeleteCvUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment.GetCvsUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment.GetCvsUseCaseError
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model.CvsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.cvs.model.CvsState
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.CvUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.get
import org.koin.core.component.inject

internal class RealCvsDialogComponent(
    componentContext: ComponentContext,
    private val isIndicator: Boolean,
    private val onDismissClick: () -> Unit,
    private val onSelectCv: (CvUi) -> Unit,
    private val onEditCv: (cvId: String) -> Unit,
) : BaseComponent(componentContext), CvsDialogComponent {

    private val getCvsUseCase: GetCvsUseCase = get()
    private val deleteCvUseCase: DeleteCvUseCase by inject()

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
            is CvsEvent.CvDetailClick -> onSelectCv(event.cv)
            is CvsEvent.CvEditClick -> onEditCv(event.cvId)
            is CvsEvent.CvDeleteClick -> deleteCv(event.cvId)
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
                isIndicator = isIndicator,
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
                        isIndicator = isIndicator,
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

    private fun deleteCv(cvId: String) {
        coroutineScope.launch {
            deleteCvUseCase(cvId).onSuccess {
                state.update { currentState ->
                    check(currentState is CvsState.Success)
                    currentState.copy(
                        cvs = currentState.cvs.filter { cv -> cv.id != cvId },
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        state.update {currentState ->
                            check(currentState is CvsState.Success)
                            currentState.copy(
                                errorMessage = error.toString(),
                            )
                        }
                    }
                }
            }
        }
    }
}