package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kaiyrzhan.de.empath.core.utils.logger.ifNull
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.SkillWeight
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SkillWeightDTO(
    @SerialName("name") val name: String?,
    @SerialName("weight") val weight: Double?,
)

internal fun SkillWeightDTO.toDomain(): SkillWeight {
    return SkillWeight(
        name = name.orEmpty(),
        weight = weight.ifNull { 0.0 },
    )
}