package kaiyrzhan.de.empath.features.posts.domain.repository

import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.posts.domain.model.Post
import kaiyrzhan.de.empath.features.posts.domain.model.Comment
import kaiyrzhan.de.empath.features.posts.domain.model.postEdit.EditedPost
import kaiyrzhan.de.empath.features.posts.domain.model.postCreate.NewPost
import kaiyrzhan.de.empath.features.posts.domain.model.Tag
import kotlinx.coroutines.flow.Flow

public interface PostsRepository {

    public fun getPosts(
        query: String?,
    ): Flow<PagingData<Post>>

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
    ):  Flow<PagingData<Tag>>

    public suspend fun getComments(
        postId: String,
    ): RequestResult<ListResult<Comment>>

    public suspend fun createComment(
        text: String,
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
}