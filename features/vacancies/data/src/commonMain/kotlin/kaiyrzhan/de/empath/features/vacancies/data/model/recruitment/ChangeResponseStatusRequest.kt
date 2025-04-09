package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ChangeResponseStatusRequest(
    @SerialName("cv_id") val cvId: String,
    @SerialName("vacancy_id") val vacancyId: String,
    @SerialName("status") val status: String,
)