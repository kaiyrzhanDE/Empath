package kaiyrzhan.de.empath.features.articles.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.articles.domain.model.Article
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository

public class GetArticleUseCase(
    private val repository: ArticlesRepository,
) {
    public suspend operator fun invoke(articleId: String): Result<Article> {
        return repository.getArticle(articleId)
            .toResult()
    }
}