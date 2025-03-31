package kaiyrzhan.de.empath.features.articles.data.remote

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
import kaiyrzhan.de.empath.features.articles.data.model.ArticleDTO
import kaiyrzhan.de.empath.features.articles.data.model.ArticleRequest
import kaiyrzhan.de.empath.features.articles.data.model.CommentDTO
import kaiyrzhan.de.empath.features.articles.data.model.CommentRequest
import kaiyrzhan.de.empath.features.articles.data.model.TagDTO

internal interface ArticlesApi {

    @GET("api/{version}/articles")
    suspend fun getArticles(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("page") page: Int,
        @Query("per_page") pageLimit: Int = PaginationUtils.PAGE_LIMIT_NORMAL,
        @Query("search") search: String? = null,
    ): RequestResult<ListResultDTO<ArticleDTO>>

    @POST("api/{version}/articles")
    suspend fun createArticle(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Body request: ArticleRequest,
    ): RequestResult<Any>

    @DELETE("api/{version}/articles/{article_id}")
    suspend fun deleteArticle(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("article_id") id: String,
    ): RequestResult<Any>

    @PATCH("api/{version}/articles/{article_id}")
    suspend fun editArticle(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("article_id") id: String,
        @Body request: ArticleRequest,
    ): RequestResult<Any>

    @GET("api/{version}/articles/tags")
    suspend fun getTags(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("page") page: Int,
        @Query("per_page") pageLimit: Int = PaginationUtils.PAGE_LIMIT_NORMAL,
        @Query("name") name: String? = null,
    ): RequestResult<ListResultDTO<TagDTO>>

    @GET("api/{version}/articles/{article_id}/comments")
    suspend fun getComments(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("article_id") articleId: String,
        @Query("page") page: Int,
        @Query("per_page") pageLimit: Int = PaginationUtils.PAGE_LIMIT_NORMAL,
    ): RequestResult<ListResultDTO<CommentDTO>>

    @POST("api/{version}/articles/{article_id}/comments")
    suspend fun createComment(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("article_id") articleId: String,
        @Body request: CommentRequest,
    ): RequestResult<Any>

    @DELETE("api/{version}/articles/{article_id}/comments/{comment_id}")
    suspend fun deleteComment(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("comment_id") commentId: String,
        @Path("article_id") articleId: String,
    ): RequestResult<Any>

    @PUT("api/{version}/articles/{article_id}/comments/{comment_id}")
    suspend fun editComment(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("comment_id") commentId: String,
        @Path("article_id") articleId: String,
        @Body request: CommentRequest,
    ): RequestResult<Any>
}