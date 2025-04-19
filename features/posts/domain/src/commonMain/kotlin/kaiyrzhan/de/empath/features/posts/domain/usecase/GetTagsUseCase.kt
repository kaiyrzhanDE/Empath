package kaiyrzhan.de.empath.features.posts.domain.usecase

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.posts.domain.model.Tag
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

public class GetTagsUseCase(
    private val repository: PostsRepository,
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