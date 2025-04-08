package kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.Author
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository

public class GetRecruiterUseCase(
    private val repository: RecruitmentRepository,
) {
    public suspend operator fun invoke(): Result<Author> {
        return repository.getRecruiter()
            .toResult()
    }
}

public sealed interface GetRecruiterUseCaseError : Result.Error {
    public data object RecruiterNotFound : GetRecruiterUseCaseError
}

private fun RequestResult<Author>.toResult(): Result<Author> {
    return when (val result = this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.DefaultError(result.toString())
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.NotFound -> GetRecruiterUseCaseError.RecruiterNotFound
                else -> Result.Error.DefaultError(result.toString())
            }
        }
    }
}


