package kaiyrzhan.de.empath.features.vacancies.data.model.recruitment

import kaiyrzhan.de.empath.features.vacancies.domain.model.Salary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public class SalaryRequest(
    @SerialName("from") public val from: Int?,
    @SerialName("to") public val to: Int?,
)

internal fun Salary.toData(): SalaryRequest {
    return SalaryRequest(
        from = from,
        to = to,
    )
}