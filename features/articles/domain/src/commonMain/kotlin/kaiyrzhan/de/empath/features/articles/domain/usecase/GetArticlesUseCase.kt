package kaiyrzhan.de.empath.features.articles.domain.usecase

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.articles.domain.model.Article
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow

public class GetArticlesUseCase(
    private val repository: ArticlesRepository,
) {
    public operator fun invoke(
        query: String?,
    ): Flow<PagingData<Article>> {
        return repository.getArticles(
            query = query,
        )
    }
}