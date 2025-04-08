package kaiyrzhan.de.empath.features.vacancies.data.model

import kotlinx.serialization.SerialName

internal class SkillDTO(
    @SerialName("name") val name: String?,
    @SerialName("id") val id: String,
)