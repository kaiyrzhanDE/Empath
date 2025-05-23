package kaiyrzhan.de.empath.features.profile.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.profile.ui.profile.ProfileComponent
import kaiyrzhan.de.empath.features.profile.ui.profileEdit.ProfileEditComponent

public interface ProfileRootComponent : BackHandlerOwner {

    public val stack: Value<ChildStack<*, Child>>

    public fun onBackClick()

    public sealed class Child {
        internal class Profile(val component: ProfileComponent) : Child()
        internal class ProfileEdit(val component: ProfileEditComponent) : Child()
    }
}