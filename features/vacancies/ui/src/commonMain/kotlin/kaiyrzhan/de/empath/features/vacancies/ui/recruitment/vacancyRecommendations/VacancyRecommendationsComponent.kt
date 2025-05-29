package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations

import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.model.VacancyRecommendationsEvent
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.model.VacancyRecommendationsState
import kotlinx.coroutines.flow.StateFlow

internal interface VacancyRecommendationsComponent {

    val state: StateFlow<VacancyRecommendationsState>

    fun onEvent(event: VacancyRecommendationsEvent)

}