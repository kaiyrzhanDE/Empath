package kaiyrzhan.de.empath.features.profile.ui.profileEdit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empath.core.uikit.generated.resources.*
import empath.core.uikit.generated.resources.Res
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    text = stringResource(Res.string.edit_profile),
                    style = EmpathTheme.typography.titleMedium,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = EmpathTheme.colors.surface,
                titleContentColor = EmpathTheme.colors.onSurface,
                navigationIconContentColor = EmpathTheme.colors.onSurface,
            ),
            navigationIcon = {
                IconButton(
                    onClick = onBackClick,
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_back),
                        contentDescription = stringResource(Res.string.ic_arrow_back_description),
                    )
                }
            },
        )
        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
    }
}