package kaiyrzhan.de.empath.features.posts.data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import kaiyrzhan.de.empath.core.network.utils.ApiVersion
import kaiyrzhan.de.empath.core.utils.pagination.ListResultDTO
import kaiyrzhan.de.empath.core.utils.pagination.PaginationUtils
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.posts.data.model.PostDTO
import kaiyrzhan.de.empath.features.posts.data.model.PostRequest
import kaiyrzhan.de.empath.features.posts.data.model.CommentDTO
import kaiyrzhan.de.empath.features.posts.data.model.CommentRequest
import kaiyrzhan.de.empath.features.posts.data.model.TagDTO

internal interface PostsApi {

    @GET("api/{version}/articles")
    suspend fun getPosts(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("page") page: Int,
        @Query("per_page") pageLimit: Int,
        @Query("search") query: String? = null,
    ): RequestResult<ListResultDTO<PostDTO>>

    @GET("api/{version}/articles/{article_id}")
    suspend fun getPost(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("article_id") id: String,
    ): RequestResult<PostDTO>


    @POST("api/{version}/articles")
    suspend fun createPost(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Body request: PostRequest,
    ): RequestResult<Any>


    @DELETE("api/{version}/articles/{article_id}")
    suspend fun deletePost(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("article_id") id: String,
    ): RequestResult<Any>

    @PATCH("api/{version}/articles/{article_id}")
    suspend fun editPost(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("article_id") id: String,
        @Body request: PostRequest,
    ): RequestResult<Any>

    @GET("api/{version}/articles/tags")
    suspend fun getTags(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("page") page: Int,
        @Query("per_page") pageLimit: Int = PaginationUtils.PAGE_LIMIT_NORMAL,
        @Query("name") query: String? = null,
    ): RequestResult<ListResultDTO<TagDTO>>

    @GET("api/{version}/articles/{article_id}/comments")
    suspend fun getComments(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("article_id") postId: String,
        @Query("page") page: Int = 1,
        @Query("per_page") pageLimit: Int = PaginationUtils.PAGE_LIMIT_EXTRA_LARGE,
    ): RequestResult<ListResultDTO<CommentDTO>>

    @POST("api/{version}/articles/{article_id}/comments")
    suspend fun createComment(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("article_id") postId: String,
        @Body request: CommentRequest,
    ): RequestResult<Any>

    @DELETE("api/{version}/articles/{article_id}/comments/{comment_id}")
    suspend fun deleteComment(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("comment_id") commentId: String,
        @Path("article_id") postId: String,
    ): RequestResult<Any>

    @PUT("api/{version}/articles/{article_id}/comments/{comment_id}")
    suspend fun editComment(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("comment_id") commentId: String,
        @Path("article_id") postId: String,
        @Body request: CommentRequest,
    ): RequestResult<Any>
}