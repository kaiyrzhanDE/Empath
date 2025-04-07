package kaiyrzhan.de.empath.features.articles.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.articles.domain.model.article_edit.EditedArticle
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository

public class EditArticleUseCase(
    private val repository: ArticlesRepository,
) {
    public suspend operator fun invoke(
        id: String,
        article: EditedArticle,
    ): Result<Any> {
        return repository
            .editArticle(id, article)
            .toResult()
    }
}