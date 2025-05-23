package kaiyrzhan.de.empath.features.auth.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PasswordOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPasswordValid: Boolean,
    arePasswordsMatching: Boolean,
    isValueVisible: Boolean,
    onShowClick: () -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.defaultMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = EmpathTheme.typography.bodyLarge,
            )
        },
        trailingIcon = {
            when {
                arePasswordsMatching.not() || isPasswordValid.not() -> {
                    Box(
                        modifier = Modifier.minimumInteractiveComponentSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_error),
                            contentDescription = stringResource(Res.string.password_dont_match_error),
                        )
                    }
                }

                else -> {
                    IconButton(
                        onClick = onShowClick,
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isValueVisible) Res.drawable.ic_visibility_off
                                else Res.drawable.ic_visibility_on
                            ),
                            contentDescription = null,
                        )
                    }
                }
            }
        },
        maxLines = 1,
        isError = arePasswordsMatching.not() || isPasswordValid.not(),
        visualTransformation = if (isValueVisible) VisualTransformation.None else PasswordVisualTransformation(),
    )
}