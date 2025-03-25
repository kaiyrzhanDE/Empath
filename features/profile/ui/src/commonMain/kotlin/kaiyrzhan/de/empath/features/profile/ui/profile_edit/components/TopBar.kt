package kaiyrzhan.de.empath.features.profile.ui.profile_edit.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empath.core.uikit.generated.resources.ic_arrow_back
import empath.core.uikit.generated.resources.ic_arrow_back_description
import empath.features.profile.ui.generated.resources.Res as FeatureRes
import empath.core.uikit.generated.resources.Res as CoreRes
import empath.features.profile.ui.generated.resources.edit_profile
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(FeatureRes.string.edit_profile),
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
                    painter = painterResource(CoreRes.drawable.ic_arrow_back),
                    contentDescription = stringResource(CoreRes.string.ic_arrow_back_description),
                )
            }
        },
    )

}