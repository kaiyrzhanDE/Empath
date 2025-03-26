package kaiyrzhan.de.empath.features.profile.ui.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AccountProperties(
    onEditProfileClick: () -> Unit,
) {
    Card(
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
            contentColor = EmpathTheme.colors.onSurface,
        ),
    ) {
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.edit_profile),
            painter = painterResource(Res.drawable.ic_error),
            onClick = onEditProfileClick,
        )
    }
}