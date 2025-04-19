package kaiyrzhan.de.empath.features.posts.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import kaiyrzhan.de.empath.core.ui.animations.swipe
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.features.posts.ui.postCreate.PostCreateScreen
import kaiyrzhan.de.empath.features.posts.ui.postDetail.PostDetailScreen
import kaiyrzhan.de.empath.features.posts.ui.postEdit.PostEditScreen
import kaiyrzhan.de.empath.features.posts.ui.posts.PostsScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
public fun PostsRootScreen(
    modifier: Modifier = Modifier,
    component: PostsRootComponent,
) {
    val currentWindowInfo = currentWindowAdaptiveInfo()

    if (currentWindowInfo.isPhone()) {
        PostsRootScreen(
            component = component,
            modifier = modifier,
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = component::onBackClick,
                fallbackAnimation = stackAnimation(swipe()),
            ),
        )
    } else {
        PostsRootScreen(
            component = component,
            modifier = modifier,
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = component::onBackClick,
                fallbackAnimation = stackAnimation(fade()),
            ),
        )
    }
}

@Composable
private fun PostsRootScreen(
    modifier: Modifier = Modifier,
    component: PostsRootComponent,
    animation: StackAnimation<Any, Any>? = null,
) {
    Children(
        modifier = modifier,
        stack = component.stack,
        animation = animation,
    ) { child ->
        when (val instance = child.instance) {
            is PostsRootComponent.Child.Posts -> {
                PostsScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is PostsRootComponent.Child.PostDetail -> {
                PostDetailScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is PostsRootComponent.Child.PostCreate -> {
                PostCreateScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is PostsRootComponent.Child.PostEdit -> {
                PostEditScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}