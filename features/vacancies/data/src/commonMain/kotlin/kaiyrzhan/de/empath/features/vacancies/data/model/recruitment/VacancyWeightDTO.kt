package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kaiyrzhan.de.empath.core.utils.logger.ifNull
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.VacancyWeight
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class VacancyWeightDTO(
    @SerialName("weight") val weight: Double?,
    @SerialName("name") val name: String?,
    @SerialName("id") val id: String,
)

internal fun VacancyWeightDTO.toDomain(): VacancyWeight {
    return VacancyWeight(
        id = id,
        weight = weight.ifNull { 0.0 },
        name = name.orEmpty(),
    )
}