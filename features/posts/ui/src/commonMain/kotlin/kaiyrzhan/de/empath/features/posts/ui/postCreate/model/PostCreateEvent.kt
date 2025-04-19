package kaiyrzhan.de.empath.features.posts.ui.postCreate.model

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.features.posts.ui.model.ImageUi
import kaiyrzhan.de.empath.features.posts.ui.model.TagUi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal sealed interface PostCreateEvent {
    data class PostTitleChange(val title: String) : PostCreateEvent
    data class PostDescriptionChange(val description: String) : PostCreateEvent
    data object TagAddClick : PostCreateEvent
    data class TagsAdded(val tags: List<TagUi>) : PostCreateEvent
    data class TagRemove(val tag: TagUi) : PostCreateEvent
    data class PostImagesAdd(val files: List<PlatformFile>) : PostCreateEvent
    data class PostImageRemove(val image: ImageUi) : PostCreateEvent
    data object SubPostAdd : PostCreateEvent
    data class SubPostRemove(val id: Uuid) : PostCreateEvent
    data object PostCreate : PostCreateEvent
    data object PostClear : PostCreateEvent
    data object BackClick : PostCreateEvent
    data object LoadUser : PostCreateEvent

    data class SubPostTitleChange(
        val id: Uuid,
        val title: String,
    ) : PostCreateEvent

    data class SubPostDescriptionChange(
        val id: Uuid,
        val description: String,
    ) : PostCreateEvent

    data class SubPostImageAdd(
        val id: Uuid,
        val files: List<PlatformFile>,
    ) : PostCreateEvent

    data class SubPostImageRemove(
        val id: Uuid,
        val image: ImageUi,
    ) : PostCreateEvent
}