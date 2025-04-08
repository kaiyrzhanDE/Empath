package kaiyrzhan.de.empath.features.vacancies.data.model

import kotlinx.serialization.SerialName

internal class ChangeResponseStatusRequest(
    @SerialName("cv_id") val cvId: String,
    @SerialName("vacancy_id") val vacancyId: String,
    @SerialName("status") val status: String,
)