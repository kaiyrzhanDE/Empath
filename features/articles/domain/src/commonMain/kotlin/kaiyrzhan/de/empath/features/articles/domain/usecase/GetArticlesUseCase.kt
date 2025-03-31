package kaiyrzhan.de.empath.features.articles.domain.usecase

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.articles.domain.model.Article
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository

public class GetArticlesUseCase(
    private val repository: ArticlesRepository,
) {
    public suspend operator fun invoke(
        page: Int,
        search: String,
    ): Result<ListResult<Article>> {
        return repository
            .getArticles(
                page = page,
                search = search,
            )
            .toResult()
    }
}