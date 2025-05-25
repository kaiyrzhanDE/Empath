package kaiyrzhan.de.empath.features.vacancies.data.model.employment

import kaiyrzhan.de.empath.core.utils.IsoType
import kaiyrzhan.de.empath.core.utils.toIso
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.WorkExperience
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class WorkExperienceRequest(
    @SerialName("company_name") val companyName: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("start_date") val startDate: String?,
    @SerialName("is_relevant") val isRelevant: Boolean,
    @SerialName("end_date") val endDate: String?,
)

internal fun WorkExperience.toData(): WorkExperienceRequest {
    return WorkExperienceRequest(
        companyName = companyName,
        title = title,
        description = description,
        startDate = startDate.toIso(type = IsoType.DATE),
        isRelevant = isRelevant,
        endDate = endDate.toIso(type = IsoType.DATE),
    )
}
