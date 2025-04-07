package kaiyrzhan.de.empath.features.articles.domain.usecase

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.articles.domain.model.Tag
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow

public class GetTagsUseCase(
    private val repository: ArticlesRepository,
) {
    public suspend operator fun invoke(
        query: String,
    ): Flow<PagingData<Tag>> {
        return repository
            .getTags(
                query = query,
            )
    }
}