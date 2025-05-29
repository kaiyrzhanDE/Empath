package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.VacancyRecommendations

internal data class VacancyRecommendationsUi(
    val recommendations: List<CvUi>,
    val weights: List<VacancyWeightUi>,
)

internal fun VacancyRecommendations.toUi(): VacancyRecommendationsUi {
    return VacancyRecommendationsUi(
        recommendations = recommendations.map { recommendation -> recommendation.toUi() },
        weights = weights.map { weight -> weight.toUi() },
    )
}