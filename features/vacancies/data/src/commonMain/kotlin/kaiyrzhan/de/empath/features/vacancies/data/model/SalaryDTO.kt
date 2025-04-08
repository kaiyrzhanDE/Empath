package kaiyrzhan.de.empath.features.vacancies.data.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SalaryDTO(
    @SerialName("from_") val from: Int?,
    @SerialName("to") val to: Int?,
)

internal fun SalaryDTO.toDomain(): Salary {
    return Salary(
        from = from,
        to = to,
    )
}

