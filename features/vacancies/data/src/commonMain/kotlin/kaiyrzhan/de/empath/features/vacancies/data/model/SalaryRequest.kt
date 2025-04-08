package kaiyrzhan.de.empath.features.vacancies.data.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Salary
import kotlinx.serialization.SerialName

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