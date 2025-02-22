package kaiyrzhan.de.empath.features.auth.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.features.auth.ui.login.LoginComponent

public interface AuthComponent : ComponentContext {
    public val stack: Value<ChildStack<*, Child>>
    public fun onBackClick()

    public sealed class Child {
        public class Login(public val component: LoginComponent) : Child()
    }
}
