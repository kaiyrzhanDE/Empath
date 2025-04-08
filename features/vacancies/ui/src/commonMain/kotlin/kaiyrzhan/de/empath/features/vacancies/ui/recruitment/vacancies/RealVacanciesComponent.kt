package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies

import androidx.paging.cachedIn
import androidx.paging.map
import app.cash.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.unknown_error
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.ChangeResponseStatusUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.GetResponsesUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.GetVacanciesUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseStatus
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacanciesFiltersUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.Tab
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesAction
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.model.VacanciesState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.get
import org.koin.core.component.inject

internal class RealVacanciesComponent(
    componentContext: ComponentContext,
    private val onVacanciesFiltersClick: (filters: VacanciesFiltersUi) -> Unit,
    private val onVacancyCreateClick: () -> Unit,
    private val onVacancyEditClick: (vacancyId: String) -> Unit,
    private val onVacancyDetailClick: (vacancyId: String) -> Unit,
    private val onCvClick: (cvId: String) -> Unit,
) : BaseComponent(componentContext), VacanciesComponent {

    private val getVacanciesUseCase: GetVacanciesUseCase = get()
    private val getResponsesUseCase: GetResponsesUseCase = get()
    private val changeResponseStatusUseCase: ChangeResponseStatusUseCase by inject()

    override val state = MutableStateFlow<VacanciesState>(
        VacanciesState.default()
    )

    @OptIn(FlowPreview::class)
    private val queryFlow = state
        .map { state -> state.query }
        .distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val vacancies: Flow<PagingData<VacancyUi>> =
        combine(queryFlow, state) { query, state ->
            query to state
        }.flatMapLatest { (query, state) ->
            getVacanciesUseCase(
                query = query,
                salaryFrom = state.vacanciesFilters.salaryFrom,
                salaryTo = state.vacanciesFilters.salaryTo,
                workExperiences = state.vacanciesFilters.workExperiences,
                workSchedules = state.vacanciesFilters.workSchedules,
                workFormats = state.vacanciesFilters.workFormats,
                excludeWords = state.vacanciesFilters.excludeWords,
                includeWords = state.vacanciesFilters.includeWords,
            ).map { pagingData ->
                pagingData.map { it.toUi() }
            }
        }.cachedIn(coroutineScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val responses: Flow<PagingData<ResponseUi>> =
        state.flatMapLatest { currentState ->
            getResponsesUseCase().map { pagingData ->
                pagingData.map { response ->
                    val responseUi = response.toUi()
                    responseUi.copy(
                        status = when (responseUi) {
                            in currentState.acceptedResponses -> ResponseStatus.ACCEPTED
                            in currentState.rejectedResponses -> ResponseStatus.REJECTED
                            else -> responseUi.status
                        },
                    )
                }
            }
        }.cachedIn(coroutineScope)


    private val _action = Channel<VacanciesAction>(capacity = Channel.BUFFERED)
    override val action: Flow<VacanciesAction> = _action.receiveAsFlow()


    override fun onEvent(event: VacanciesEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is VacanciesEvent.VacanciesSearch -> searchVacancies(event.query)
            is VacanciesEvent.TabChange -> changeTab(event.index)
            is VacanciesEvent.VacanciesFiltersClick -> onVacanciesFiltersClick(state.value.vacanciesFilters)
            is VacanciesEvent.VacancyCreateClick -> onVacancyCreateClick()
            is VacanciesEvent.VacancyEditClick -> onVacancyEditClick(event.id)
            is VacanciesEvent.VacancyHideClick -> hideVacancy(event.id)
            is VacanciesEvent.VacancyDetailClick -> onVacancyDetailClick(event.id)
            is VacanciesEvent.ResponseCvClick -> onCvClick(event.response.cvId)
            is VacanciesEvent.ResponseAccept -> acceptResponse(event.response)
            is VacanciesEvent.ResponseReject -> rejectResponse(event.response)
        }
    }

    private fun changeTab(index: Int) {
        state.update { currentState ->
            currentState.copy(
                currentTab = currentState.tabs.getOrElse(index) { Tab.Vacancies },
            )
        }
    }

    private fun searchVacancies(query: String) {
        state.update { currentState ->
            currentState.copy(
                query = query,
            )
        }
    }

    private fun hideVacancy(id: String) {
        //TODO(Not yer implemented)
    }

    private fun acceptResponse(
        response: ResponseUi,
    ) {
        coroutineScope.launch {
            changeResponseStatusUseCase(
                cvId = response.cvId,
                vacancyId = response.vacancyId,
                status = ResponseStatus.ACCEPTED.type,
            ).onSuccess { result ->
                state.update { currentState ->
                    currentState.copy(
                        acceptedResponses = currentState.acceptedResponses + response,
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            VacanciesAction.ShowSnacbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }

    private fun rejectResponse(response: ResponseUi) {
        coroutineScope.launch {
            changeResponseStatusUseCase(
                cvId = response.cvId,
                vacancyId = response.vacancyId,
                status = ResponseStatus.REJECTED.type,
            ).onSuccess { result ->
                state.update { currentState ->
                    currentState.copy(
                        rejectedResponses = currentState.rejectedResponses + response,
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            VacanciesAction.ShowSnacbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }
}