package kaiyrzhan.de.empath.features.vacancies.domain.usecase.job

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kaiyrzhan.de.empath.features.vacancies.domain.repository.JobRepository

public class GetEmploymentTypesUseCase(
    private val repository: JobRepository,
) {
    public suspend operator fun invoke(): Result<List<Skill>> {
        return repository
            .getEmploymentTypes()
            .toResult()
    }
}
