package kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.repository.EmploymentRepository

public class ResponseToVacancyUseCase(
    private val repository: EmploymentRepository,
) {
    public suspend operator fun invoke(
        cvId: String,
        vacancyId: String,
    ): Result<Any> {
        return repository
            .responseToVacancy(
                vacancyId = vacancyId,
                cvId = cvId,
            )
            .toResult()
    }
}