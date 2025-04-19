package kaiyrzhan.de.empath.features.posts.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.posts.domain.model.postEdit.EditedPost
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository

public class EditPostUseCase(
    private val repository: PostsRepository,
) {
    public suspend operator fun invoke(
        id: String,
        post: EditedPost,
    ): Result<Any> {
        return repository
            .editPost(id, post)
            .toResult()
    }
}