package kaiyrzhan.de.empath.features.articles.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository

public class DeleteCommentUseCase(
    private val repository: ArticlesRepository,
) {
    public suspend operator fun invoke(
        commentId: String,
        articleId: String,
    ): Result<Any> {
        return repository
            .deleteComment(
                commentId = commentId,
                articleId = articleId,
            )
            .toResult()
    }
}