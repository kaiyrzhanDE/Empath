package kaiyrzhan.de.empath.features.posts.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository

public class DeletePostUseCase(
    private val repository: PostsRepository,
) {
    public suspend operator fun invoke(
        id: String,
    ): Result<Any> {
        return repository
            .deletePost(id)
            .toResult()
    }
}