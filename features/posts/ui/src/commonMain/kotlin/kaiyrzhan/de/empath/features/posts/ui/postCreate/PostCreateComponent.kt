package kaiyrzhan.de.empath.features.posts.ui.postCreate

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.posts.ui.postCreate.model.PostCreateAction
import kaiyrzhan.de.empath.features.posts.ui.postCreate.model.PostCreateEvent
import kaiyrzhan.de.empath.features.posts.ui.postCreate.model.PostCreateState
import kaiyrzhan.de.empath.features.posts.ui.tags.TagsDialogComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface PostCreateComponent: BackHandlerOwner {

    val state: StateFlow<PostCreateState>

    val action: Flow<PostCreateAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    val tagsDialog: Value<ChildSlot<*, TagsDialogComponent>>

    fun onEvent(event: PostCreateEvent)

}