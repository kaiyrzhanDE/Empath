package kaiyrzhan.de.empath.features.profile.ui.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_error
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun WorkProperties(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = EmpathTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = EmpathTheme.colors.surface,
            contentColor = EmpathTheme.colors.onSurface,
        ),
    ) {
        Header(text = "Work")
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "CV",
            painter = painterResource(Res.drawable.ic_error),
            onClick = { /* TODO */ },
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = EmpathTheme.colors.outlineVariant,
        )
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "Vacancies",
            painter = painterResource(Res.drawable.ic_error),
            onClick = { /* TODO */ },
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = EmpathTheme.colors.outlineVariant,
        )
        Property(
            modifier = Modifier.fillMaxWidth(),
            text = "Tasks",
            painter = painterResource(Res.drawable.ic_error),
            onClick = { /* TODO */ },
        )
    }
}