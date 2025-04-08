package kaiyrzhan.de.empath.features.vacancies.ui.model

import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Salary

internal data class SalaryUi(
    val from: Int?,
    val to: Int?,
)

internal fun Salary.toUi(): SalaryUi {
    return SalaryUi(
        from = from,
        to = to,
    )
}
