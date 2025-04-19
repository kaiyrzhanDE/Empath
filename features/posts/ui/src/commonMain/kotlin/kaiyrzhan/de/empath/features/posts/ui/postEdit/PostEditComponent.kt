package kaiyrzhan.de.empath.features.posts.ui.postEdit

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.posts.ui.postEdit.model.PostEditAction
import kaiyrzhan.de.empath.features.posts.ui.postEdit.model.PostEditEvent
import kaiyrzhan.de.empath.features.posts.ui.postEdit.model.PostEditState
import kaiyrzhan.de.empath.features.posts.ui.tags.TagsDialogComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface PostEditComponent: BackHandlerOwner {

    val state: StateFlow<PostEditState>

    val action: Flow<PostEditAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    val tagsDialog: Value<ChildSlot<*, TagsDialogComponent>>

    fun onEvent(event: PostEditEvent)

}