package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.VacancyRecommendations
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class VacancyRecommendationsDTO(
    @SerialName("weight") val weights: VacancyWeightDTO,
    @SerialName("recommendations") val recommendations: List<CvDTO>,
)

internal fun VacancyRecommendationsDTO.toDomain(): VacancyRecommendations {
    return VacancyRecommendations(
        weights = weights.toDomain(),
        recommendations = recommendations.map { cv -> cv.toDomain() },
    )
}