package kaiyrzhan.de.empath.features.posts.domain.repository

import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.posts.domain.model.Post
import kaiyrzhan.de.empath.features.posts.domain.model.Comment
import kaiyrzhan.de.empath.features.posts.domain.model.Specialization
import kaiyrzhan.de.empath.features.posts.domain.model.postEdit.EditedPost
import kaiyrzhan.de.empath.features.posts.domain.model.postCreate.NewPost
import kaiyrzhan.de.empath.features.posts.domain.model.Tag
import kotlinx.coroutines.flow.Flow

public interface PostsRepository {

    public suspend fun getPosts(
        query: String?,
        excludeWords: List<String>,
        includeWords: List<String>,
        isLiked: Boolean,
        isDisliked: Boolean,
        specializationsIds: List<String>,
        isViewed: Boolean,
        tagsIds: List<String>,
    ): RequestResult<ListResult<Post>>

    public suspend fun getPost(
        id: String,
    ): RequestResult<Post>

    public suspend fun createPost(
        newPost: NewPost,
    ): RequestResult<Any>

    public suspend fun editPost(
        postId: String,
        post: EditedPost,
    ): RequestResult<Any>

    public suspend fun deletePost(id: String): RequestResult<Any>

    public suspend fun getTags(
        query: String,
    ): Flow<PagingData<Tag>>

    public suspend fun getComments(
        postId: String,
    ): RequestResult<ListResult<Comment>>

    public suspend fun createComment(
        text: String,
        commentId: String?,
        postId: String,
    ): RequestResult<Any>

    public suspend fun deleteComment(
        commentId: String,
        postId: String,
    ): RequestResult<Any>

    public suspend fun editComment(
        commentId: String,
        postId: String,
        text: String,
    ): RequestResult<Any>

    public suspend fun likePost(
        postId: String,
    ): RequestResult<Any>

    public suspend fun cancelLikePost(
        postId: String,
    ): RequestResult<Any>

    public suspend fun dislikePost(
        postId: String,
    ): RequestResult<Any>

    public suspend fun cancelDislikePost(
        postId: String,
    ): RequestResult<Any>

    public suspend fun viewPost(
        postId: String,
    ): RequestResult<Any>

    public suspend fun likeComment(
        commentId: String,
    ): RequestResult<Any>

    public suspend fun cancelLikeComment(
        commentId: String,
    ): RequestResult<Any>

    public suspend fun dislikeComment(
        commentId: String,
    ): RequestResult<Any>

    public suspend fun cancelDislikeComment(
        commentId: String,
    ): RequestResult<Any>

    public suspend fun getSpecializations(
        query: String?,
    ): RequestResult<ListResult<Specialization>>
}