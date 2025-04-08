package kaiyrzhan.de.empath.features.vacancies.data.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kotlinx.serialization.SerialName

internal class SkillRequest(
    @SerialName("name") val name: String,
    @SerialName("id") val id: String?,
)

internal fun Skill.toData(): SkillRequest {
    return SkillRequest(
        id = id,
        name = name,
    )
}