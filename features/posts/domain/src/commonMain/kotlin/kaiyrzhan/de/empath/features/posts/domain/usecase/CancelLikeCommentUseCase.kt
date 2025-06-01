package kaiyrzhan.de.empath.features.posts.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository

public class CancelLikeCommentUseCase(
    private val repository: PostsRepository,
) {
    public suspend operator fun invoke(commentId: String): Result<Any> {
        return repository
            .cancelLikeComment(
                commentId = commentId,
            )
            .toResult()
    }
}