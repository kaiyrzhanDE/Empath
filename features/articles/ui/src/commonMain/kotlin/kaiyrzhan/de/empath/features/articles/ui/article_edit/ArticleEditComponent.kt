package kaiyrzhan.de.empath.features.articles.ui.article_edit

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.articles.ui.article_create.model.ArticleCreateAction
import kaiyrzhan.de.empath.features.articles.ui.article_create.model.ArticleCreateEvent
import kaiyrzhan.de.empath.features.articles.ui.article_create.model.ArticleCreateState
import kaiyrzhan.de.empath.features.articles.ui.article_edit.model.ArticleEditAction
import kaiyrzhan.de.empath.features.articles.ui.article_edit.model.ArticleEditEvent
import kaiyrzhan.de.empath.features.articles.ui.article_edit.model.ArticleEditState
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