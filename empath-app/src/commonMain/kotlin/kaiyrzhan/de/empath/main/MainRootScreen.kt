package kaiyrzhan.de.empath.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.posts.ui.root.PostsRootScreen
import kaiyrzhan.de.empath.features.profile.ui.root.ProfileRootScreen
import kaiyrzhan.de.empath.features.vacancies.ui.employment.root.EmploymentRootScreen
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.root.RecruitmentRootScreen
import kaiyrzhan.de.empath.main.MainRootComponent.Child
import kaiyrzhan.de.empath.main.components.NavigationBar
import kaiyrzhan.de.empath.main.components.NavigationRail

@OptIn(ExperimentalDecomposeApi::class)
@Composable
public fun MainRootScreen(
    component: MainRootComponent,
    modifier: Modifier = Modifier,
) {
    val windowInfo = currentWindowAdaptiveInfo()
    val stack by component.stack.subscribeAsState()
    val currentChild = stack.active.instance
    val snackbarHostState = remember { SnackbarHostState() }

    if (windowInfo.isPhone()) {
        CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
            Scaffold(
                modifier = modifier,
                bottomBar = {
                    NavigationBar(
                        currentChild = currentChild,
                        component = component,
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState) { data ->
                        Snackbar(
                            snackbarData = data,
                        )
                    }
                },
            ) { contentPadding ->
                MainScreen(
                    component = component,
                    modifier = modifier
                        .padding(contentPadding),
                    animation = predictiveBackAnimation(
                        backHandler = component.backHandler,
                        onBack = component::onBackClick,
                        fallbackAnimation = stackAnimation(fade()),
                    ),
                )
            }
        }
    } else {
        Row(
            modifier = modifier,
        ) {
            NavigationRail(
                currentChild = currentChild,
                component = component,
            )
            MainScreen(
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
                ProfileRootScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is Child.Posts -> {
                PostsRootScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is Child.Menu -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(EmpathTheme.colors.surfaceDim),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Menu")
                }
            }

            is Child.Employment -> {
                EmploymentRootScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is Child.Recruitment -> {
                RecruitmentRootScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
