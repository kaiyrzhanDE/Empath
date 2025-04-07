package kaiyrzhan.de.empath.features.articles.ui.article_edit.model

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.features.articles.ui.model.ImageUi
import kaiyrzhan.de.empath.features.articles.ui.model.TagUi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal sealed interface ArticleEditEvent {
    data class ArticleTitleChange(val title: String) : ArticleEditEvent
    data class ArticleDescriptionChange(val description: String) : ArticleEditEvent
    data object TagAddClick : ArticleEditEvent
    data class TagsAdded(val tags: List<TagUi>) : ArticleEditEvent
    data class TagRemove(val tag: TagUi) : ArticleEditEvent
    data class ArticleImagesAdd(val files: List<PlatformFile>) : ArticleEditEvent
    data class ArticleImageRemove(val image: ImageUi) : ArticleEditEvent
    data object SubArticleAdd : ArticleEditEvent
    data class SubArticleRemove(val id: Uuid) : ArticleEditEvent
    data object ArticleEdit : ArticleEditEvent
    data object ArticleRevert : ArticleEditEvent
    data object LoadArticle : ArticleEditEvent
    data object BackClick : ArticleEditEvent

    data class SubArticleTitleChange(
        val id: Uuid,
        val title: String,
    ) : ArticleEditEvent

    data class SubArticleDescriptionChange(
        val id: Uuid,
        val description: String,
    ) : ArticleEditEvent

    data class SubArticleImageAdd(
        val id: Uuid,
        val files: List<PlatformFile>,
    ) : ArticleEditEvent

    data class SubArticleImageRemove(
        val id: Uuid,
        val image: ImageUi,
    ) : ArticleEditEvent
}