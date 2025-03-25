package kaiyrzhan.de.empath.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_broken_ice
import empath.core.uikit.generated.resources.oops_something_went_wrong_description
import empath.core.uikit.generated.resources.oops_something_went_wrong_title
import empath.core.uikit.generated.resources.try_again
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
public fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String,
    onTryAgainClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(Res.drawable.ic_broken_ice),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.defaultMaxWidth(),
            text = stringResource(Res.string.oops_something_went_wrong_title),
            style = EmpathTheme.typography.titleLarge,
            color = EmpathTheme.colors.onSurface,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.defaultMaxWidth(),
            text = message.ifBlank { stringResource(Res.string.oops_something_went_wrong_description) },
            style = EmpathTheme.typography.labelLarge,
            color = EmpathTheme.colors.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.defaultMaxWidth(),
            onClick = onTryAgainClick,
            shape = EmpathTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = EmpathTheme.colors.primary,
                contentColor = EmpathTheme.colors.onPrimary,
            ),
        ) {
            Text(
                text = stringResource(Res.string.try_again),
                style = EmpathTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}