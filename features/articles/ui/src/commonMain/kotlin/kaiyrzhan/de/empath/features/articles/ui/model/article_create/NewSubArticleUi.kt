package kaiyrzhan.de.empath.features.articles.ui.model.article_create

import kaiyrzhan.de.empath.features.articles.domain.model.article_create.NewSubArticle
import kaiyrzhan.de.empath.features.articles.ui.model.ImageUi
import kaiyrzhan.de.empath.features.articles.ui.model.toDomain
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal data class NewSubArticleUi(
    val id: Uuid = Uuid.random(),
    val title: String,
    val description: String,
    val images: List<ImageUi>,
) {
    fun isChanged(): Boolean {
        return title.isNotBlank() || description.isNotBlank() || images.isNotEmpty()
    }

    companion object {
        fun create(): NewSubArticleUi {
            return NewSubArticleUi(
                title = "",
                description = "",
                images = emptyList(),
            )
        }
    }
}

internal fun NewSubArticleUi.toDomain(): NewSubArticle {
    return NewSubArticle(
        title = title,
        description = description,
        imageUrls = images.toDomain(),
    )
}


