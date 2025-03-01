package kaiyrzhan.de.empath.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.auth.ui.root.AuthComponent

public interface RootComponent : BackHandlerOwner {

    public val stack: Value<ChildStack<*, Child>>

    public fun onBackClicked()

    public sealed class Child {
        public class Auth(public val component: AuthComponent) : Child()
    }
}
