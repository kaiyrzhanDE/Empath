package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.model

import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model.VacancyRecommendationsUi

internal sealed class VacancyRecommendationsState {
    object Initial : VacancyRecommendationsState()
    object Loading : VacancyRecommendationsState()
    class Error(val message: String) : VacancyRecommendationsState()
    data class Success(
        val recommendations: VacancyRecommendationsUi,
    ) : VacancyRecommendationsState()

    companion object {
        fun default(): VacancyRecommendationsState {
            return Initial
        }
    }
}