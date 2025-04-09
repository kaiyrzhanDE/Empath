package kaiyrzhan.de.empath.features.profile.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.features.profile.ui.profile.RealProfileComponent
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileEvent
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.RealProfileEditComponent
import kotlinx.serialization.Serializable

public class RealProfileRootComponent(
    componentContext: ComponentContext,
    private val onLogOutClick: () -> Unit,
) : BaseComponent(componentContext), ProfileRootComponent {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, ProfileRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Profile,
        childFactory = ::createChild,
    )

    override fun onBackClick(): Unit = navigation.pop()

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): ProfileRootComponent.Child {
        logger.d(this.className(), "Profile child: $config")
        return when (config) {
            is Config.Profile -> createProfileComponent(componentContext)
            is Config.ProfileEdit -> createProfileEditComponent(componentContext)
        }
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun createProfileComponent(componentContext: ComponentContext): ProfileRootComponent.Child.Profile {
        return ProfileRootComponent.Child.Profile(
            RealProfileComponent(
                componentContext = componentContext,
                onLogOutClick = onLogOutClick,
                onUserPageClick = {
//                    TODO("Need implementation")
                },
                onProfileEditClick = { navigation.push(Config.ProfileEdit) },
            )
        )
    }

    private fun createProfileEditComponent(componentContext: ComponentContext): ProfileRootComponent.Child.ProfileEdit {
        return ProfileRootComponent.Child.ProfileEdit(
            RealProfileEditComponent(
                componentContext = componentContext,
                onBackClick = { isProfileEdited ->
                    if (isProfileEdited) {
                        reloadProfile()
                    } else {
                        onBackClick()
                    }
                },
            )
        )
    }

    private fun reloadProfile() {
        navigation.pop {
            (stack.active.instance as? ProfileRootComponent.Child.Profile)
                ?.component
                ?.onEvent(ProfileEvent.Reload)
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Profile : Config

        @Serializable
        data object ProfileEdit : Config
    }
}