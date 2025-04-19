package kaiyrzhan.de.empath.features.posts.domain.usecase

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.posts.domain.model.Comment
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository

public class GetCommentsUseCase(
    private val repository: PostsRepository,
) {
    public suspend operator fun invoke(
        postId: String,
    ): Result<ListResult<Comment>> {
        return repository
            .getComments(
                postId = postId,
            )
            .toResult()
    }
}