package kaiyrzhan.de.empath.features.vacancies.domain.usecase.job

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.VacancyDetail
import kaiyrzhan.de.empath.features.vacancies.domain.repository.JobRepository

public class GetVacancyDetailUseCase(
    private val repository: JobRepository,
) {
    public suspend operator fun invoke(id: String): Result<VacancyDetail> {
        return repository
            .getVacancyDetail(id = id)
            .toResult()
    }
}