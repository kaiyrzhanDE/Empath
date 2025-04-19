package kaiyrzhan.de.empath.features.posts.ui.postEdit.model

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.features.posts.ui.model.ImageUi
import kaiyrzhan.de.empath.features.posts.ui.model.TagUi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal sealed interface PostEditEvent {
    data class PostTitleChange(val title: String) : PostEditEvent
    data class PostDescriptionChange(val description: String) : PostEditEvent
    data object TagAddClick : PostEditEvent
    data class TagsAdded(val tags: List<TagUi>) : PostEditEvent
    data class TagRemove(val tag: TagUi) : PostEditEvent
    data class PostImagesAdd(val files: List<PlatformFile>) : PostEditEvent
    data class PostImageRemove(val image: ImageUi) : PostEditEvent
    data object SubPostAdd : PostEditEvent
    data class SubPostRemove(val id: Uuid) : PostEditEvent
    data object PostEdit : PostEditEvent
    data object PostRevert : PostEditEvent
    data object LoadPost : PostEditEvent
    data object BackClick : PostEditEvent

    data class SubPostTitleChange(
        val id: Uuid,
        val title: String,
    ) : PostEditEvent

    data class SubPostDescriptionChange(
        val id: Uuid,
        val description: String,
    ) : PostEditEvent

    data class SubPostImageAdd(
        val id: Uuid,
        val files: List<PlatformFile>,
    ) : PostEditEvent

    data class SubPostImageRemove(
        val id: Uuid,
        val image: ImageUi,
    ) : PostEditEvent
}