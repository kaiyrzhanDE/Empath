package kaiyrzhan.de.empath.features.auth.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.CodeConfirmationComponent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.EmailVerificationComponent
import kaiyrzhan.de.empath.features.auth.ui.login.LoginComponent
import kaiyrzhan.de.empath.features.auth.ui.signUp.SignUpComponent

public interface AuthComponent : ComponentContext {
    public val stack: Value<ChildStack<*, Child>>
    public fun onBackClick()

    public sealed class Child {
        public class Login(public val component: LoginComponent) : Child()
        public class EmailVerification(public val component: EmailVerificationComponent) : Child()
        public class CodeConfirmation(public val component: CodeConfirmationComponent) : Child()
        public class SignUp(public val component: SignUpComponent) : Child()
    }
}
