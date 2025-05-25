package kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.repository.EmploymentRepository

public class DeleteCvUseCase(
    private val repository: EmploymentRepository,
) {
    public suspend operator fun invoke(cvId: String): Result<Any> {
        return repository
            .deleteCv(cvId)
            .toResult()
    }
}