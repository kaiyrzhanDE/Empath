package kaiyrzhan.de.empath.features.posts.ui.tags.model

import kaiyrzhan.de.empath.features.posts.ui.model.TagUi
import kotlinx.serialization.Serializable

@Serializable
internal data class TagsState(
    val query: String = "",
    val originalTags: List<TagUi>,
    val editableTags: List<TagUi> = originalTags,
) {
    fun isQueryValidLength(): Boolean {
        return query.length in 0..50
    }

    fun hasTag(): Boolean {
        return editableTags.any { tag -> tag.name == query }
    }
}