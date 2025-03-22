package kaiyrzhan.de.empath.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.auth.ui.root.AuthRootComponent
import kaiyrzhan.de.empath.main.MainRootComponent

public interface RootComponent : BackHandlerOwner {

    public val stack: Value<ChildStack<*, Child>>

    public fun onBackClick()

    public sealed class Child {
        public class Auth(public val component: AuthRootComponent) : Child()
        public class Main(public val component: MainRootComponent) : Child()
    }
}
