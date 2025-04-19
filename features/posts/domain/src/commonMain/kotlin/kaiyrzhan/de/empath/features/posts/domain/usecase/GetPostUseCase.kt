package kaiyrzhan.de.empath.features.posts.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.posts.domain.model.Post
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository

public class GetPostUseCase(
    private val repository: PostsRepository,
) {
    public suspend operator fun invoke(postId: String): Result<Post> {
        return repository
            .getPost(postId)
            .toResult()
    }
}