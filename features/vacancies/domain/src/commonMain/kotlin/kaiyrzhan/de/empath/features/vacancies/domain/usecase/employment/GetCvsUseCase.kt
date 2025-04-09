package kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Cv
import kaiyrzhan.de.empath.features.vacancies.domain.repository.EmploymentRepository

public class GetCvsUseCase(
    private val repository: EmploymentRepository,
) {
    public suspend operator fun invoke(): Result<ListResult<Cv>> {
        return repository
            .getCvs()
            .toResult()
    }
}