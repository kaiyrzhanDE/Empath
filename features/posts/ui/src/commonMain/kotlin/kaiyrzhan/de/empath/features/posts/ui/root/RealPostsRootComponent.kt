package kaiyrzhan.de.empath.features.posts.ui.root

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
import kaiyrzhan.de.empath.features.posts.ui.postCreate.RealPostCreateComponent
import kaiyrzhan.de.empath.features.posts.ui.postDetail.RealPostDetailComponent
import kaiyrzhan.de.empath.features.posts.ui.postEdit.RealPostEditComponent
import kaiyrzhan.de.empath.features.posts.ui.posts.RealPostsComponent
import kotlinx.serialization.Serializable

public class RealPostsRootComponent(
    componentContext: ComponentContext,
) : BaseComponent(componentContext), PostsRootComponent {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, PostsRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Posts,
        childFactory = ::createChild,
    )

    override fun onBackClick(): Unit = navigation.pop()

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): PostsRootComponent.Child {
        logger.d(this.className(), "Profile child: $config")
        return when (config) {
            is Config.Posts -> createPostsComponent(componentContext)
            is Config.PostDetail -> createPostDetailComponent(componentContext, config)
            is Config.PostCreate -> createPostCreateComponent(componentContext)
            is Config.PostEdit -> createPostEditComponent(componentContext, config)
        }
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createPostDetailComponent(
        componentContext: ComponentContext,
        config: Config.PostDetail,
    ): PostsRootComponent.Child.PostDetail {
        return PostsRootComponent.Child.PostDetail(
            component = RealPostDetailComponent(
                componentContext = componentContext,
                postId = config.postId,
                onBackClick = ::onBackClick,
                onPostEditClick = {
                    navigation.push(
                        Config.PostEdit(config.postId)
                    )
                },
            )
        )
    }


    private fun createPostEditComponent(
        componentContext: ComponentContext,
        config: Config.PostEdit,
    ): PostsRootComponent.Child.PostEdit {
        return PostsRootComponent.Child.PostEdit(
            component = RealPostEditComponent(
                componentContext = componentContext,
                postId = config.postId,
                onBackClick = ::onBackClick,
            )
        )
    }


    @OptIn(DelicateDecomposeApi::class)
    private fun createPostsComponent(componentContext: ComponentContext): PostsRootComponent.Child.Posts {
        return PostsRootComponent.Child.Posts(
            component = RealPostsComponent(
                componentContext = componentContext,
                onPostClick = { postId ->
                    navigation.push(
                        Config.PostDetail(postId)
                    )
                },
                onPostCreateClick = {
                    navigation.push(Config.PostCreate)
                },
                onPostEditClick = { postId ->
                    navigation.push(
                        Config.PostEdit(postId)
                    )
                }
            )
        )
    }

    private fun createPostCreateComponent(componentContext: ComponentContext): PostsRootComponent.Child.PostCreate {
        return PostsRootComponent.Child.PostCreate(
            component = RealPostCreateComponent(
                componentContext = componentContext,
                onBackClick = ::onBackClick,
            )
        )
    }


    @Serializable
    private sealed interface Config {
        @Serializable
        data object Posts : Config

        @Serializable
        data class PostDetail(val postId: String) : Config

        @Serializable
        data object PostCreate : Config

        @Serializable
        data class PostEdit(val postId: String) : Config

    }
}