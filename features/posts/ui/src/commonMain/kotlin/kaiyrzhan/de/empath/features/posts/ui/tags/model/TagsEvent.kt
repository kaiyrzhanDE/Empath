package kaiyrzhan.de.empath.features.posts.ui.tags.model

import kaiyrzhan.de.empath.features.posts.ui.model.TagUi

internal sealed interface TagsEvent {
    data class TagSelect(val tag: TagUi) : TagsEvent
    data class TagRemove(val tag: TagUi) : TagsEvent
    data object TagCreate : TagsEvent
    data class Search(val query: String) : TagsEvent
    data object TagsSelectClick : TagsEvent
    data object DismissClick : TagsEvent
}