package kaiyrzhan.de.empath.core.ui.dialog.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

@Composable
internal fun DialogActionButton(
    config: DialogActionConfig?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (config?.isPrimary) {
        true -> {
            Button(
                modifier = modifier,
                onClick = onClick,
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmpathTheme.colors.primary,
                    contentColor = EmpathTheme.colors.onPrimary,
                ),
            ) {
                Text(
                    text = config.text,
                    style = EmpathTheme.typography.labelLarge,
                )
            }
        }

        false -> {
            TextButton(
                modifier = modifier,
                onClick = onClick,
                shape = EmpathTheme.shapes.small,
                colors = ButtonDefaults.textButtonColors(),
            ) {
                Text(
                    text = config.text,
                    style = EmpathTheme.typography.labelLarge,
                )
            }
        }

        null -> Unit
    }
}