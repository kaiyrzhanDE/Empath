package kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.Cv
import kaiyrzhan.de.empath.features.vacancies.domain.repository.EmploymentRepository

public class UpdateCvUseCase(
    private val repository: EmploymentRepository,
) {
    public suspend operator fun invoke(
        cvId: String,
        cv: Cv,
    ): Result<Any> {
        return repository
            .updateCv(
                cvId = cvId,
                cv = cv,
            )
            .toResult()
    }
}