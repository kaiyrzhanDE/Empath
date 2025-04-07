package kaiyrzhan.de.empath.features.profile.ui.root

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
import kaiyrzhan.de.empath.features.profile.ui.profile.ProfileScreen
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.ProfileEditScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
public fun ProfileRootScreen(
    component: ProfileRootComponent,
    modifier: Modifier = Modifier,
) {
    val currentWindowInfo = currentWindowAdaptiveInfo()
    if (currentWindowInfo.isPhone()) {
        ProfileRootScreen(
            modifier = modifier,
            component = component,
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = component::onBackClick,
                fallbackAnimation = stackAnimation(swipe()),
            ),
        )
    } else {
        ProfileRootScreen(
            modifier = modifier,
            component = component,
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = component::onBackClick,
                fallbackAnimation = stackAnimation(fade()),
            ),
        )
    }
}

@Composable
private fun ProfileRootScreen(
    modifier: Modifier = Modifier,
    component: ProfileRootComponent,
    animation: StackAnimation<Any, Any>? = null,
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = animation,
    ) { child ->
        when (val instance = child.instance) {
            is ProfileRootComponent.Child.Profile -> {
                ProfileScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is ProfileRootComponent.Child.ProfileEdit -> {
                ProfileEditScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

