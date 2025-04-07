package kaiyrzhan.de.empath.features.articles.ui.model

import kaiyrzhan.de.empath.features.articles.domain.model.SubArticle
import kotlin.uuid.ExperimentalUuidApi

internal data class SubArticleUi(
    val id: String,
    val title: String,
    val description: String,
    val imageUrls: List<String>,
)

@OptIn(ExperimentalUuidApi::class)
internal fun SubArticle.toUi(): SubArticleUi {
    return SubArticleUi(
        id = id,
        title = title,
        description = description,
        imageUrls = imageUrls,
    )
}

@OptIn(ExperimentalUuidApi::class)
internal fun SubArticleUi.toDomain(): SubArticle {
    return SubArticle(
        id = id,
        title = title,
        description = description,
        imageUrls = imageUrls,
    )
}
