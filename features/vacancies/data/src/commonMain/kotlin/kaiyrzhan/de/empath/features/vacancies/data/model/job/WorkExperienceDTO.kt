package kaiyrzhan.de.empath.features.vacancies.data.model.job

import kaiyrzhan.de.empath.core.utils.toInstantOrNull
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.WorkExperience
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class WorkExperienceDTO(
    @SerialName("company_name") val companyName: String?,
    @SerialName("title") val title: String?,
    @SerialName("description") val description: String?,
    @SerialName("start_date") val startDate: String?,
    @SerialName("is_relevant") val isRelevant: Boolean?,
    @SerialName("end_date") val endDate: String?,
)

internal fun WorkExperienceDTO.toDomain(): WorkExperience {
    return WorkExperience(
        companyName = companyName.orEmpty(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        startDate = startDate.toInstantOrNull(),
        isRelevant = isRelevant == true,
        endDate = endDate.toInstantOrNull(),
    )
}
