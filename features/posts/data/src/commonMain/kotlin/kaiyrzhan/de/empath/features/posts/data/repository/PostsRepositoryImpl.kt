package kaiyrzhan.de.empath.features.posts.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.pagination.PaginationUtils
import kaiyrzhan.de.empath.core.utils.pagination.map
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.toDomain
import kaiyrzhan.de.empath.features.posts.data.model.*
import kaiyrzhan.de.empath.features.posts.data.pagingSource.PostsPagingSource
import kaiyrzhan.de.empath.features.posts.data.pagingSource.TagsPagingSource
import kaiyrzhan.de.empath.features.posts.data.remote.PostsApi
import kaiyrzhan.de.empath.features.posts.domain.model.*
import kaiyrzhan.de.empath.features.posts.domain.model.postCreate.NewPost
import kaiyrzhan.de.empath.features.posts.domain.model.postEdit.EditedPost
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

internal class PostsRepositoryImpl(
    private val api: PostsApi,
) : PostsRepository {

    override fun getPosts(
        query: String?,
    ): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = PaginationUtils.PAGE_LIMIT_NORMAL,
                prefetchDistance = 3,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                PostsPagingSource(
                    api = api,
                    query = query,
                )
            }
        ).flow
    }

    override suspend fun getPost(
        id: String,
    ): RequestResult<Post> {
        return api.getPost(
            id = id,
        ).toDomain { post -> post.toDomain() }
    }

    override suspend fun createPost(
        newPost: NewPost,
    ): RequestResult<Any> {
        return api.createPost(
            request = newPost.toData(),
        )
    }

    override suspend fun editPost(
        id: String,
        post: EditedPost,
    ): RequestResult<Any> {
        return api.editPost(
            id = id,
            request = post.toData(),
        )
    }

    override suspend fun deletePost(id: String): RequestResult<Any> {
        return api.deletePost(
            id = id,
        )
    }

    override suspend fun getTags(
        query: String,
    ): Flow<PagingData<Tag>> {
        return Pager(
            config = PagingConfig(
                pageSize = PaginationUtils.PAGE_LIMIT_NORMAL,
                prefetchDistance = 3,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                TagsPagingSource(
                    api = api,
                    query = query,
                )
            }
        ).flow
    }

    override suspend fun getComments(
        postId: String,
    ): RequestResult<ListResult<Comment>> {
        return api.getComments(
            postId = postId,
        ).toDomain { comments ->
            comments.map { comment -> comment.toDomain() }
        }
    }

    override suspend fun createComment(
        text: String,
        postId: String,
    ): RequestResult<Any> {
        return api.createComment(
            postId = postId,
            request = CommentRequest(
                text = text,
            ),
        )
    }

    override suspend fun deleteComment(
        commentId: String,
        postId: String,
    ): RequestResult<Any> {
        return api.deleteComment(
            commentId = commentId,
            postId = postId,
        )
    }

    override suspend fun editComment(
        commentId: String,
        postId: String,
        text: String,
    ): RequestResult<Any> {
        return api.editComment(
            commentId = commentId,
            postId = postId,
            request = CommentRequest(
                text = text,
            )
        )
    }
}