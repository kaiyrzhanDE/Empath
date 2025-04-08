package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
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