package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations

import com.arkivanov.decompose.ComponentContext
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.AppUtils
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment.GetVacancyRecommendationsUseCase
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.toUi
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.model.VacancyRecommendationsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.model.VacancyRecommendationsState
import kotlinx.coroutines.flow.MutableStateFlow
import kaiyrzhan.de.empath.core.utils.result.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.get

internal class RealVacancyRecommendationsComponent(
    componentContext: ComponentContext,
    private val vacancyId: String,
    private val onBackClick: () -> Unit,
    private val onVacancyDetailClick: (vacancyId: String) -> Unit,
    private val onCvClick: (cvId: String) -> Unit,
) : BaseComponent(componentContext), VacancyRecommendationsComponent {

    private val getVacancyRecommendationsUseCase: GetVacancyRecommendationsUseCase = get()
    private val appUtils: AppUtils = get()

    override val state = MutableStateFlow(VacancyRecommendationsState.default())

    override fun onEvent(event: VacancyRecommendationsEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is VacancyRecommendationsEvent.BackClick -> onBackClick()
            is VacancyRecommendationsEvent.LoadVacancyRecommendations -> loadVacancyRecommendations()
            is VacancyRecommendationsEvent.VacancyDetailClick -> onVacancyDetailClick(vacancyId)
            is VacancyRecommendationsEvent.CvDetailClick -> onCvClick(event.cvId)
            is VacancyRecommendationsEvent.ContactEmailClick -> contactEmail(event.email)
        }
    }

    init {
        loadVacancyRecommendations()
    }

    private fun loadVacancyRecommendations() {
        coroutineScope.launch {
            state.update { VacancyRecommendationsState.Loading }
            delay(4000)
            getVacancyRecommendationsUseCase(vacancyId).onSuccess { recommendations ->
                state.update {
                    VacancyRecommendationsState.Success(
                        recommendations = recommendations.toUi(),
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        state.update {
                            VacancyRecommendationsState.Error(error.toString())
                        }
                    }
                }
            }
        }
    }

    private fun contactEmail(email: String) {
        val link = "mailto:$email"
        appUtils.openUrl(link)
    }
}