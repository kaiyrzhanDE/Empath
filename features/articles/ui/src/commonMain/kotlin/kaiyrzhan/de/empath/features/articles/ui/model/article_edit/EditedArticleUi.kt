package kaiyrzhan.de.empath.features.articles.ui.model.article_edit

import kaiyrzhan.de.empath.features.articles.domain.model.Article
import kaiyrzhan.de.empath.features.articles.domain.model.article_edit.EditedArticle
import kaiyrzhan.de.empath.features.articles.ui.model.ImageUi
import kaiyrzhan.de.empath.features.articles.ui.model.TagUi
import kaiyrzhan.de.empath.features.articles.ui.model.AuthorUi
import kaiyrzhan.de.empath.features.articles.ui.model.toDomain
import kaiyrzhan.de.empath.features.articles.ui.model.toUi

//import kaiyrzhan.de.empath.features.articles.ui.model.toUi

internal data class EditedArticleUi(
    val id: String,
    val title: String,
    val description: String,
    val isVisible: Boolean,
    val images: List<ImageUi>,
    val tags: List<TagUi>,
    val subArticles: List<EditedSubArticleUi>,
    val viewsCount: Int,
    val likesCount: Int,
    val isViewed: Boolean = false,
    val isLiked: Boolean = false,
    val dislikesCount: Int,
    val author: AuthorUi,
) {
    fun isChanged(): Boolean {
        return title.isNotBlank() ||
                description.isNotBlank() ||
                images.isNotEmpty() ||
                tags.isNotEmpty() ||
                subArticles.any { subArticle -> subArticle.isChanged() }
    }
}

internal fun Article.toEdit(): EditedArticleUi {
    return EditedArticleUi(
        id = id,
        title = title,
        description = description,
        isVisible = isVisible,
        images = imageUrls.toUi(),
        tags = tags.map { tag -> tag.toUi() },
        subArticles = subArticles.map { subArticle -> subArticle.toEdit() },
        author = author.toUi(),
        dislikesCount = dislikesCount,
        likesCount = likesCount,
        viewsCount = viewsCount,
    )
}

internal fun EditedArticleUi.toDomain(): EditedArticle {
    return EditedArticle(
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = images.toDomain(),
        id = id,
        tags = tags.map { tag -> tag.toDomain() },
        subArticles = subArticles.map { subArticle -> subArticle.toDomain() },
        viewsCount = viewsCount,
        likesCount = likesCount,
        dislikesCount = dislikesCount,
        author = author.toDomain(),
    )
}
