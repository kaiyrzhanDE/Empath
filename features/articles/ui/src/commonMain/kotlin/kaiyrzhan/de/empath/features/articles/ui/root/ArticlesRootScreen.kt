package kaiyrzhan.de.empath.features.articles.ui.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import kaiyrzhan.de.empath.core.ui.animations.swipe
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.features.articles.ui.article_create.ArticleCreateScreen
import kaiyrzhan.de.empath.features.articles.ui.article_detail.ArticleDetailScreen
import kaiyrzhan.de.empath.features.articles.ui.article_edit.ArticleEditScreen
import kaiyrzhan.de.empath.features.articles.ui.articles.ArticlesScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
public fun ArticlesRootScreen(
    modifier: Modifier = Modifier,
    component: ArticlesRootComponent,
) {
    val currentWindowInfo = currentWindowAdaptiveInfo()

    if (currentWindowInfo.isPhone()) {
        ArticlesRootScreen(
            component = component,
            modifier = modifier,
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = component::onBackClick,
                fallbackAnimation = stackAnimation(swipe()),
            ),
        )
    } else {
        ArticlesRootScreen(
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
private fun ArticlesRootScreen(
    modifier: Modifier = Modifier,
    component: ArticlesRootComponent,
    animation: StackAnimation<Any, Any>? = null,
) {
    Children(
        modifier = modifier,
        stack = component.stack,
        animation = animation,
    ) { child ->
        when (val instance = child.instance) {
            is ArticlesRootComponent.Child.Articles -> {
                ArticlesScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is ArticlesRootComponent.Child.ArticleDetail -> {
                ArticleDetailScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is ArticlesRootComponent.Child.ArticleCreate -> {
                ArticleCreateScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is ArticlesRootComponent.Child.ArticleEdit -> {
                ArticleEditScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}