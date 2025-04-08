package kaiyrzhan.de.empath.features.vacancies.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository

public class ChangeResponseStatusUseCase(
    private val repository: RecruitmentRepository,
) {
    public suspend operator fun invoke(
        cvId: String,
        status: String,
        vacancyId: String,
    ): Result<Any> {
        return repository
            .changeResponseStatus(
                cvId = cvId,
                status = status,
                vacancyId = vacancyId,
            )
            .toResult()
    }
}