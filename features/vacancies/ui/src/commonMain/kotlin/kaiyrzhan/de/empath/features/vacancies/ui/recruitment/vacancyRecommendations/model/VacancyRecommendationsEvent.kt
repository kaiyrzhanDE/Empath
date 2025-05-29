package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyRecommendations.model

internal sealed interface VacancyRecommendationsEvent {
    data object BackClick : VacancyRecommendationsEvent
    data object LoadVacancyRecommendations : VacancyRecommendationsEvent
    data object VacancyDetailClick : VacancyRecommendationsEvent
    data class ContactEmailClick(val email: String) : VacancyRecommendationsEvent
}