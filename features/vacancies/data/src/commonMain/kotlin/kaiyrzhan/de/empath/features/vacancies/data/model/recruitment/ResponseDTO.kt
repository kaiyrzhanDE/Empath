package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kaiyrzhan.de.empath.core.utils.DatePattern
import kaiyrzhan.de.empath.core.utils.toInstantOrNull
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Response
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ResponseDTO(
    @SerialName("response_author") val authorFullName: String?,
    @SerialName("response_email") val authorEmail: String?,
    @SerialName("vacancy_id") val vacancyId: String,
    @SerialName("cv_id") val cvId: String,
    @SerialName("cv_title") val cvTitle: String?,
    @SerialName("created_at") val dateOfCreated: String?,
    @SerialName("status") val status: String?,
)

internal fun ResponseDTO.toDomain(): Response {
    return Response(
        authorFullName = authorFullName.orEmpty(),
        authorEmail = authorEmail.orEmpty(),
        vacancyId = vacancyId,
        cvId = cvId,
        cvTitle = cvTitle.orEmpty(),
        dateOfCreated = dateOfCreated?.toInstantOrNull(DatePattern.DATE),
        status = status.orEmpty(),
    )
}