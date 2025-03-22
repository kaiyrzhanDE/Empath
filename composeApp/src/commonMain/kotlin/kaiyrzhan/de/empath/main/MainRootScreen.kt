package kaiyrzhan.de.empath.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kaiyrzhan.de.empath.core.ui.animations.swipe
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.profile.ui.root.ProfileRootScreen
import kaiyrzhan.de.empath.main.MainRootComponent.Child
import kaiyrzhan.de.empath.main.components.NavigationBar
import kaiyrzhan.de.empath.main.components.NavigationRail

@OptIn(ExperimentalDecomposeApi::class)
@Composable
public fun MainRootScreen(
    component: MainRootComponent,
    modifier: Modifier = Modifier,
) {
    val currentWindowInfo = currentWindowAdaptiveInfo()

    val stack by component.stack.subscribeAsState()
    val currentChild = stack.active.instance

    if (currentWindowInfo.isPhone()) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MainScreen(
                component = component,
                modifier = Modifier.weight(1f),
                animation = predictiveBackAnimation(
                    backHandler = component.backHandler,
                    onBack = component::onBackClick,
                    fallbackAnimation = stackAnimation(fade()),
                ),
            )
            HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
            NavigationBar(
                currentChild = currentChild,
                component = component,
            )
        }
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavigationRail(
                component = component,
                currentChild = currentChild,
            )
            MainScreen(
                component = component,
                modifier = Modifier.weight(1f),
                animation = predictiveBackAnimation(
                    backHandler = component.backHandler,
                    onBack = component::onBackClick,
                    fallbackAnimation = stackAnimation(fade()),
                ),
            )
        }
    }
}

@Composable
private fun MainScreen(
    component: MainRootComponent,
    modifier: Modifier = Modifier,
    animation: StackAnimation<Any, Any>? = null,
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = animation,
    ) { child ->
        when (val instance = child.instance) {
            is Child.Profile -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(EmpathTheme.colors.scrim),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Profile")
                }
            }
            is Child.Study -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(EmpathTheme.colors.scrim),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Study")
                }
            }

            is Child.Menu -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(EmpathTheme.colors.scrim),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Menu")
                }
            }

            is Child.Analytics -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(EmpathTheme.colors.scrim),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Analytics")
                }
            }

            is Child.Vacancies -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(EmpathTheme.colors.scrim),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Vacancies")
                }
            }
        }
    }
}
