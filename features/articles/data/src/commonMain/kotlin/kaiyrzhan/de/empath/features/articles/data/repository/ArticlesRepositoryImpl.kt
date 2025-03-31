package kaiyrzhan.de.empath.features.articles.data.repository

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.pagination.map
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.toDomain
import kaiyrzhan.de.empath.features.articles.data.model.*
import kaiyrzhan.de.empath.features.articles.data.remote.ArticlesApi
import kaiyrzhan.de.empath.features.articles.domain.model.*
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository

internal class ArticlesRepositoryImpl(
    private val api: ArticlesApi,
) : ArticlesRepository {

    override suspend fun getArticles(
        page: Int,
        search: String,
    ): RequestResult<ListResult<Article>> {
        return api.getArticles(
            page = page,
            search = search,
        ).toDomain { articles ->
            articles.map { article -> article.toDomain() }
        }
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
        article: Article,
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
        page: Int,
        name: String,
    ): RequestResult<ListResult<Tag>> {
        return api.getTags(
            page = page,
            name = name,
        ).toDomain { tags ->
            tags.map { tag -> tag.toDomain() }
        }
    }

    override suspend fun getComments(
        page: Int,
        articleId: String,
    ): RequestResult<ListResult<Comment>> {
        return api.getComments(
            page = page,
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