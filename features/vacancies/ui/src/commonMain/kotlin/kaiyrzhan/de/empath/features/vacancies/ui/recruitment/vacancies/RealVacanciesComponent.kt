package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies

import androidx.paging.cachedIn
import androidx.paging.map
import app.cash.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.GetVacanciesUseCase
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
import org.koin.core.component.get

internal class RealVacanciesComponent(
    componentContext: ComponentContext,
    private val onVacanciesFiltersClick: (filters: VacanciesFiltersUi) -> Unit,
    private val onVacancyCreateClick: () -> Unit,
    private val onVacancyEditClick: (vacancyId: String) -> Unit,
) : BaseComponent(componentContext), VacanciesComponent {

    private val getVacanciesUseCase: GetVacanciesUseCase = get()

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
}