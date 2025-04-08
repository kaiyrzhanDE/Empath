package kaiyrzhan.de.empath.features.articles.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.pagination.PaginationUtils
import kaiyrzhan.de.empath.core.utils.pagination.map
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.toDomain
import kaiyrzhan.de.empath.features.articles.data.model.*
import kaiyrzhan.de.empath.features.articles.data.pagingSource.ArticlesPagingSource
import kaiyrzhan.de.empath.features.articles.data.pagingSource.TagsPagingSource
import kaiyrzhan.de.empath.features.articles.data.remote.ArticlesApi
import kaiyrzhan.de.empath.features.articles.domain.model.*
import kaiyrzhan.de.empath.features.articles.domain.model.article_create.NewArticle
import kaiyrzhan.de.empath.features.articles.domain.model.article_edit.EditedArticle
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow

internal class ArticlesRepositoryImpl(
    private val api: ArticlesApi,
) : ArticlesRepository {

    override fun getArticles(
        query: String?,
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PaginationUtils.PAGE_LIMIT_NORMAL,
                prefetchDistance = 3,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                ArticlesPagingSource(
                    api = api,
                    query = query,
                )
            }
        ).flow
    }

    override suspend fun getArticle(
        id: String,
    ): RequestResult<Article> {
        return api.getArticle(
            id = id,
        ).toDomain { article -> article.toDomain() }
    }

    override suspend fun createArticle(
        newArticle: NewArticle,
    ): RequestResult<Any> {
        return api.createArticle(
            request = newArticle.toData(),
        )
    }

    override suspend fun editArticle(
        id: String,
        article: EditedArticle,
    ): RequestResult<Any> {
        return api.editArticle(
            id = id,
            request = article.toData(),
        )
    }

    override suspend fun deleteArticle(id: String): RequestResult<Any> {
        return api.deleteArticle(
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
        articleId: String,
    ): RequestResult<ListResult<Comment>> {
        return api.getComments(
            articleId = articleId,
        ).toDomain { comments ->
            comments.map { comment -> comment.toDomain() }
        }
    }

    override suspend fun createComment(
        text: String,
        articleId: String,
    ): RequestResult<Any> {
        return api.createComment(
            articleId = articleId,
            request = CommentRequest(
                text = text,
            ),
        )
    }

    override suspend fun deleteComment(
        commentId: String,
        articleId: String,
    ): RequestResult<Any> {
        return api.deleteComment(
            commentId = commentId,
            articleId = articleId,
        )
    }

    override suspend fun editComment(
        commentId: String,
        articleId: String,
        text: String,
    ): RequestResult<Any> {
        return api.editComment(
            commentId = commentId,
            articleId = articleId,
            request = CommentRequest(
                text = text,
            )
        )
    }
}