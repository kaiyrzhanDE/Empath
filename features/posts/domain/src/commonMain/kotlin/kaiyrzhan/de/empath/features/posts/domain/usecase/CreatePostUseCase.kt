package kaiyrzhan.de.empath.features.posts.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.StatusCode
import kaiyrzhan.de.empath.features.posts.domain.model.postCreate.NewPost
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository

public class CreatePostUseCase(
    private val repository: PostsRepository,
) {
    public suspend operator fun invoke(
        newPost: NewPost,
    ): Result<Any> {
        return repository
            .createPost(newPost)
            .toResult()
    }
}

/**
 * @InvalidPostContent:
 * - if post title length is more than 50 characters
 * - if post tag name length is more than 50 characters
 * - if post tags list is empty
 */

public sealed class CreatePostUseCaseError : Result.Error {
    public data object InvalidPostContent : CreatePostUseCaseError()
}

private fun RequestResult<Any>.toResult(): Result<Any> {
    return when (val result = this) {
        is RequestResult.Success -> Result.Success(data)
        is RequestResult.Failure.Exception -> Result.Error.DefaultError(result.toString())
        is RequestResult.Failure.Error -> {
            when (statusCode) {
                StatusCode.UnProcessableEntity -> CreatePostUseCaseError.InvalidPostContent
                else -> Result.Error.DefaultError(result.toString())
            }
        }

    }
}