package kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.NewCv
import kaiyrzhan.de.empath.features.vacancies.domain.repository.EmploymentRepository

public class CreateCvUseCase(
    private val repository: EmploymentRepository,
) {
    public suspend operator fun invoke(cv: NewCv): Result<Any> {
        return repository
            .createCv(cv)
            .toResult()
    }
}