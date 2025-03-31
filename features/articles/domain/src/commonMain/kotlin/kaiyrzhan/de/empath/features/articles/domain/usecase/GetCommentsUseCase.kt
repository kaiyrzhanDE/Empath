package kaiyrzhan.de.empath.features.articles.domain.usecase

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.articles.domain.model.Comment
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository

public class GetCommentsUseCase(
    private val repository: ArticlesRepository,
) {
    public suspend operator fun invoke(
        page: Int,
        articleId: String,
    ): Result<ListResult<Comment>> {
        return repository
            .getComments(
                page = page,
                articleId = articleId,
            )
            .toResult()
    }
}