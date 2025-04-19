package kaiyrzhan.de.empath.features.posts.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository

public class EditCommentUseCase(
    private val repository: PostsRepository,
) {
    public suspend operator fun invoke(
        commentId: String,
        postId: String,
        text: String,
    ): Result<Any> {
        return repository
            .editComment(
                commentId = commentId,
                postId = postId,
                text = text,
            )
            .toResult()
    }
}