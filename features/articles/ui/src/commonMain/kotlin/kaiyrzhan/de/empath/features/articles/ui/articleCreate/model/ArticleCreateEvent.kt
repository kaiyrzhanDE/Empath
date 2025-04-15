package kaiyrzhan.de.empath.features.articles.ui.articleCreate.model

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.features.articles.ui.model.ImageUi
import kaiyrzhan.de.empath.features.articles.ui.model.TagUi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal sealed interface ArticleCreateEvent {
    data class ArticleTitleChange(val title: String) : ArticleCreateEvent
    data class ArticleDescriptionChange(val description: String) : ArticleCreateEvent
    data object TagAddClick : ArticleCreateEvent
    data class TagsAdded(val tags: List<TagUi>) : ArticleCreateEvent
    data class TagRemove(val tag: TagUi) : ArticleCreateEvent
    data class ArticleImagesAdd(val files: List<PlatformFile>) : ArticleCreateEvent
    data class ArticleImageRemove(val image: ImageUi) : ArticleCreateEvent
    data object SubArticleAdd : ArticleCreateEvent
    data class SubArticleRemove(val id: Uuid) : ArticleCreateEvent
    data object ArticleCreate : ArticleCreateEvent
    data object ArticleClear : ArticleCreateEvent
    data object BackClick : ArticleCreateEvent
    data object LoadUser : ArticleCreateEvent

    data class SubArticleTitleChange(
        val id: Uuid,
        val title: String,
    ) : ArticleCreateEvent

    data class SubArticleDescriptionChange(
        val id: Uuid,
        val description: String,
    ) : ArticleCreateEvent

    data class SubArticleImageAdd(
        val id: Uuid,
        val files: List<PlatformFile>,
    ) : ArticleCreateEvent

    data class SubArticleImageRemove(
        val id: Uuid,
        val image: ImageUi,
    ) : ArticleCreateEvent
}