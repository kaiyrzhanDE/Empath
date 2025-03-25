package kaiyrzhan.de.empath.features.profile.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import kaiyrzhan.de.empath.features.profile.ui.profile.ProfileScreen
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.ProfileEditScreen

@Composable
public fun ProfileRootScreen(
    component: RootProfileComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.stack,
        modifier = modifier,
    ) { child ->
        when (val instance = child.instance) {
            is RootProfileComponent.Child.Profile -> {
                ProfileScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            is RootProfileComponent.Child.ProfileEdit -> {
                ProfileEditScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

