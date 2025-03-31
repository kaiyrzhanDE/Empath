package kaiyrzhan.de.empath.features.articles.domain.usecase

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.articles.domain.model.Tag
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository

public class GetTagsUseCase(
    private val repository: ArticlesRepository,
) {
    public suspend operator fun invoke(
        page: Int,
        name: String,
    ): Result<ListResult<Tag>> {
        return repository
            .getTags(
                page = page,
                name = name,
            )
            .toResult()
    }
}