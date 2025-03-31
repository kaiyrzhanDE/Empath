package kaiyrzhan.de.empath.features.articles.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository

public class EditCommentUseCase(
    private val repository: ArticlesRepository,
) {
    public suspend operator fun invoke(
        commentId: String,
        articleId: String,
        text: String,
    ): Result<Any> {
        return repository
            .editComment(
                commentId = commentId,
                articleId = articleId,
                text = text,
            )
            .toResult()
    }
}