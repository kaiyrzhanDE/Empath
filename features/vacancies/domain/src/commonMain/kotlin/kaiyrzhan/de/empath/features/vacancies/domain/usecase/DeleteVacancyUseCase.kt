package kaiyrzhan.de.empath.features.vacancies.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository

public class DeleteVacancyUseCase(
    private val repository: RecruitmentRepository,
) {
    public suspend operator fun invoke(
        id: String,
    ): Result<Any> {
        return repository
            .deleteVacancy(id)
            .toResult()
    }
}