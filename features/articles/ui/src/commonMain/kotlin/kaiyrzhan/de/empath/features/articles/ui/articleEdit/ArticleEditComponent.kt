package kaiyrzhan.de.empath.features.articles.ui.articleEdit

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.articles.ui.articleEdit.model.ArticleEditAction
import kaiyrzhan.de.empath.features.articles.ui.articleEdit.model.ArticleEditEvent
import kaiyrzhan.de.empath.features.articles.ui.articleEdit.model.ArticleEditState
import kaiyrzhan.de.empath.features.articles.ui.tags.TagsDialogComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface ArticleEditComponent: BackHandlerOwner {

    val state: StateFlow<ArticleEditState>

    val action: Flow<ArticleEditAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    val tagsDialog: Value<ChildSlot<*, TagsDialogComponent>>

    fun onEvent(event: ArticleEditEvent)

}