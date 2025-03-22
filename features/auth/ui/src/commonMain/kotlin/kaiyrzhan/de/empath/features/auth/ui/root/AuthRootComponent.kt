package kaiyrzhan.de.empath.features.auth.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kaiyrzhan.de.empath.features.auth.ui.codeConfirmation.CodeConfirmationComponent
import kaiyrzhan.de.empath.features.auth.ui.emailVerification.EmailVerificationComponent
import kaiyrzhan.de.empath.features.auth.ui.login.LoginComponent
import kaiyrzhan.de.empath.features.auth.ui.password_recovery.PasswordRecoveryComponent
import kaiyrzhan.de.empath.features.auth.ui.signUp.SignUpComponent

public interface AuthRootComponent : ComponentContext {
    public val stack: Value<ChildStack<*, Child>>
    public fun onBackClick()

    public sealed class Child {
        internal class Login(val component: LoginComponent) : Child()
        internal class EmailVerification(val component: EmailVerificationComponent) : Child()
        internal class CodeConfirmation(val component: CodeConfirmationComponent) : Child()
        internal class SignUp(val component: SignUpComponent) : Child()
        internal class PasswordRecovery(val component: PasswordRecoveryComponent) : Child()
    }
}
