package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail

import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.job.GetVacancyDetailUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.job.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailAction
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailEvent
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.model.VacancyDetailState
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseStatus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.get

internal class RealVacancyDetailComponent(
    componentContext: ComponentContext,
    private val vacancyId: String,
    private val onBackClick: () -> Unit,
    private val responseStatus: ResponseStatus = ResponseStatus.UNKNOWN,
    private val onVacancyDeleteClick: ((vacancyId: String) -> Unit)? = null,
    private val onResponseClick: ((vacancyId: String) -> Unit)? = null,
    private val onVacancyEditClick: ((vacancyId: String) -> Unit)? = null,
) : BaseComponent(componentContext), VacancyDetailComponent {

    private val getVacancyDetailUseCase: GetVacancyDetailUseCase = get()

    override val state = MutableStateFlow<VacancyDetailState>(
        VacancyDetailState.default()
    )

    private val _action = Channel<VacancyDetailAction>(capacity = Channel.BUFFERED)
    override val action: Flow<VacancyDetailAction> = _action.receiveAsFlow()

    init {
        loadVacancy(vacancyId)
    }

    override fun onEvent(event: VacancyDetailEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is VacancyDetailEvent.BackClick -> onBackClick()
            is VacancyDetailEvent.VacancyDeleteClick -> onVacancyDeleteClick?.invoke(vacancyId)
            is VacancyDetailEvent.VacancyEditClick -> onVacancyEditClick?.invoke(vacancyId)
            is VacancyDetailEvent.ReloadVacancyDetail -> loadVacancy(vacancyId)
            is VacancyDetailEvent.ResponseToVacancyClick -> Unit//TODO(responseToVacancy(vacancyId))
        }
    }

    private fun loadVacancy(id: String) {
        state.value = VacancyDetailState.Loading
        coroutineScope.launch {
            getVacancyDetailUseCase(id).onSuccess { detail ->
                state.update {
                    VacancyDetailState.Success(
                        vacancyDetail = detail.toUi(),
                        isEditing = onVacancyEditClick != null,
                        isDeleting = onVacancyDeleteClick != null,
                        isResponding = onResponseClick != null,
                        responseStatus = responseStatus,
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        state.update {
                            VacancyDetailState.Error(
                                message = error.toString(),
                            )
                        }
                    }
                }
            }
        }
    }



}