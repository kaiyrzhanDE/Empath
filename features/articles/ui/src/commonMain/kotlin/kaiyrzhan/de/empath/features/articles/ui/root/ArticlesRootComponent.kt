package kaiyrzhan.de.empath.features.articles.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.articles.ui.articleCreate.ArticleCreateComponent
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.ArticleDetailComponent
import kaiyrzhan.de.empath.features.articles.ui.articleEdit.ArticleEditComponent
import kaiyrzhan.de.empath.features.articles.ui.articles.ArticlesComponent

public interface ArticlesRootComponent : BackHandlerOwner {
    public val stack: Value<ChildStack<*, Child>>

    public fun onBackClick()

    public sealed class Child {
        internal class Articles(val component: ArticlesComponent) : Child()
        internal class ArticleDetail(val component: ArticleDetailComponent) : Child()
        internal class ArticleCreate(val component: ArticleCreateComponent) : Child()
        internal class ArticleEdit(val component: ArticleEditComponent) : Child()
    }
}