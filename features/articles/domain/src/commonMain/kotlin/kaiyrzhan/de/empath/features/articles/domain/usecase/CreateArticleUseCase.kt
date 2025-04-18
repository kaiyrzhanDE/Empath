package kaiyrzhan.de.empath.features.articles.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
import kaiyrzhan.de.empath.features.articles.domain.model.article_create.NewArticle
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository

public class CreateArticleUseCase(
    private val repository: ArticlesRepository,
) {
    public suspend operator fun invoke(
        newArticle: NewArticle,
    ): Result<Any> {
        return repository
            .createArticle(newArticle)
            .toResult()
    }
}

/**
 * @InvalidArticleContent:
 * - if article title length is more than 50 characters
 * - if article tag name length is more than 50 characters
 * - if article tags list is empty
 */

public sealed class CreateArticleUseCaseError : Result.Error {
    public data object InvalidArticleContent : CreateArticleUseCaseError()
}

private fun RequestResult<Any>.toResult(): Result<Any> {
    return when (val result = this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.DefaultError(result.toString())
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.UnProcessableEntity -> CreateArticleUseCaseError.InvalidArticleContent
                else -> Result.Error.DefaultError(result.toString())
            }
        }

    }
}