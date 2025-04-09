package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies

import androidx.paging.cachedIn
import androidx.paging.map
import app.cash.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment.ResponseToVacancyUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment.GetResponsesUseCase
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment.GetVacanciesUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacanciesFiltersUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.VacancyUi
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.Tab
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesAction
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesEvent
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.model.VacanciesState
import kaiyrzhan.de.empath.features.vacancies.ui.model.ResponseStatus
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
import org.koin.core.component.get
import org.koin.core.component.inject

internal class RealVacanciesComponent(
    componentContext: ComponentContext,
    private val onVacanciesFiltersClick: (filters: VacanciesFiltersUi) -> Unit,
    private val onVacancyDetailClick: (vacancyId: String, status: ResponseStatus) -> Unit,
) : BaseComponent(componentContext), VacanciesComponent {

    private val getVacanciesUseCase: GetVacanciesUseCase = get()
    private val getResponsesUseCase: GetResponsesUseCase = get()
    private val responseToVacancyUseCase: ResponseToVacancyUseCase by inject()

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
                educations = state.vacanciesFilters.educations,
            ).map { pagingData ->
                pagingData.map { it.toUi() }
            }
        }.cachedIn(coroutineScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val responses: Flow<PagingData<VacancyUi>> =
        combine(queryFlow, state) { query, state ->
            query to state
        }.flatMapLatest { (query, state) ->
            getResponsesUseCase(
                query = query,
                salaryFrom = state.vacanciesFilters.salaryFrom,
                salaryTo = state.vacanciesFilters.salaryTo,
                workExperiences = state.vacanciesFilters.workExperiences,
                workSchedules = state.vacanciesFilters.workSchedules,
                workFormats = state.vacanciesFilters.workFormats,
                excludeWords = state.vacanciesFilters.excludeWords,
                includeWords = state.vacanciesFilters.includeWords,
                educations = state.vacanciesFilters.educations,
            ).map { pagingData ->
                pagingData.map { it.toUi() }
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
            is VacanciesEvent.VacancyDetailClick -> onVacancyDetailClick(event.vacancy.id, event.vacancy.status)
            is VacanciesEvent.ResponseToVacancy -> Unit// TODO(responseToVacancy(event.vacancy))
            is VacanciesEvent.CvCreateClick -> Unit// TODO(createCv())
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
}