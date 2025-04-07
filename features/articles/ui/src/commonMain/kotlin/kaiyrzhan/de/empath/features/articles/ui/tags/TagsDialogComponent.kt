package kaiyrzhan.de.empath.features.articles.ui.tags

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.articles.ui.model.TagUi
import kaiyrzhan.de.empath.features.articles.ui.tags.model.TagsEvent
import kaiyrzhan.de.empath.features.articles.ui.tags.model.TagsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface TagsDialogComponent {

    val state: StateFlow<TagsState>

    val tags: Flow<PagingData<TagUi>>

    fun onEvent(event: TagsEvent)

    companion object {
        const val DEFAULT_KEY: String = "tags"
    }
}