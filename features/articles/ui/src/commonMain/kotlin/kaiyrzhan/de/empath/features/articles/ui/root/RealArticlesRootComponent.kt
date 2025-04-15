package kaiyrzhan.de.empath.features.articles.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.articles.ui.articleСreate.RealArticleCreateComponent
import kaiyrzhan.de.empath.features.articles.ui.articleDetail.RealArticleDetailComponent
import kaiyrzhan.de.empath.features.articles.ui.articleEdit.RealArticleEditComponent
import kaiyrzhan.de.empath.features.articles.ui.articles.RealArticlesComponent
import kotlinx.serialization.Serializable

public class RealArticlesRootComponent(
    componentContext: ComponentContext,
) : BaseComponent(componentContext), ArticlesRootComponent {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, ArticlesRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Articles,
        childFactory = ::createChild,
    )

    override fun onBackClick(): Unit = navigation.pop()

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): ArticlesRootComponent.Child {
        logger.d(this.className(), "Profile child: $config")
        return when (config) {
            is Config.Articles -> createArticlesComponent(componentContext)
            is Config.ArticleDetail -> createArticleDetailComponent(componentContext, config)
            is Config.ArticleCreate -> createArticleCreateComponent(componentContext)
            is Config.ArticleEdit -> createArticleEditComponent(componentContext, config)
        }
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createArticleDetailComponent(
        componentContext: ComponentContext,
        config: Config.ArticleDetail,
    ): ArticlesRootComponent.Child.ArticleDetail {
        return ArticlesRootComponent.Child.ArticleDetail(
            component = RealArticleDetailComponent(
                componentContext = componentContext,
                articleId = config.articleId,
                onBackClick = ::onBackClick,
                onArticleEditClick = {
                    navigation.push(
                        Config.ArticleEdit(config.articleId)
                    )
                },
            )
        )
    }


    private fun createArticleEditComponent(
        componentContext: ComponentContext,
        config: Config.ArticleEdit,
    ): ArticlesRootComponent.Child.ArticleEdit {
        return ArticlesRootComponent.Child.ArticleEdit(
            component = RealArticleEditComponent(
                componentContext = componentContext,
                articleId = config.articleId,
                onBackClick = ::onBackClick,
            )
        )
    }


    @OptIn(DelicateDecomposeApi::class)
    private fun createArticlesComponent(componentContext: ComponentContext): ArticlesRootComponent.Child.Articles {
        return ArticlesRootComponent.Child.Articles(
            component = RealArticlesComponent(
                componentContext = componentContext,
                onArticleClick = { articleId ->
                    navigation.push(
                        Config.ArticleDetail(articleId)
                    )
                },
                onArticleCreateClick = {
                    navigation.push(Config.ArticleCreate)
                },
                onArticleEditClick = { articleId ->
                    navigation.push(
                        Config.ArticleEdit(articleId)
                    )
                }
            )
        )
    }

    private fun createArticleCreateComponent(componentContext: ComponentContext): ArticlesRootComponent.Child.ArticleCreate {
        return ArticlesRootComponent.Child.ArticleCreate(
            component = RealArticleCreateComponent(
                componentContext = componentContext,
                onBackClick = ::onBackClick,
            )
        )
    }


    @Serializable
    private sealed interface Config {
        @Serializable
        data object Articles : Config

        @Serializable
        data class ArticleDetail(val articleId: String) : Config

        @Serializable
        data object ArticleCreate : Config

        @Serializable
        data class ArticleEdit(val articleId: String) : Config

    }
}