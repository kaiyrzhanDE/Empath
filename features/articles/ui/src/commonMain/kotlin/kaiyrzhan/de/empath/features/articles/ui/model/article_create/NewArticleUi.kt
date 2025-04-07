package kaiyrzhan.de.empath.features.articles.ui.model.article_create

import kaiyrzhan.de.empath.features.articles.domain.model.article_create.NewArticle
import kaiyrzhan.de.empath.features.articles.ui.model.ImageUi
import kaiyrzhan.de.empath.features.articles.ui.model.TagUi
import kaiyrzhan.de.empath.features.articles.ui.model.toDomain

internal data class NewArticleUi(
    val title: String,
    val description: String,
    val isVisible: Boolean,
    val images: List<ImageUi>,
    val tags: List<TagUi>,
    val subArticles: List<NewSubArticleUi>,
) {
    fun isChanged(): Boolean {
        return title.isNotBlank() ||
                description.isNotBlank() ||
                images.isNotEmpty() ||
                tags.isNotEmpty() ||
                subArticles.any { subArticle -> subArticle.isChanged() }
    }

    companion object {
        fun default(): NewArticleUi {
            return NewArticleUi(
                title = "",
                description = "",
                isVisible = true,
                images = emptyList(),
                tags = emptyList(),
                subArticles = listOf(NewSubArticleUi.create()),
            )
        }
    }
}

internal fun NewArticleUi.toDomain(): NewArticle {
    return NewArticle(
        title = title,
        description = description,
        isVisible = isVisible,
        imageUrls = images.toDomain(),
        tags = tags.map { tag -> tag.toDomain() },
        subArticles = subArticles.map { subArticle -> subArticle.toDomain() },
    )
}
