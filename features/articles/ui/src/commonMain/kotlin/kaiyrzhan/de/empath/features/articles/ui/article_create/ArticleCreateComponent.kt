package kaiyrzhan.de.empath.features.articles.ui.article_create

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.features.articles.ui.article_create.model.ArticleCreateAction
import kaiyrzhan.de.empath.features.articles.ui.article_create.model.ArticleCreateEvent
import kaiyrzhan.de.empath.features.articles.ui.article_create.model.ArticleCreateState
import kaiyrzhan.de.empath.features.articles.ui.tags.TagsDialogComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface ArticleCreateComponent: BackHandlerOwner {

    val state: StateFlow<ArticleCreateState>

    val action: Flow<ArticleCreateAction>

    val messageDialog: Value<ChildSlot<*, MessageDialogComponent>>

    val tagsDialog: Value<ChildSlot<*, TagsDialogComponent>>

    fun onEvent(event: ArticleCreateEvent)

}