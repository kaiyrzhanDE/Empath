package kaiyrzhan.de.empath.features.posts.domain.usecase

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.posts.domain.model.Post
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

public class GetPostsUseCase(
    private val repository: PostsRepository,
) {
    public operator fun invoke(
        query: String?,
    ): Flow<PagingData<Post>> {
        return repository.getPosts(
            query = query,
        )
    }
}