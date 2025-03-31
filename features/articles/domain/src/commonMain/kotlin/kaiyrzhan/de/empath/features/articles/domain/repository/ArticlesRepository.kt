package kaiyrzhan.de.empath.features.articles.domain.repository

import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.articles.domain.model.Article
import kaiyrzhan.de.empath.features.articles.domain.model.Comment
import kaiyrzhan.de.empath.features.articles.domain.model.NewArticle
import kaiyrzhan.de.empath.features.articles.domain.model.Tag

public interface ArticlesRepository {

    public suspend fun getArticles(
        page: Int,
        search: String,
    ): RequestResult<ListResult<Article>>

    public suspend fun createArticle(
        newArticle: NewArticle,
    ): RequestResult<Any>

    public suspend fun editArticle(
        articleId: String,
        article: Article,
    ): RequestResult<Any>

    public suspend fun deleteArticle(id: String): RequestResult<Any>

    public suspend fun getTags(
        page: Int,
        name: String,
    ): RequestResult<ListResult<Tag>>

    public suspend fun getComments(
        page: Int,
        articleId: String,
    ): RequestResult<ListResult<Comment>>

    public suspend fun createComment(
        text: String,
        articleId: String,
    ): RequestResult<Any>

    public suspend fun deleteComment(
        commentId: String,
        articleId: String,
    ): RequestResult<Any>

    public suspend fun editComment(
        commentId: String,
        articleId: String,
        text: String,
    ): RequestResult<Any>
}