package kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
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

public sealed interface GetCvsUseCaseError : Result.Error {
    public data object CvsNotFound : GetCvsUseCaseError
}

private fun RequestResult<ListResult<Cv>>.toResult(): Result<ListResult<Cv>> {
    return when (val result = this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.DefaultError(result.toString())
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.NotFound -> GetCvsUseCaseError.CvsNotFound
                else -> Result.Error.DefaultError(result.toString())
            }
        }
    }
}