package kaiyrzhan.de.empath.features.articles.domain.repository

import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.articles.domain.model.Article
import kaiyrzhan.de.empath.features.articles.domain.model.Comment
import kaiyrzhan.de.empath.features.articles.domain.model.article_edit.EditedArticle
import kaiyrzhan.de.empath.features.articles.domain.model.article_create.NewArticle
import kaiyrzhan.de.empath.features.articles.domain.model.Tag
import kotlinx.coroutines.flow.Flow

public interface ArticlesRepository {

    public fun getArticles(
        query: String?,
    ): Flow<PagingData<Article>>

    public suspend fun getArticle(
        id: String,
    ): RequestResult<Article>

    public suspend fun createArticle(
        newArticle: NewArticle,
    ): RequestResult<Any>

    public suspend fun editArticle(
        articleId: String,
        article: EditedArticle,
    ): RequestResult<Any>

    public suspend fun deleteArticle(id: String): RequestResult<Any>

    public suspend fun getTags(
        query: String,
    ):  Flow<PagingData<Tag>>

    public suspend fun getComments(
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