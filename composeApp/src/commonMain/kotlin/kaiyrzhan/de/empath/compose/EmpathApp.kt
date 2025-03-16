package kaiyrzhan.de.empath.compose

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.auth.ui.root.AuthScreen
import kaiyrzhan.de.empath.root.RootComponent

@OptIn(ExperimentalDecomposeApi::class, ExperimentalMaterial3Api::class)
@Composable
public fun EmpathApp(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    EmpathTheme(
        snackbarHostState = snackbarHostState,
    ) {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState){ data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = EmpathTheme.colors.surfaceContainer,
                        contentColor = EmpathTheme.colors.onSurface,
                        actionContentColor = EmpathTheme.colors.onPrimary,
                        actionColor = EmpathTheme.colors.primary,
                    )
                }
            },
            containerColor = EmpathTheme.colors.surface,
        ) { contentPadding ->
            Children(
                stack = component.stack,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                animation = predictiveBackAnimation(
                    backHandler = component.backHandler,
                    onBack = { component.onBackClicked() },
                    fallbackAnimation = stackAnimation(scale()),
                ),
            ) { child ->
                when (val instance = child.instance) {
                    is RootComponent.Child.Auth -> AuthScreen(instance.component)
                }
            }
        }
    }
}

