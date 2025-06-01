package kaiyrzhan.de.empath.features.posts.domain.usecase

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.posts.domain.model.Post
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository

public class GetPostsUseCase(
    private val repository: PostsRepository,
) {
    private val whiteSpaceRegex = Regex("\\s+")
    public suspend operator fun invoke(
        query: String?,
        excludeWords: String,
        includeWords: String,
        isLiked: Boolean,
        isDisliked: Boolean,
        isViewed: Boolean,
        specializationsIds: List<String>,
        tagsIds: List<String>,
    ): Result<ListResult<Post>> {
        return repository
            .getPosts(
                query = query,
                excludeWords = excludeWords
                    .replace(whiteSpaceRegex, "")
                    .split(",")
                    .filter { word -> word.isNotBlank() },
                includeWords = includeWords
                    .replace(whiteSpaceRegex, "")
                    .split(",")
                    .filter { word -> word.isNotBlank() },
                isLiked = isLiked,
                isDisliked = isDisliked,
                specializationsIds = specializationsIds,
                isViewed = isViewed,
                tagsIds = tagsIds,
            )
            .toResult()
    }
}