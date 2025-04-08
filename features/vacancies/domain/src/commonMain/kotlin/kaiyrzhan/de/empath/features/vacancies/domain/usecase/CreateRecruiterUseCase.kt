package kaiyrzhan.de.empath.features.vacancies.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository

public class CreateRecruiterUseCase(
    private val repository: RecruitmentRepository,
) {
    public suspend operator fun invoke(
        companyName: String,
        companyDescription: String,
        email: String,
    ): Result<Any> {
        return repository
            .createRecruiter(
                companyName = companyName,
                companyDescription = companyDescription,
                email = email,
            )
            .toResult()
    }
}