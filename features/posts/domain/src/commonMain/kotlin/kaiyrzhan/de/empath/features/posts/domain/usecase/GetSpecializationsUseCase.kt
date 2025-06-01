package kaiyrzhan.de.empath.features.posts.domain.usecase

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.posts.domain.model.Specialization
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository

public class GetSpecializationsUseCase(
    private val repository: PostsRepository,
) {
    public suspend operator fun invoke(
        query: String?,
    ): Result<ListResult<Specialization>> {
        return repository
            .getSpecializations(
                query = query,
            )
            .toResult()
    }
}