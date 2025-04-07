package kaiyrzhan.de.empath.features.articles.ui.model.article_edit

import kaiyrzhan.de.empath.features.articles.domain.model.SubArticle
import kaiyrzhan.de.empath.features.articles.domain.model.article_edit.EditedSubArticle
import kaiyrzhan.de.empath.features.articles.ui.model.ImageUi
import kaiyrzhan.de.empath.features.articles.ui.model.SubArticleUi
import kaiyrzhan.de.empath.features.articles.ui.model.toDomain
import kaiyrzhan.de.empath.features.articles.ui.model.toUi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal data class EditedSubArticleUi(
    val id: Uuid = Uuid.random(),
    val subArticleId: String?,
    val title: String,
    val description: String,
    val images: List<ImageUi>,
) {
    fun isChanged(): Boolean {
        return title.isNotBlank() || description.isNotBlank() || images.isNotEmpty()
    }

    companion object {
        fun create(): EditedSubArticleUi {
            return EditedSubArticleUi(
                subArticleId = null,
                title = "",
                description = "",
                images = emptyList(),
            )
        }
    }
}

internal fun EditedSubArticleUi.toDomain(): EditedSubArticle {
    return EditedSubArticle(
        id = subArticleId,
        title = title,
        description = description,
        imageUrls = images.toDomain(),
    )
}

@OptIn(ExperimentalUuidApi::class)
internal fun SubArticle.toEdit(): EditedSubArticleUi {
    return EditedSubArticleUi(
        subArticleId = id,
        title = title,
        description = description,
        images = imageUrls.toUi(),
    )
}

