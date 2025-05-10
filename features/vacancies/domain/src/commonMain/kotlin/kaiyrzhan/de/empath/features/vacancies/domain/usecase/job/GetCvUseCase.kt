package kaiyrzhan.de.empath.features.vacancies.domain.usecase.job

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.Cv
import kaiyrzhan.de.empath.features.vacancies.domain.repository.JobRepository

public class GetCvUseCase(
    private val repository: JobRepository,
) {
    public suspend operator fun invoke(cvId: String): Result<Cv> {
        return repository
            .getCv(cvId)
            .toResult()
    }
}