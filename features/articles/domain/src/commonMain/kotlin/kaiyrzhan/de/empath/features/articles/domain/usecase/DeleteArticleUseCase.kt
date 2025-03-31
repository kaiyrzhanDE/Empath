package kaiyrzhan.de.empath.features.articles.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository

public class DeleteArticleUseCase(
    private val repository: ArticlesRepository,
) {
    public suspend operator fun invoke(
        id: String,
    ): Result<Any> {
        return repository
            .deleteArticle(id)
            .toResult()
    }
}