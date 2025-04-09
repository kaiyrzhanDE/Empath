package kaiyrzhan.de.empath.features.vacancies.data.model.employment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal class ResponseToVacancyRequest(
    @SerialName("cv_id") val cvId: String,
)