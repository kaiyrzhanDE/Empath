package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies

import androidx.paging.cachedIn
import androidx.paging.map
import app.cash.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.unknown_error
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.ChangeResponseStatusUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.GetRecruiterUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.GetRecruiterUseCaseError
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.GetResponsesUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.GetVacanciesUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.job.model.AuthorUi
import kaiyrzhan.de.empath.features.vacancies.ui.job.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter.RealRecruiterCreateDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter.RecruiterCreateDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createRecruiter.model.RecruiterCreateState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.ResponseStatus
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.ResponseUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.VacanciesFiltersUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.RealSkillsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.SkillsDialogComponent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.skills.model.SkillsState
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
import kotlin.getValue

internal class RealVacanciesComponent(
    componentContext: ComponentContext,
    private val onVacanciesFiltersClick: (filters: VacanciesFiltersUi) -> Unit,
    private val onVacancyCreateClick: (author: AuthorUi) -> Unit,
    private val onVacancyEditClick: (vacancyId: String) -> Unit,
    private val onVacancyDetailClick: (vacancyId: String) -> Unit,
    private val onCvClick: (cvId: String) -> Unit,
) : BaseComponent(componentContext), VacanciesComponent {

    private val getVacanciesUseCase: GetVacanciesUseCase = get()
    private val getResponsesUseCase: GetResponsesUseCase = get()
    private val getRecruiterUseCase: GetRecruiterUseCase by inject()
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

    private val recruiterDialogNavigation = SlotNavigation<RecruiterCreateState>()
    override val recruiterDialog: Value<ChildSlot<*, RecruiterCreateDialogComponent>> = childSlot(
        source = recruiterDialogNavigation,
        key = MessageDialogComponent.DEFAULT_KEY,
        serializer = RecruiterCreateState.serializer(),
        childFactory = ::createRecruiterDialog,
    )

    override fun onEvent(event: VacanciesEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is VacanciesEvent.VacanciesSearch -> searchVacancies(event.query)
            is VacanciesEvent.TabChange -> changeTab(event.index)
            is VacanciesEvent.VacanciesFiltersClick -> onVacanciesFiltersClick(state.value.vacanciesFilters)
            is VacanciesEvent.VacancyCreateClick -> clickVacancyCreate()
            is VacanciesEvent.VacancyEditClick -> onVacancyEditClick(event.id)
            is VacanciesEvent.VacancyHideClick -> hideVacancy(event.id)
            is VacanciesEvent.VacancyDetailClick -> onVacancyDetailClick(event.id)
            is VacanciesEvent.ResponseCvClick -> onCvClick(event.response.cvId)
            is VacanciesEvent.ResponseAccept -> acceptResponse(event.response)
            is VacanciesEvent.ResponseReject -> rejectResponse(event.response)
        }
    }

    private fun createRecruiterDialog(
        state: RecruiterCreateState,
        childComponentContext: ComponentContext,
    ): RecruiterCreateDialogComponent {
        return RealRecruiterCreateDialogComponent(
            componentContext = childComponentContext,
            onDismissClick = recruiterDialogNavigation::dismiss,
            onRecruiterCreate = ::clickVacancyCreate,
        )
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
                            VacanciesAction.ShowSnackbar(
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
                            VacanciesAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }

    private fun clickVacancyCreate() {
        coroutineScope.launch {
            getRecruiterUseCase().onSuccess { author ->
                onVacancyCreateClick(author.toUi())
            }.onFailure { error ->
                when (error) {
                    is GetRecruiterUseCaseError.RecruiterNotFound -> {
                        recruiterDialogNavigation.activate(
                            RecruiterCreateState.default()
                        )
                    }

                    is Result.Error.DefaultError -> {
                        _action.send(
                            VacanciesAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }
}